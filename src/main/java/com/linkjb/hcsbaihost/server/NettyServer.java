package com.linkjb.hcsbaihost.server;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.CharsetUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import java.io.IOException;
import java.util.concurrent.*;

/**
 * @author shark
 */
@Slf4j
@Component
public class NettyServer {

    @Resource
    RestTemplate restTemplate;

    private Thread thread;

    public void start(final String address, final int port) throws IOException {
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                // param
                EventLoopGroup bossGroup = new NioEventLoopGroup();
                EventLoopGroup workerGroup = new NioEventLoopGroup();
                ThreadPoolExecutor bizThreadPool = new ThreadPoolExecutor(
                        0,
                        200,
                        60L,
                        TimeUnit.SECONDS,
                        new LinkedBlockingQueue<Runnable>(2000),
                        new ThreadFactory() {
                            @Override
                            public Thread newThread(Runnable r) {
                                return new Thread(r, "shark-netty-Server-bizThreadPool-" + r.hashCode());
                            }
                        },
                        new RejectedExecutionHandler() {
                            @Override
                            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                                throw new RuntimeException("shark-netty-Server-bizThreadPool is EXHAUSTED!");
                            }
                        });
                try {
                    // start server
                    ServerBootstrap bootstrap = new ServerBootstrap();
                    bootstrap.group(bossGroup, workerGroup)
                            .channel(NioServerSocketChannel.class)
                            .childHandler(new ChannelInitializer<SocketChannel>() {
                                @Override
                                public void initChannel(SocketChannel channel) throws Exception {
                                    channel.pipeline()
                                            .addLast(new IdleStateHandler(0, 0, 30 * 3, TimeUnit.SECONDS))  // beat 3N, close if idle
                                            .addLast(new HttpServerCodec())
                                            .addLast(new HttpObjectAggregator(5 * 1024 * 1024))  // merge request & reponse to FULL
                                            //.addLast(new EmbedHttpServerHandler(gateWayBiz, accessToken, bizThreadPool));
                                            .addLast(new EmbedHttpServerHandler(bizThreadPool));
                                }
                            })
                            .childOption(ChannelOption.SO_KEEPALIVE, true);
                    // bind
                    ChannelFuture future = bootstrap.bind(port).sync();
                    log.info(">>>>>>>>>>> shark-netty remoting server start success, nettype = {}, port = {}", NettyServer.class, port);
                    future.channel().closeFuture().sync();

                } catch (InterruptedException e) {
                    log.info(">>>>>>>>>>> shark-netty remoting server stop.");
                } catch (Exception e) {
                    log.error(">>>>>>>>>>> shark-netty remoting server error.", e);
                } finally {
                    // stop
                    try {
                        workerGroup.shutdownGracefully();
                        bossGroup.shutdownGracefully();
                    } catch (Exception e) {
                        log.error(e.getMessage(), e);
                    }
                }
            }
        });
        thread.setDaemon(true);    // daemon, service jvm, user thread leave >>> daemon leave >>> jvm leave
        thread.start();
    }

    public void stop() throws Exception {
        // destroy server thread
        if (thread != null && thread.isAlive()) {
            thread.interrupt();
        }
        // stop registry
        //stopRegistry();
        log.info(">>>>>>>>>>> shark-netty remoting server destroy success.");
    }


    // ---------------------- registry ----------------------

    /**
     * netty_http
     * <p>
     *
     * @author shark
     */
    public class EmbedHttpServerHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

        private final ThreadPoolExecutor bizThreadPool;

        public EmbedHttpServerHandler(ThreadPoolExecutor bizThreadPool) {
            this.bizThreadPool = bizThreadPool;
        }



        @Override
        protected void channelRead0(final ChannelHandlerContext ctx, FullHttpRequest msg) throws Exception {
            String requestData = msg.content().toString(CharsetUtil.UTF_8);
            String uri = msg.uri();
            HttpHeaders headers = msg.headers();
            boolean keepAlive = HttpUtil.isKeepAlive(msg);
            // request parse
            //final byte[] requestBytes = ByteBufUtil.getBytes(msg.content());    // byteBuf.toString(io.netty.util.CharsetUtil.UTF_8);
            // invoke
            bizThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    // do invoke
                    Object responseObj = process(msg, requestData, uri, headers, ctx);
                    String jsonString = responseObj.toString();
                    // to json
                    //String responseJson = GsonTool.toJson(responseObj);
//                    try {
//                        jsonString = JSON.toJSONString(responseObj);
//                    } catch (Exception e) {
//
//                    }

                    // write response
                    writeResponse(ctx, keepAlive, jsonString, headers);
                }
            });
        }

        private Object process(FullHttpRequest msg, String requestData, String uri, HttpHeaders headers, ChannelHandlerContext ctx) {
            switch (uri) {
                //判断是否为组件专属
                case "/hcsbComponentTest":
                    //Result result = engineFeignService.componentTest(requestData);
                    return "hcsbComponentTest";
            }
            //return JSON.toJSONString()
            return "not matched";
        }

        /**
         * write response
         */
        private void writeResponse(ChannelHandlerContext ctx, boolean keepAlive, String responseJson, HttpHeaders headers) {
            // write response
            FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, Unpooled.copiedBuffer(responseJson, CharsetUtil.UTF_8));   //  Unpooled.wrappedBuffer(responseJson)
            // HttpHeaderValues.TEXT_PLAIN.toString()
            response.headers().set(HttpHeaderNames.CONTENT_TYPE, headers.get(HttpHeaderNames.CONTENT_TYPE) == null ? "text/html;charset=UTF-8" : headers.get(HttpHeaderNames.CONTENT_TYPE));
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes());
            if (keepAlive) {
                response.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
            }
            ctx.writeAndFlush(response);
        }

        @Override
        public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
            ctx.flush();
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
            log.error(">>>>>>>>>>> shark-netty provider netty_http server caught exception", cause);
            ctx.close();
        }

        @Override
        public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
            if (evt instanceof IdleStateEvent) {
                ctx.channel().close();      // beat 3N, close if idle
                log.debug(">>>>>>>>>>> shark-netty provider netty_http server close an idle channel.");
            } else {
                super.userEventTriggered(ctx, evt);
            }
        }
    }




}
package com.linkjb.hcsbaihost.executor;


import com.linkjb.hcsbaihost.server.NettyServer;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Resource;
import lombok.Data;
import lombok.experimental.Accessors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;



/**
 * @ClassName GateWayExecutor
 * @Description gateway-netty网关启动类
 * @Author shark
 * @Data 2023/5/10 17:25
 **/
@Data
@Accessors(chain = true)
@Schema(title = "netty注册器")
@Component
public class NettyExecutor {
    @Resource
    NettyServer nettyServer;


    private static final Logger logger = LoggerFactory.getLogger(NettyExecutor.class);

    @Schema(title = "主网关地址 可提供多个以,分割")
    private String gateWayAddresses;
    @Schema(title = "token")
    private String accessToken;
    @Schema(title = "应用名称如 hcsb普通应用")
    private String appName;
    @Schema(title = "netty发布地址")
    private String address = "0.0.0.0";
    @Schema(title = "netty发布ip")
    private String ip;
    @Schema(title = "netty发布端口")
    private int port = 9998;
    @Schema(title = "日志存储地址-相对地址")
    private String logPath;
    @Schema(title = "日志存储保存时间")
    private int logRetentionDays;

    public void statr() throws Exception {
        //初始化日志目录
        //GateWayFileAppender.initLogPath(logPath);
        //初始化shark-gateway网关 ，支持集群
        //initGateWayBizList(gateWayAddresses, accessToken);
        // init JobLogFileCleanThread
        // 初始化日志清理守护线程，比如清理超过30天的日志
        //LogFileCleanThread.getInstance().start(logRetentionDays);
        // init TriggerCallbackThread
        // 初始化回调调度平台的守护线程
        //不做回调

        // init executor-server
        //初始化执行器
        initNettyServer(address, ip, port, appName, accessToken);
    }

    public void destroy() throws Exception {
        nettyServer.stop();
    }

    //private static List<GateWayAdminBiz> gateWayAdminBizList;
//    private void initGateWayBizList(String gateWayAddresses, String accessToken) {
//        if (gateWayAddresses!=null && gateWayAddresses.trim().length()>0) {
//            for (String address: gateWayAddresses.trim().split(",")) {
//                if (address!=null && address.trim().length()>0) {
//
//                    GateWayAdminBiz adminBiz = new GateWayAdminBizClient(address.trim(), accessToken);
//
//                    if (gateWayAdminBizList == null) {
//                        gateWayAdminBizList = new ArrayList<GateWayAdminBiz>();
//                    }
//                    gateWayAdminBizList.add(adminBiz);
//                }
//            }
//        }
//    }

    private void initNettyServer(String address, String ip, int port, String appname, String accessToken) throws Exception {





        nettyServer.start(address, port);
    }


}
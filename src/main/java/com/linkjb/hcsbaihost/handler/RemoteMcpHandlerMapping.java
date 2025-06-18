package com.linkjb.hcsbaihost.handler;

import cn.hutool.core.util.StrUtil;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.http.codec.HttpMessageReader;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.http.server.reactive.observation.ServerRequestObservationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.filter.reactive.ServerHttpObservationFilter;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.handler.AbstractHandlerMapping;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.pattern.PathPattern;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: shark
 * @date: 2025/4/13 21:59
 * @description: 路由映射
 */
@Component
public class RemoteMcpHandlerMapping extends AbstractHandlerMapping implements InitializingBean {

    // 路由映射
    private final Map<String, RouterFunction<?>> mappings = new HashMap<>();

    private List<HttpMessageReader<?>> messageReaders = Collections.emptyList();

    @Override
    public int getOrder() {
        return 0;
    }


    public void afterPropertiesSet() {
        if (CollectionUtils.isEmpty(this.messageReaders)) {
            ServerCodecConfigurer codecConfigurer = ServerCodecConfigurer.create();
            this.messageReaders = codecConfigurer.getReaders();
        }
    }


    @Override
    protected Mono<?> getHandlerInternal(ServerWebExchange exchange) {
        // 获取路径随机字符串 mcp server的唯一标识
        String randomStr = exchange.getRequest().getURI().getPath().split("/")[1];
        if (StrUtil.isBlank(randomStr) || !mappings.containsKey(randomStr)) {
            return Mono.empty();
        }
        ServerRequest request = ServerRequest.create(exchange, this.messageReaders);
        return mappings.get(randomStr).route(request).doOnNext((handler) -> this.setAttributes(exchange.getAttributes(), request, handler));
    }

    public void addMapping(String path, RouterFunction<?> mapping) {
        mappings.put(path, mapping);
    }

    private void setAttributes(Map<String, Object> attributes, ServerRequest serverRequest, HandlerFunction<?> handlerFunction) {
        attributes.put(RouterFunctions.REQUEST_ATTRIBUTE, serverRequest);
        attributes.put(BEST_MATCHING_HANDLER_ATTRIBUTE, handlerFunction);
        PathPattern matchingPattern = (PathPattern) attributes.get(RouterFunctions.MATCHING_PATTERN_ATTRIBUTE);
        if (matchingPattern != null) {
            attributes.put(BEST_MATCHING_PATTERN_ATTRIBUTE, matchingPattern);
            ServerHttpObservationFilter.findObservationContext(serverRequest.exchange()).ifPresent((context) -> context.setPathPattern(matchingPattern.toString()));
            ServerRequestObservationContext.findCurrent(serverRequest.exchange().getAttributes()).ifPresent((context) -> context.setPathPattern(matchingPattern.toString()));
        }

        Map<String, String> uriVariables = (Map) attributes.get(RouterFunctions.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        if (uriVariables != null) {
            attributes.put(URI_TEMPLATE_VARIABLES_ATTRIBUTE, uriVariables);
        }
    }

    public void clear(String path) {
        mappings.remove(path);
    }
}
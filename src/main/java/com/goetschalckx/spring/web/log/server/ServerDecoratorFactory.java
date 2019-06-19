package com.goetschalckx.spring.web.log.server;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;

public class ServerDecoratorFactory {

    public LoggingServerWebExchangeDecorator webExchangeDecorator(ServerWebExchange exchange) {
        return new LoggingServerWebExchangeDecorator(exchange, this);
    }

    public CachingServerHttpRequestDecorator cachingServerHttpRequestDecorator(ServerHttpRequest serverHttpRequest) {
        return null;
    }

    public CachingServerHttpResponseDecorator cachingServerHttpResponseDecorator(ServerHttpResponse serverHttpResponse) {
        return null;
    }

}

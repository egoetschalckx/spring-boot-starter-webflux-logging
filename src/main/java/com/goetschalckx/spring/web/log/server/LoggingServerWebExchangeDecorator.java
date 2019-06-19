package com.goetschalckx.spring.web.log.server;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.ServerWebExchangeDecorator;

public final class LoggingServerWebExchangeDecorator extends ServerWebExchangeDecorator {

    private final ServerHttpRequestDecorator requestDecorator;
    private final ServerHttpResponseDecorator responseDecorator;

    public LoggingServerWebExchangeDecorator(
            ServerWebExchange exchange,
            ServerDecoratorFactory serverDecoratorFactory
    ) {
        super(exchange);
        this.requestDecorator = serverDecoratorFactory.cachingServerHttpRequestDecorator(exchange.getRequest());
        this.responseDecorator = serverDecoratorFactory.cachingServerHttpResponseDecorator(exchange.getResponse());
    }

    @Override
    public ServerHttpRequest getRequest() {
        return requestDecorator;
    }

    @Override
    public ServerHttpResponse getResponse() {
        return responseDecorator;
    }

}

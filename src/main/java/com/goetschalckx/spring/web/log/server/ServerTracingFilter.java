package com.goetschalckx.spring.web.log.server;

import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

public class ServerTracingFilter implements WebFilter {

    private final ServerDecoratorFactory serverDecoratorFactory;

    public ServerTracingFilter(ServerDecoratorFactory serverDecoratorFactory) {
        this.serverDecoratorFactory = serverDecoratorFactory;
    }

    @Override
    public Mono<Void> filter(
            ServerWebExchange exchange,
            WebFilterChain chain
    ) {
        return chain.filter(serverDecoratorFactory.webExchangeDecorator(exchange));
    }

}

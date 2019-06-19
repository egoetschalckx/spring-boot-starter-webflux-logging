package com.goetschalckx.spring.web.log.server;

import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;

public class CachingServerHttpResponseDecorator extends ServerHttpResponseDecorator {

    public CachingServerHttpResponseDecorator(ServerHttpResponse delegate) {
        super(delegate);
    }

}

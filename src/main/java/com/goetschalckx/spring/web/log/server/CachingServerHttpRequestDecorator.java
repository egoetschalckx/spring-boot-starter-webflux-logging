package com.goetschalckx.spring.web.log.server;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import reactor.core.publisher.Flux;

import java.nio.charset.StandardCharsets;
import java.time.OffsetDateTime;

public class CachingServerHttpRequestDecorator extends ServerHttpRequestDecorator {

    private final OffsetDateTime timestamp = OffsetDateTime.now();
    private final StringBuilder cachedBody = new StringBuilder();

    public CachingServerHttpRequestDecorator(ServerHttpRequest delegate) {
        super(delegate);
    }

    @Override
    public Flux<DataBuffer> getBody() {
        return super.getBody().doOnNext(this::cache);
    }

    //@SneakyThrows
    private void cache(DataBuffer buffer) {
        cachedBody
                .append(StandardCharsets.UTF_8.decode(buffer.asByteBuffer())
                .toString());
    }

    public String getCachedBody() {
        return cachedBody.toString();
    }

}

package com.goetschalckx.spring.web.log.client;

import com.goetschalckx.spring.web.log.LogEventContext;
import com.goetschalckx.spring.web.log.server.LoggingWebFilter;
import com.goetschalckx.spring.web.log.span.SpanIdGenerator;
import com.goetschalckx.spring.web.log.span.SpanType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.client.reactive.ClientHttpResponse;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

public class LoggingExchangeFilterFunction implements ExchangeFilterFunction {

    private static final Logger log = LoggerFactory.getLogger(LoggingWebFilter.class);
    private static final String SPAN_KIND_CLIENT = SpanType.CLIENT.logValue();

    private final ClientLogger clientLogger;
    private final SpanIdGenerator spanIdGenerator;

    public LoggingExchangeFilterFunction(
            ClientLogger clientLogger,
            SpanIdGenerator spanIdGenerator
    ) {
        this.clientLogger = clientLogger;
        this.spanIdGenerator = spanIdGenerator;
    }

    @Override
    public Mono<ClientResponse> filter(ClientRequest request, ExchangeFunction next) {
        final LogEventContext context = new LogEventContext(
                false,
                SPAN_KIND_CLIENT,
                spanIdGenerator.spanId(),
                request.method().toString(),
                request.url().toString()
        );

        clientLogger.logRequest(context, request);

        Mono<ClientResponse> clientResponseMono = next.exchange(request);

        return clientResponseMono.doOnSuccessOrError((x, y) -> {
            clientLogger.logResponse(context, x);
        });
    }

}

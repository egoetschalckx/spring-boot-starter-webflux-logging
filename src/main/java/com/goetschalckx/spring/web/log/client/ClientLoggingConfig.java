package com.goetschalckx.spring.web.log.client;

import com.goetschalckx.spring.web.log.span.SpanIdGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientLoggingConfig {

    @Bean
    public ClientLogger clientLogger() {
        return new ClientLogger();
    }

    @Bean
    public LoggingExchangeFilterFunction loggingExchangeFilterFunction(
            ClientLogger clientLogger,
            SpanIdGenerator spanIdGenerator
    ) {
        return new LoggingExchangeFilterFunction(
                clientLogger,
                spanIdGenerator
        );
    }

    @Bean
    public LoggingWebClientCustomizer loggingWebClientCustomizer(
            LoggingExchangeFilterFunction loggingExchangeFilterFunction
    ) {
        return new LoggingWebClientCustomizer(loggingExchangeFilterFunction);
    }

}

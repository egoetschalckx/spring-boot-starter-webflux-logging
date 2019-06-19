package com.goetschalckx.spring.web.log.client;

import org.springframework.boot.web.reactive.function.client.WebClientCustomizer;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

public class LoggingWebClientCustomizer implements WebClientCustomizer {

    private final LoggingExchangeFilterFunction loggingExchangeFilterFunction;

    public LoggingWebClientCustomizer(LoggingExchangeFilterFunction loggingExchangeFilterFunction) {
        this.loggingExchangeFilterFunction = loggingExchangeFilterFunction;
    }

    @Override
    public void customize(WebClient.Builder webClientBuilder) {
        /*final HttpClient httpClient = HttpClient
                .create()
                .wiretap(true);

        final ReactorClientHttpConnector reactorClientHttpConnector = new ReactorClientHttpConnector(httpClient);*/

        webClientBuilder
                .filter(loggingExchangeFilterFunction)
                //.clientConnector(reactorClientHttpConnector);
        ;
    }

}

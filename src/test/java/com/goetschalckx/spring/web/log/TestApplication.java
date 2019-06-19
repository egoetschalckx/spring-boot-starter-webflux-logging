package com.goetschalckx.spring.web.log;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@SpringBootApplication
//@ComponentScan("com.ncr.spring.http")
public class TestApplication {

    private static final String ORG = "nr1-order";
    private static final String APP_KEY = "8a808f025bc179b6015bd8e6c4e90021";
    private static final String CORRELATION_ID = UUID.randomUUID().toString();

    public static void main(String[] args) {
        new SpringApplicationBuilder(TestApplication.class).run(args);
    }

    @Bean
    public String test(
            WebClient.Builder webClientBuilder
    ) {
        WebClient webClient = webClientBuilder.build();

        HttpHeaders requestHeaders = generateHeaders();

        Map<String, Object> body = new HashMap<>();

        String url = "https://nep-gateway.swenglabs.ncr.com/order/orders";

        URI uri = UriComponentsBuilder
                .fromHttpUrl(url)
                .build()
                .encode()
                .toUri();

        Mono<String> responseMono = webClient
                .post()
                .uri(uri)
                .headers(headers -> { headers.addAll(requestHeaders); })
                .accept(MediaType.APPLICATION_JSON)
                .acceptCharset(StandardCharsets.UTF_8)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(body))
                .exchange()
                //.log()
                .flatMap(clientResponse -> {
                    if (clientResponse.statusCode().is5xxServerError()) {
                        clientResponse.body((clientHttpResponse, context) -> clientHttpResponse.getBody());
                        return clientResponse.bodyToMono(String.class);
                    } else {
                        return clientResponse.bodyToMono(String.class);
                    }
                });

        String response = responseMono.block();

        return null;
    }

    private HttpHeaders generateHeaders() {
        HttpHeaders headers = new HttpHeaders();

        headers.add("nep-enterprise-unit", UUID.randomUUID().toString().replaceAll("-", ""));
        headers.add("nep-organization", ORG);
        headers.add("nep-application-key", APP_KEY);
        headers.add("nep-correlation-id", CORRELATION_ID);

        headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON.toString());
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString());
        headers.add(HttpHeaders.DATE, DateTimeFormatter.RFC_1123_DATE_TIME.format(ZonedDateTime.now(ZoneOffset.UTC)));

        return headers;
    }

}

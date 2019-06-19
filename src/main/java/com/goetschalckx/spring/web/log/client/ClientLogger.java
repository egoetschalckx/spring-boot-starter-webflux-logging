package com.goetschalckx.spring.web.log.client;

import com.goetschalckx.spring.web.log.LogArgUtils;
import com.goetschalckx.spring.web.log.LogEventContext;
import com.goetschalckx.spring.web.log.LoggingConstants;
import net.logstash.logback.marker.Markers;
import org.slf4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;

public class ClientLogger {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(ClientLogger.class);

    public void logResponse(LogEventContext context, ClientResponse response) {
        String statusCode = response.statusCode().name();
        LogArgUtils.addIfValuePresent(context.getArgs(), LoggingConstants.HTTP_RESPONSE_CODE, statusCode);

        HttpHeaders httpHeaders = response.headers().asHttpHeaders();
        LogArgUtils.addHeaders(context.getArgs(), httpHeaders);

        if (context.getIncludeBody()) {
            String responseBody = "{{response body here}}";

            /*try {
                responseBody = StreamUtils.copyToString(response.getBody(), StandardCharsets.UTF_8);
            } catch (IOException e) {
                log.error("IOException during copyToString");
                responseBody = "[unknown]";
            }*/

            log.debug(
                    Markers.appendEntries(context.getArgs()),
                    "Inbound Message\n"
                            + "----------------------------\n"
                            + "Span Kind: {}\n"
                            + "Span ID: {}\n"
                            + "Method: {}\n"
                            + "URL: {}\n"
                            + "Status Code: {}\n"
                            + "Headers: {}\n"
                            + "Payload: {}\n"
                            + "--------------------------------------",
                    context.getSpanKind(),
                    context.getSpanId(),
                    context.getMethod(),
                    context.getUrl(),
                    statusCode,
                    httpHeaders,
                    responseBody);
        } else {
            log.debug(
                    Markers.appendEntries(context.getArgs()),
                    "Inbound Message\n"
                            + "--------------------------------------\n"
                            + "Span Kind: {}\n"
                            + "Span ID: {}\n"
                            + "Method: {}\n"
                            + "URL: {}\n"
                            + "Status Code: {}\n"
                            + "Headers: {}\n"
                            + "--------------------------------------",
                    context.getSpanKind(),
                    context.getSpanId(),
                    context.getMethod(),
                    context.getUrl(),
                    statusCode,
                    httpHeaders);
        }
    }

    public void logRequest(LogEventContext context, ClientRequest clientRequest) {
        LogArgUtils.addHeaders(context.getArgs(), clientRequest.headers());

        if (context.getIncludeBody()) {
            log.debug(
                    Markers.appendEntries(context.getArgs()),
                    "Outbound Message\n"
                            + "--------------------------------------\n"
                            + "Span Kind: {}\n"
                            + "Span ID: {}\n"
                            + "Method: {}\n"
                            + "URL: {}\n"
                            + "Headers: {}\n"
                            + "Payload: {}\n"
                            + "--------------------------------------",
                    context.getSpanKind(),
                    context.getSpanId(),
                    context.getMethod(),
                    context.getUrl(),
                    clientRequest.headers(),
                    "{{request body here}}");
                    //new String(body, StandardCharsets.UTF_8));
        } else {
            log.debug(
                    Markers.appendEntries(context.getArgs()),
                    "Outbound Message\n"
                            + "--------------------------------------\n"
                            + "Span Kind: {}\n"
                            + "Span ID: {}\n"
                            + "Method: {}\n"
                            + "URL: {}\n"
                            + "Headers: {}\n"
                            + "--------------------------------------",
                    context.getSpanKind(),
                    context.getSpanId(),
                    context.getMethod(),
                    context.getUrl(),
                    clientRequest.headers());
        }
    }

}

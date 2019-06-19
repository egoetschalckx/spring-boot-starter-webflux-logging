package com.goetschalckx.spring.web.log.server;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * https://stackoverflow.com/questions/47182961/copy-of-the-request-response-body-on-a-spring-reactive-app
 */
@Configuration
public class ServerLoggingConfig {

    @Bean
    public ServerDecoratorFactory serverDecoratorFactory() {
        return new ServerDecoratorFactory();
    }

    @Bean
    public ServerTracingFilter serverTracingFilter(
            ServerDecoratorFactory serverDecoratorFactory
    ) {
        return new ServerTracingFilter(serverDecoratorFactory);
    }

}

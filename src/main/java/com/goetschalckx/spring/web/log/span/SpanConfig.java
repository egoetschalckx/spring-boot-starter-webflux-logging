package com.goetschalckx.spring.web.log.span;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpanConfig {

    @Bean
    public SpanIdGenerator spanIdGenerator() {
        return new SpanIdGenerator();
    }

}

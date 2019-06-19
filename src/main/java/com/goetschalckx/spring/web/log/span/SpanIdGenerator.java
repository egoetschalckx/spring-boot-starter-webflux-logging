package com.goetschalckx.spring.web.log.span;

import java.util.UUID;

public class SpanIdGenerator {

    public String spanId() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

}

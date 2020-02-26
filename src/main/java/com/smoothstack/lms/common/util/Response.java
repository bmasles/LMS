package com.smoothstack.lms.common.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class Response {

    private final ResponsePayload payload = new ResponsePayload(HttpStatus.OK);

    public Response(HttpStatus status) {
        payload.setStatus(status);
    }

    public Response(HttpStatus status, Object object) {
        this(status, object.getClass().getSimpleName(), object);
    }

    public Response(HttpStatus status, String key, Object object) {
        this(status);
        payload.getResponse().put(key, object);
    }

    public Response(String key, Object object) {
        payload.getResponse().put(key, object);
    }

    public Response() {
        this(HttpStatus.OK);
    }

    public ResponsePayload getPayload() {
        return payload;
    }

    public ResponseEntity<ResponsePayload> buildResponseEntity() {
        payload.getRequest().values().forEach(LoopTerminator::apply);
        payload.getResponse().values().forEach(LoopTerminator::apply);
        payload.getMeta().values().forEach(LoopTerminator::apply);
        return ResponseEntity.status(payload.getStatus()).body(payload);
    }
}



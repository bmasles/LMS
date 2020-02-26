package com.smoothstack.lms.common.util;

import org.springframework.http.HttpStatus;

import java.util.LinkedHashMap;
import java.util.Map;

public class ResponsePayload {
    private int code = HttpStatus.OK.value();
    private HttpStatus status = HttpStatus.OK;
    private final Map<String, Object> meta = new LinkedHashMap<>();
    private final Map<String, Object> request = new LinkedHashMap<>();
    private final Map<String, Object> response = new LinkedHashMap<>();

    public ResponsePayload(HttpStatus statusCode) {
        setStatus(statusCode);
    }

    public int getCode() {
        return code;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.code = status.value();
        this.status = status;
    }

    public Map<String, Object> getRequest() {
        return request;
    }

    public Map<String, Object> getResponse() {
        return response;
    }

    public Map<String, Object> getMeta() {
        return meta;
    }
}
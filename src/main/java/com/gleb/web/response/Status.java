package com.gleb.web.response;

public enum Status {
    OK("HTTP/1.1 200 OK"),
    BAD_REQUEST("HTTP/1.1 400 Bad Request"),
    NOT_FOUND("HTTP/1.1 404 Not Found"),
    SERVICE_UNAVAILABLE("HTTP/1.1 503 Service Unavailable");

    private String status;

    Status(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}

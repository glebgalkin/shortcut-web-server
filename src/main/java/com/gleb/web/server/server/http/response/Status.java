package com.gleb.web.server.server.http.response;

public enum Status {
    OK("200 OK"),
    BAD_REQUEST("400 Bad Request"),
    NOT_FOUND("404 Not Found"),
    SERVICE_UNAVAILABLE("503 Service Unavailable");

    private String status;
    Status(String status) {
        this.status = status;
    }
}

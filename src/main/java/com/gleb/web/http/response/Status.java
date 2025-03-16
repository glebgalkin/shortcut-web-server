package com.gleb.web.http.response;

public enum Status {
    OK("HTTP/1.1 200 OK"),
    BAD_REQUEST("HTTP/1.1 400 Bad Request"),
    NOT_FOUND("HTTP/1.1 404 Not Found"),
    SERVICE_UNAVAILABLE("HTTP/1.1 503 Service Unavailable");

    private final String name;

    Status(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

package com.gleb.web.http.request;

public interface RequestFactory {
    HttpRequest build(String rawData);
}

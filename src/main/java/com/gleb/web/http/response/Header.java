package com.gleb.web.http.response;

public enum Header {
    CONTENT_TYPE("Content-Type"),
    CONTENT_LENGTH("Content-Length"),
    CACHE_CONTROL("Cache-Control");

    private final String name;

    Header(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public enum ContentType {
        TEXT_HTML("text/html;UTF-8"),
        OCTET_STREAM("application/octet-stream");

        private final String name;

        ContentType(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    enum CacheControl {
        NO_CACHE("no-cache");
        private final String name;

        CacheControl(String name){ this.name = name; }

        public String getName() { return name; }
    }
}

package com.gleb.web.response;

public enum Header {
    CONTENT_TYPE("Content-Type"),
    CONTENT_LENGTH("Content-Length");

    private String name;

    Header(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    enum ContentType {
        TEXT_HTML("text/html;UTF-8"),
        OCTET_STREAM("application/octet-stream");

        private String name;

        ContentType(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}

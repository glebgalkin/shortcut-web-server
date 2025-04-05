package com.gleb.web.http.request;

import java.io.IOException;
import java.io.InputStream;

public interface RequestParser {
    HttpRequest parse(InputStream socketStream) throws IOException;
}

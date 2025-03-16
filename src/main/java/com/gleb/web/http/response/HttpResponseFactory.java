package com.gleb.web.http.response;

import java.io.File;
import java.io.IOException;

public interface HttpResponseFactory {
    HttpResponse build(Status status, File file) throws IOException;
}

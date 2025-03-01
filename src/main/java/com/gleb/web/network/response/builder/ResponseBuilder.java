package com.gleb.web.network.response.builder;

import java.io.File;
import java.io.IOException;

public class ResponseBuilder {
    public static byte[] build(Status status, File file) throws IOException {
        HttpResponse httpResponse = HttpResponseBuilder.buildResponse(status, file);
        return ByteResponseBuilder.build(httpResponse);
    }
}

package com.gleb.web.network.response;

import com.gleb.web.network.response.util.ByteResponseBuilder;
import com.gleb.web.network.response.util.HttpResponse;
import com.gleb.web.network.response.util.HttpResponseBuilder;
import com.gleb.web.network.response.util.Status;

import java.io.File;
import java.io.IOException;

public class ResponseBuilder {
    public static byte[] build(Status status, File file) throws IOException {
        HttpResponse httpResponse = HttpResponseBuilder.buildResponse(status, file);
        return ByteResponseBuilder.build(httpResponse);
    }
}

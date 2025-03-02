package com.gleb.web.network.response;

import com.gleb.web.network.response.util.Header;
import com.gleb.web.network.response.util.HttpResponse;
import com.gleb.web.network.response.util.HttpResponseBuilder;
import com.gleb.web.network.response.util.Status;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HttpResponseBuilderTest {
// TODO: Switch http response body to InputStream

//    @Test
//    public void buildResponse() throws IOException {
//        // given
//        File tempFile = Files.createTempFile("test", ".html").toFile();
//        Files.write(tempFile.toPath(), "<html><body>Test</body></html>".getBytes());
//        System.out.println(tempFile.getAbsolutePath());
//
//        // when
//        HttpResponse response = HttpResponseBuilder.buildResponse(Status.OK, tempFile);
//
//        // then
//        assertEquals(3, response.getHeaders().size());
//        assertEquals(Header.ContentType.TEXT_HTML.getName(), response.getHeaders().get(Header.CONTENT_TYPE.getName()));
//    }
}

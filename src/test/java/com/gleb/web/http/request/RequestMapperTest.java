package com.gleb.web.http.request;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class RequestMapperTest {

    @Test
    public void testBuildRequest() {
        // given
        String rawData = "GET /home HTTP/1.1\n" +
                "Host: localhost\n" +
                "User-Agent: Mozilla/5.0\n" +
                "Accept: text/html\n";

        // when
        RequestFactoryImpl requestFactory = new RequestFactoryImpl();
        HttpRequest request = requestFactory.build(rawData);

        // then
        assertNotNull(request);
        assertEquals("GET", request.getRequestType().name());
        assertEquals("/home", request.getPath());
        assertEquals("localhost", request.getHeaders().get("Host"));
        assertEquals("Mozilla/5.0", request.getHeaders().get("User-Agent"));
        assertEquals("text/html", request.getHeaders().get("Accept"));
    }
}

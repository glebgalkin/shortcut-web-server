package com.gleb.web.network;

import com.gleb.web.network.util.NetworkUtil;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;

public class NetworkUtilTest {

    @Test
    public void getRawString() throws IOException {
        // given
        String httpRequestData = "GET /hello HTTP/1.1\nHost: localhost\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(httpRequestData.getBytes());

        // when
        String rawString = NetworkUtil.getRawRequest(inputStream);

        // then
        assertEquals(httpRequestData, rawString);
    }
}

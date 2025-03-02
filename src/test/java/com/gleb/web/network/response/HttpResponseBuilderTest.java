package com.gleb.web.network.response;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class HttpResponseBuilderTest {

    @Test
    public void buildResponse() throws IOException {
        // given
        // TODO for such tests it's beter to store files in the test resources
        File tempFile = Files.createTempFile("test", ".html").toFile();
        Files.writeString(tempFile.toPath(), "<html><body>Test</body></html>");

        try {
            // when
            HttpResponse response = HttpResponseBuilder.build(Status.OK, tempFile);

            // then
            assertEquals(Status.OK.getName(), response.getStatus());
            assertEquals(3, response.getHeaders().size());
            assertEquals(Header.ContentType.TEXT_HTML.getName(), response.getHeaders().get(Header.CONTENT_TYPE.getName()));
            assertEquals(String.valueOf(tempFile.length()), response.getHeaders().get(Header.CONTENT_LENGTH.getName()));
            assertNotNull(response.getBody());

        } finally {
            if(tempFile.delete()) System.out.println("Successfully deleted test file: " +
                    tempFile.getName() + " at path: " + tempFile.getPath());
            else System.out.println("Could not remove test file: " +
                    tempFile.getName() + " at path: " + tempFile.getPath());
        }
    }
}

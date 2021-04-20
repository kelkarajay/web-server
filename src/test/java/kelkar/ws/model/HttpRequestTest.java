package kelkar.ws.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Set;

public class HttpRequestTest {
    private HttpRequest httpRequest;

    @BeforeEach
    public void setup() {
        httpRequest = new HttpRequest();
    }

    @Test
    public void testParseRequest() throws IOException {
        String reqLine = "GET / HTTP/1.1\r\nContent-Type: text/html\n";
        httpRequest.parseRequest(reqLine);
        Assertions.assertEquals("GET", httpRequest.getMethod());
        Assertions.assertEquals("/", httpRequest.getUri());
    }

    @Test
    public void testParseRequestHeaders() throws IOException {
        String reqLine = "GET / HTTP/1.1\r\nContent-Type: text/html\n";
        httpRequest.parseRequest(reqLine);
        Set<String> headerKeys = httpRequest.getAllHeaderKeys();

        Assertions.assertEquals(1, headerKeys.size());
        Assertions.assertEquals("text/html", httpRequest.getHeaderValue("Content-Type"));
    }

    @Test
    public void testParseRequestNonExistingHeader() throws IOException {
        String reqLine = "GET / HTTP/1.1\r\nContent-Type: text/html\n";
        httpRequest.parseRequest(reqLine);
        Set<String> headerKeys = httpRequest.getAllHeaderKeys();

        Assertions.assertEquals(1, headerKeys.size());
        Assertions.assertEquals(null, httpRequest.getHeaderValue("Accept"));
    }

    @Test
    public void testParseRequest_BadRequest() {
        String reqLine = "GET / \r\nContent-Type: text/html\n";
        try {
            httpRequest.parseRequest(reqLine);
        } catch (Exception e) {
            Assertions.assertTrue(e instanceof ArrayIndexOutOfBoundsException);
        }
    }

    @Test
    public void testParseRequest_BadRequestHeader() throws IOException {
        String reqLine = "GET / HTTP/1.1\r\nContent-Type: \n";
        try {
            httpRequest.parseRequest(reqLine);
        } catch (Exception e) {
            Assertions.assertTrue(e instanceof ArrayIndexOutOfBoundsException);
        }
    }
}

package kelkar.ws.model;

import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

public class HttpRequest {
    private HashMap<String, String> queryParametersMap;
    private HashMap<String, String> requestHeadersMap;
    private String method;
    private String uri;
    private String httpVersion;

    public HttpRequest() {
        queryParametersMap = new HashMap<String, String>(0);
        requestHeadersMap = new HashMap<String, String>(8);
    }

    @Override
    public String toString() {
        return "HttpRequest{" +
                "queryParametersMap=" + queryParametersMap +
                ", requestHeadersMap=" + requestHeadersMap +
                ", method='" + method + '\'' +
                ", uri='" + uri + '\'' +
                ", httpVersion='" + httpVersion + '\'' +
                '}';
    }

    /***
     * Uses BufferedReader to read from the socket and parses to build HttpRequest
     * @param data - Data encoded as a string
     * @throws IOException
     */
    public void parseRequest(String data) {
        String temp;
        String[] lines = data.split("\n");
        String[] tokens;
        boolean requestLineRead = false;
        for (String line: lines) {
            if (!requestLineRead) {
                parseRequestLine(line);
                requestLineRead = true;
                continue;
            }
            if (line.equals("")) break;
            tokens = line.split(": ");
            requestHeadersMap.put(tokens[0], tokens[1]);
        }
        System.out.println(method);
        System.out.println(uri);
        System.out.println(httpVersion);
        System.out.println(requestHeadersMap.toString());
    }

    /***
     * Fetch header value for the specified request header
     * @param key
     * @return String
     */
    public String getHeaderValue(String key) {
        if (requestHeadersMap.containsKey(key)) {
            return requestHeadersMap.get(key);
        }
        return null;
    }

    public String getMethod() {
        return method;
    }

    public String getUri() {
        return uri;
    }

    /***
     * Return keys of all headers on the HttpRequest
     * @return Set<String>
     */
    public Set<String> getAllHeaderKeys() {
        return requestHeadersMap.keySet();
    }

    private void parseRequestLine(String reqLine) throws ArrayIndexOutOfBoundsException {
        String[] tokens = reqLine.split(" ");
        method = tokens[0];
        uri = tokens[1];
        httpVersion = tokens[2];
    }
}

package kelkar.ws.model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class HttpResponse {
    private String httpVersion = "HTTP/1.1";
    private String lineBreakChar = "\n";

    private StringBuilder response;
    private HttpStatus httpStatus;
    private String responseBody;
    private HashMap<String, String> headerMap;

    public HttpResponse() {
        this.response = new StringBuilder();
        this.httpStatus = HttpStatus.OK;
        this.headerMap = new HashMap<String, String>();
    }

    public HttpResponse(HttpStatus httpStatus, String body) {
        this.response = new StringBuilder();
        this.httpStatus = httpStatus;
        this.responseBody = body;
        this.headerMap = new HashMap<String, String>();
    }

    public HttpResponse(HttpStatus httpStatus, HashMap<String, String> headerMap, String body) {
        this.response = new StringBuilder();
        this.httpStatus = httpStatus;
        this.responseBody = body;
        this.headerMap = headerMap;
    }

    /***
     * Build the response with Headers and body
     * @return String - response to be written to the connection
     */
    public String build() {
        response.setLength(0); // Clear to build

        addResponseHeaderLine();
        response.append(lineBreakChar);

        addHeaders();
        response.append(lineBreakChar);
        response.append(lineBreakChar);

        response.append(responseBody);

        return response.toString();
    }

    private void addResponseHeaderLine() {
        response.append(httpVersion).append(" ").append(this.httpStatus.getStatusCode()).append(" ").append(this.httpStatus.getStatusText());
    }

    private void addHeaders() {
        if(headerMap == null){
            return;
        }

        Set<String> keys = headerMap.keySet();
        Iterator<String> keyIterator = keys.iterator();

        while(keyIterator.hasNext()) {
            String key = keyIterator.next();
            String value = headerMap.get(key);

            if (value != null) {
                response.append(key).append(": ").append(value);
                response.append(lineBreakChar);
            }
        }
    }
}

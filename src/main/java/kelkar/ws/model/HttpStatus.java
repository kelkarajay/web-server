package kelkar.ws.model;

/***
 * Enum for Http Response Codes
 * @author Ajay Kelkar
 * Reference - https://tools.ietf.org/html/rfc7231#section-6.1
 */
public enum HttpStatus {
    OK(200, "OK"),
    NOT_FOUND(404, "Not Found"),
    FORBIDDEN(403, "Forbidden"),
    INTERNAL_SERVER_ERROR(500, "Internal Server Error"),
    BAD_REQUEST(400, "Bad Request");

    private String statusText;
    private int statusCode;

    public String getStatusText() {
        return this.statusText;
    }

    public int getStatusCode() {
        return this.statusCode;
    }

    @Override
    public String toString() {
        return this.statusCode + " " + this.statusText;
    }

    private HttpStatus (int code, String value) {
        this.statusText = value;
        this.statusCode = code;
    }
}

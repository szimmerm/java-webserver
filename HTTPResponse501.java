import java.util.Map;
import java.util.HashMap;

/* Representation of a HTTP response code 501 (not implemented) */
public class HTTPResponse501 implements IHTTPResponse {
    private Map<String, String> headers;
    private String crlf;

    public HTTPResponse501() {
        char[] crlfArray = {(char) 13, (char) 10};
        headers = new HashMap<String, String>();
        crlf = new String(crlfArray);
    }

    public void addHeader(String headerName, String headerValue) {
        headers.put(headerName, headerValue);
    }

    public void addContent(String newData) {
        return;
    }

    public String toString() {
        String body = "<h1>Not yet implemented !</h1>"+crlf;
        StringBuffer response = new StringBuffer();
        response.append("HTTP/1.1 501 Not Implemented"+crlf);
        response.append("Content-Length: "+body.length()+crlf+crlf);
        response.append(body);
        return response.toString();
    }
}

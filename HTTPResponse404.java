import java.util.Map;
import java.util.HashMap;


/* Representation of a 404 (Not found) HTTP response */
public class HTTPResponse404 implements IHTTPResponse {
    private Map<String, String> headers;
    private String crlf;

    public HTTPResponse404() {
        char[] crlfArray = {(char) 13, (char) 10};
        headers = new HashMap<String, String>();
        crlf = new String(crlfArray);
    }

    public void addHeader(String headerName, String headerValue) {
        headers.put(headerName, headerValue);
    }

    public void addContent(String newData) {
        return; //404 page is hardcoded
    }

    public String toString() {
        String body = "<h1>Error 404 : File not found !!</h1>"+crlf;
        StringBuffer response = new StringBuffer();
        response.append("HTTP/1.1 404 Not Found"+crlf);
        response.append("Content-Length: "+body.length()+crlf+crlf);
        response.append(body);
        return response.toString();
    }
}

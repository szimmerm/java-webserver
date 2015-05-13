import java.util.Map;
import java.util.HashMap;


/* Representation of a 403 (access forbidden) HTTP response */
public class HTTPResponse403 implements IHTTPResponse {
    private Map<String, String> headers;
    private String crlf;

    public HTTPResponse403() {
        char[] crlfArray = {(char) 13, (char) 10};
        headers = new HashMap<String, String>();
        crlf = new String(crlfArray);
    }

    public void addHeader(String headerName, String headerValue) {
        headers.put(headerName, headerValue);
    }

    public void addContent(String newData) {
        return; //403 page is hardcoded
    }

    public String toString() {
        String body = "<h1>Error 403 : Action forbidden !!</h1>"+crlf;
        StringBuffer response = new StringBuffer();
        response.append("HTTP/1.1 403 Forbidden"+crlf);
        response.append("Content-Length: "+body.length()+crlf+crlf);
        response.append(body);
        return response.toString();
    }
}

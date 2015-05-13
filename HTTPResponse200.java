import java.util.Map;
import java.util.Hashtable;

/* representation of a HTTP response code 200 (OK code) */
public class HTTPResponse200 implements IHTTPResponse {
    private String crlf;
    private Map<String, String> headers;
    private StringBuffer content;

    public HTTPResponse200() {
        char[] crlfArray = {(char) 13, (char) 10};
        crlf = new String(crlfArray);
        headers = new Hashtable<String, String>();
        content = new StringBuffer();
    }

    public void addHeader(String headerName, String headerValue) {
        headers.put(headerName, headerValue);
    }

    public void addContent(String newData) {
        content.append(newData);
    }

    private String buildHeaders() {
        StringBuffer buffer = new StringBuffer();
        for(Map.Entry<String, String> entry : headers.entrySet()) {
            buffer.append(entry.getKey()+": "+entry.getValue()+crlf);
        }
        buffer.append("Content-Length: "+content.length()+crlf);
        return buffer.toString();
    }

    private String buildContent() {
        return content.toString();
    }

    public String toString() {
        StringBuffer response = new StringBuffer();
        response.append("HTTP/1.1 200 OK"+crlf);
        response.append(buildHeaders());
        response.append(crlf);
        response.append(buildContent());
        response.append(crlf);
        return response.toString();
    }
}

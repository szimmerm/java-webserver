/* common interface for all responses ; used to treat them uniformly */
public interface IHTTPResponse {
    public void addHeader(String headerName, String headerValue);
    public void addContent(String newData);
    public String toString();
}

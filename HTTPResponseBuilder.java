/* we use a factory-like construction to build the responses, as the connection
 * doesn't have to known the response code or such */
public class HTTPResponseBuilder {
    public static IHTTPResponse buildResponse(int statusCode) {
        switch(statusCode) {
            case 200:
                return new HTTPResponse200();
            case 403:
                return new HTTPResponse403();
            case 404:
                return new HTTPResponse404();
            default:
                return new HTTPResponse501();
        }
    }
}

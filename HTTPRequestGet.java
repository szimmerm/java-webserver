import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;


/* internal representation of a GET request*/
public class HTTPRequestGet {
    private static final String localPrefix = "public_html";
    private String crlf;

    private Map<String, String> headers;
    private String path;

    public HTTPRequestGet(String request) throws UndefinedActionException{
        char[] crlfArray = {(char) 13, (char)10};
        crlf = new String(crlfArray);
        headers = new HashMap<String, String>();

        String[] splittedRequest = request.split(crlf, 2);
        String[] splittedCode = splittedRequest[0].split(" ", 3);
        if(splittedCode[0].equals("GET")) {
            path = splittedCode[1];
            parseHeaders(splittedRequest[1]);
        } else {
            throw new UndefinedActionException(splittedCode[0]);
        }
    }

    /* process the request and return an appropriate HTTP Response */
    public IHTTPResponse process() {
        if (!isPathValid()) {
            return HTTPResponseBuilder.buildResponse(403);
        }

        try {
            BufferedReader in = 
                new BufferedReader(new FileReader(localPrefix+path));
            IHTTPResponse response = HTTPResponseBuilder.buildResponse(200);
            String line;
            while((line = in.readLine()) != null) {
                response.addContent(line);
            }
            in.close();
            return response;
        }
        catch(IOException e) {
            System.err.println(e);
            return HTTPResponseBuilder.buildResponse(404);
        }
    }

    /* check if request ask for keeping connection alive*/
    public boolean keepConnectionAlive() {
        return "keep-alive".equals(headers.get("Connection"));
    }

    private void parseHeaders(String headersString) {
        for(String headerLine : headersString.split(crlf)) {
            String[] pair = headerLine.split(": ", 2);
            headers.put(pair[0], pair[1]);
        }
    }

    private boolean isPathValid() {
        int depth = 0;
        String[] pathUnits = path.split("/");
        for(String folder : pathUnits) {
            if(folder.equals("..")) {
                depth--;
                if (depth <= 0) {
                    return false;
                }
            } else {
                depth++;
            }
        }
        return true;
    }

}

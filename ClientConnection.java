import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.FileReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

/* representation of a client connection */
public class ClientConnection implements Runnable {
    private static final String localPrefix = "public_html";
    private Socket clientSocket;
    private BufferedReader clientInput;
    private OutputStreamWriter clientOutput;
    private boolean keepOpen = true;
    private final int socketTimeout = 60000; // 1 minute timeout

    public ClientConnection(Socket socket) throws IOException, SocketException {
        clientSocket = socket;
        clientSocket.setSoTimeout(socketTimeout);
        clientInput = 
            new BufferedReader(
                new InputStreamReader(clientSocket.getInputStream()));
        clientOutput = 
            new OutputStreamWriter(clientSocket.getOutputStream());
    }

    /* execute the connection ; used for threading all the clients connections */
    public void run() {
        char[] endPattern = {(char)13, (char)10, (char)13, (char)10};

        try {
            while(keepOpen) {
                String requestString = readInputUntilPattern(new String(endPattern));
                System.err.println("request got on socket : "+clientSocket);
                System.err.println(requestString);
                HTTPRequestGet request = new HTTPRequestGet(requestString);
                IHTTPResponse response = request.process();
                clientOutput.append(response.toString());
                clientOutput.flush();
                keepOpen = request.keepConnectionAlive();
            }
        }
        catch (SocketTimeoutException e) {
            System.err.println(e);
        }
        catch (IOException e) {
            System.err.println(e);
        }
        catch (UndefinedActionException e) {
            System.err.println(e);
        }

        try {
            System.err.println("closing socket : "+clientSocket);
            clientSocket.close();
        }
        catch (IOException e) {
            System.err.println(e);
        }
    }

    /* read the HTTP request on the stream */
    private String readInputUntilPattern(String pattern) 
        throws SocketTimeoutException, IOException{
        int patternPosition = 0;
        StringBuffer buff = new StringBuffer();

        while(patternPosition < pattern.length()) {
            int readValue = clientInput.read();
            if (readValue == -1) {
                throw new SocketTimeoutException("client has closed stream");
            }
            char readCharacter = (char) readValue;
            buff.append(readCharacter);
            if(readCharacter == pattern.charAt(patternPosition)) {
                patternPosition++;
            } else {
                patternPosition = 0; //TODO : on suppose que CR ne vient pas
                                    // sans LF. A corriger pour etre general
            }
        }
        return buff.toString();
    }
}

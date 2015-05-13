import java.net.Socket;
import java.net.ServerSocket;
import java.io.IOException;

/* server class ; used to regroup all connection threads */
public class Server {
    private boolean running = true;
    private int serverPort;
    private ServerSocket serverSocket;

    public Server(int port) {
        serverPort = port;
    }

    public void run() {
        System.out.println("Launching the server");
        try {
            serverSocket = new ServerSocket(serverPort);
            while(running) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Connection accepted : "+clientSocket);
                ClientConnection client = new ClientConnection(clientSocket);
                (new Thread(client)).start();
            }
        }
        catch (IOException e) {
            System.err.println(e);
            return;
        }
    }
}

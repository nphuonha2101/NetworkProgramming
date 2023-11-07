package socket.echoServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {
    private ServerSocket server;

    public static void main(String[] args) throws IOException {
        EchoServer echoServer = new EchoServer();
        echoServer.startServer();
    }

    public void startServer() throws IOException {
        server = new ServerSocket(7777);
        System.out.println("Server is listening at port " + 7777);

        while (true) {
            Socket client = server.accept();

            new Thread(new ServerThread(server, client)).start();
        }
    }
}

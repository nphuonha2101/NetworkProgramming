package socket.ftpServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static final int PORT = 5678;
    private ServerSocket server;

    public static void main(String[] args) throws IOException {
        Server server = new Server();
        server.startServer();
    }

    public void startServer() throws IOException {
        server = new ServerSocket(PORT);
        System.out.println("Server is listening at port " + PORT);

        while (true) {
            Socket client = server.accept();

            new Thread(new ServerThread(server, client)).start();
        }


    }
}

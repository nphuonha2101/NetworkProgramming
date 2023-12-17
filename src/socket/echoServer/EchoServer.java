package socket.echoServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {
    public static void main(String[] args) throws IOException {
        try {
            ServerSocket server = new ServerSocket(7777);
            System.out.println("Server is listening on port 7");


            while (true) {
                Socket client = server.accept();
                new Thread(new EchoThread(client)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

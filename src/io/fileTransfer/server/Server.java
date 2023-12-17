package io.fileTransfer.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private ServerSocket server;
    private ServerSocket ftpServer;

    public void startServer(int port) throws IOException {
        server = new ServerSocket(port);

        while (true) {
            Socket client = server.accept();
            PrintWriter clientOut = new PrintWriter(client.getOutputStream(), true);
            clientOut.println("Hello from server");
            new Thread(new ServerThread(client, this)).start();
        }
    }

    public boolean openFtpServer() throws IOException {
        synchronized (Server.class) {
            if (ftpServer == null)
                ftpServer = new ServerSocket(23456);
            return true;
        }
    }

    public ServerSocket getFtpServer() {
        synchronized (Server.class) {
            return ftpServer;
        }
    }

    public static void main(String[] args) throws IOException {
        Server server = new Server();
        server.startServer(12345);
    }
}

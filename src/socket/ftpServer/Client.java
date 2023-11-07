package socket.ftpServer;

import java.net.Socket;

public class Client {
    private Socket client;

    public static void main(String[] args) {
        Client client = new Client();
        client.connectServer("localhost", 5678);
    }

    public void connectServer(String address, int port) {
        try {
            client = new Socket(address, port);

            System.out.println("Connected to server " + address + ":" + port);
            new Thread(new ClientThread(client)).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

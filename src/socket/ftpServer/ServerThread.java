package socket.ftpServer;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerThread implements Runnable {
    private ServerSocket server;
    private Socket client;

    public ServerThread(ServerSocket server, Socket client) {
        this.server = server;
        this.client = client;
    }


    @Override
    public void run() {
        try {
            DataInputStream dis = new DataInputStream(client.getInputStream());
            String desFile = dis.readUTF();

            IO.transferFileServer(dis, desFile);
            dis.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}

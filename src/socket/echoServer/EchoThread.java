package socket.echoServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class EchoThread implements Runnable {
    private Socket client;

    public EchoThread(Socket client) throws IOException {
        this.client = client;
    }


    @Override
    public void run() {
        BufferedReader clientBufferedReader = null;
        try {
            clientBufferedReader = new BufferedReader(new InputStreamReader(client.getInputStream(),
                    StandardCharsets.UTF_8));

            PrintWriter clientOutputStream = new PrintWriter(client.getOutputStream());
            clientOutputStream.println("Hello from server!");
            clientOutputStream.flush();

            while (true) {
                String line = clientBufferedReader.readLine().toLowerCase();

                if (line.equals("exit")) {

                    clientOutputStream.println("Goodbye!");
                    clientOutputStream.flush();
                    client.close();
                }
                clientOutputStream.println("Echo: " + line);
                clientOutputStream.flush();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

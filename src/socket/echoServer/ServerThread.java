package socket.echoServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ServerThread implements Runnable {
    private Socket client;
    private ServerSocket server;

    public ServerThread(ServerSocket server, Socket client) {
        this.server = server;
        this.client = client;
    }


    @Override
    public void run() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream(), StandardCharsets.UTF_8));
            String commands = reader.readLine();

            executeCommands(commands);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void executeCommands(String commands) throws IOException {
        String[] commandsArr = commands.split(" ");
        String command = commandsArr[0];
        String message = commandsArr[1];
        switch (command) {
            case "echo" -> echo(message);
            case "exit" -> {
                client.close();
                server.close();
            }
        }
    }

    public void echo(String message) throws IOException {
        PrintWriter out = new PrintWriter(client.getOutputStream());

        out.println("Echo: " + message);
        out.flush();
    }
}

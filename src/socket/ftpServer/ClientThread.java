package socket.ftpServer;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ClientThread implements Runnable {
    private Socket client;

    public ClientThread(Socket client) {
        this.client = client;
    }


    @Override
    public void run() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));
            String command = bufferedReader.readLine();

            executeCommand(command);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    public void executeCommand(String command) {
        String[] commandTokens = command.split(" ");
        String commandName = commandTokens[0];
        String srcFile = commandTokens[1];
        String desFile = commandTokens[2];
        switch (commandName) {
            case "exit" -> {
                try {
                    client.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            case "copy" -> {
                try {
                    InputStream is = new BufferedInputStream(new FileInputStream(srcFile));
                    IO.transferFileClient(is, desFile, client);
                    is.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}

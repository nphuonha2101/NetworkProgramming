package ftp.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.StringTokenizer;

public class ServerThread implements Runnable {
    private Socket client;
    private ServerSocket serverSocketFTP;
    private Socket transSocket;

    public ServerThread(Socket client) throws IOException {
        this.client = client;
        serverSocketFTP = new ServerSocket(2100);
    }

    @Override
    public void run() {
        try {
            BufferedReader cmdReader = new BufferedReader(new InputStreamReader(client.getInputStream(), StandardCharsets.UTF_8));
            String commands = cmdReader.readLine();

            handleCommand(commands);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public boolean handleCommand(String cmd) throws IOException {
        StringTokenizer cmdTokens = new StringTokenizer(cmd, " ");
        String command = "";
        String dest = "";
        PrintWriter clientOut = new PrintWriter(client.getOutputStream(), true);



        if (cmdTokens.hasMoreTokens()) {
            command = cmdTokens.nextToken().trim().toLowerCase();

            if (command.equals("quit")) {
                client.close();
                return true;
            }

            if (command.equals("trans")) {
                if (cmdTokens.hasMoreTokens()) {
                    cmdTokens.nextToken();
                    if (cmdTokens.hasMoreTokens()) {
                        transSocket = serverSocketFTP.accept();
                        clientOut.println("OK");

                        dest = cmdTokens.nextToken();
                        InputStream inputStream = transSocket.getInputStream();
                        transferFile(inputStream, dest);
                    }
                } else {
                    clientOut.println("Vui long nhap dest path");
                }
            }

        } else {
            clientOut.println("Vui long nhap lenh!");
        }
        return false;
    }

    public void transferFile(InputStream src, String dest) throws IOException {
        BufferedInputStream bis = new BufferedInputStream(src);
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(dest));

        byte[] buffer = new byte[100 * 1024];
        int bytesRead;

        while ((bytesRead = bis.read(buffer)) != -1) {
            bos.write(buffer, 0, bytesRead);
        }

        bis.close();
        bos.close();
    }
}

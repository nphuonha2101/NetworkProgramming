package ftp.client;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.StringTokenizer;

public class Client {
    private Socket socket;
    private Socket transSocket;

    public void start() throws IOException {
        socket = new Socket("localhost", 2000);
        System.out.println("connected to server!");
        System.out.flush();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));
        PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
        out.flush();
//        out.println(reader.readLine());



        while (true) {
            String cmd = reader.readLine();
//            out.println(cmd);
            handleCommand(cmd);
        }


    }

    public void handleCommand(String command) throws IOException {
        StringTokenizer cmdTokens = new StringTokenizer(command, " ");
        command = cmdTokens.nextToken().trim().toLowerCase();
        String src = "";

        if (command.equals("trans")) {
            if (cmdTokens.hasMoreTokens()) {
                src = cmdTokens.nextToken();
                if (cmdTokens.hasMoreTokens()) {

                    transSocket = new Socket("localhost", 2100);

                    transferFile(src);
                }
            }
        }
    }

    public void transferFile(String src) throws IOException {
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(src));
        BufferedOutputStream bos = new BufferedOutputStream(transSocket.getOutputStream());

        byte[] buffer = new byte[100 * 1024];
        int bytesRead = 0;

        while ((bytesRead = bis.read(buffer)) != -1) {
            bos.write(buffer, 0, bytesRead);
        }

        bis.close();
        bos.close();
    }

    public static void main(String[] args) throws IOException {
        Client client = new Client();
        client.start();
    }
}

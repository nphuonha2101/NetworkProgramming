package io.fileTransfer.client;

import io.fileTransfer.TransferUtils;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.StringTokenizer;

public class Client {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 12345);

        while (true) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));
            String content = bufferedReader.readLine();
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println(content);
            StringTokenizer contentTokens = new StringTokenizer(content, " ");

            String param1 = "";
            String param2 = "";

            if (contentTokens.nextToken().trim().toLowerCase().equals("send")) {
                Socket transportDataSocket = new Socket("localhost", 23456);
                if (contentTokens.hasMoreTokens())
                    param1 = contentTokens.nextToken();
                if (contentTokens.hasMoreTokens()) {
                    param2 = contentTokens.nextToken();

                    BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(param1));
                    BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(transportDataSocket.getOutputStream());

                    TransferUtils.transfer(bufferedInputStream, bufferedOutputStream);
                }
            }
        }

    }
}

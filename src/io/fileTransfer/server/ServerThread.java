package io.fileTransfer.server;

import io.fileTransfer.TransferUtils;
import io.fileTransfer.userServices.UserServices;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.StringTokenizer;

public class ServerThread implements Runnable {
    private Socket client;
    private Server server;
    private String destPath = "D:/Test";
    private boolean isEndSession;
    private String username;
    private boolean isLogin;

    public ServerThread(Socket client, Server server) {
        this.client = client;
        this.server = server;
    }

    @Override
    public void run() {
        BufferedReader clientIn = null;
        try {
            clientIn = new BufferedReader(new InputStreamReader(client.getInputStream(), StandardCharsets.UTF_8));
            while (!isEndSession) {
                String command = clientIn.readLine();
                handleCommand(command);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    public void handleCommand(String command) throws IOException {
        PrintWriter clientOut = new PrintWriter(client.getOutputStream(), true);

        StringTokenizer cmdTokens = new StringTokenizer(command, " ");
        String instruction = "";
        String param1 = "";
        String param2 = "";
        if (cmdTokens.hasMoreTokens()) {
            instruction = cmdTokens.nextToken().toLowerCase().trim();
            if (!cmdTokens.hasMoreTokens()) {
                switch (instruction) {
                    case "quit": {
                        isEndSession = true;
                        client.close();
                        break;
                    }
                    case "ping": {
                        clientOut.println("[Server]: ban vua ping den Server");
                        break;
                    }
                    default:
                        clientOut.println("[Server]: lenh khong hop le");
                        break;
                }

            } else {
                if (!isLogin) {
                    param1 = cmdTokens.nextToken();

                    switch (instruction) {
                        case "user": {
                            username = UserServices.getInstance().authUsername(param1);
                            if (username == null)
                                clientOut.println("[Server]: username khong ton tai");
                            else
                                clientOut.println("[Server]: OK");
                            break;
                        }
                        case "pass": {
                            if (username != null) {
                                isLogin = UserServices.getInstance().authLogin(username, param1);
                                if (isLogin)
                                    clientOut.println("[Server]: Login OK");
                                else
                                    clientOut.println("[Server]: Login failed");
                            } else
                                clientOut.println("[Server]: vui long nhap username");
                            break;
                        }
                        default:
                            clientOut.println("[Server]: lenh khong hop le");
                            break;
                    }
                } else {
                    if (cmdTokens.hasMoreTokens())
                        param2 = cmdTokens.nextToken();

                    switch (instruction) {
                        case "setpath": {
                            setDestPath(param1);
                            clientOut.println("[Server]: Set path thanh cong");
                            clientOut.println(getDestPath());
                            break;
                        }

                        case "send": {
                            if (param2 == null) {
                                clientOut.println("[Server]: param2 bang null");
                            } else {
                                server.openFtpServer();
                                clientOut.println("[Server]: OK");
                                try {
                                    Socket clientFtp = server.getFtpServer().accept();

                                    InputStream is = clientFtp.getInputStream();
                                    OutputStream os = new FileOutputStream(param2);

                                    TransferUtils.transfer(is, os);

                                    clientOut.println("[Server]: file transfer successful");
                                } catch (IOException e) {
                                    e.printStackTrace(System.out);
                                }
                            }
                            break;
                        }

                        default:
                            clientOut.println("[Server]: lenh khong hop le");
                            break;
                    }

                }
            }
        }
    }



    public String getDestPath() {
        return this.destPath;
    }

    public void setDestPath(String destPath) {
        this.destPath = destPath;
    }
}

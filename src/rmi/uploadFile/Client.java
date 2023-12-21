package rmi.uploadFile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.StringTokenizer;

public class Client {
    public static void main(String[] args) throws IOException, NotBoundException {
        boolean isLogin = false;

        Registry reg = LocateRegistry.getRegistry(12345);
        RemoteInterface upload = (RemoteInterface) reg.lookup("UPLOAD");

        System.out.println("Enter command: ");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));


        int sessionID = -1;
        boolean foundUser = false;
        String loginUsername = null;

        while (!isLogin) {
            String command = reader.readLine();
            StringTokenizer commandTokens = new StringTokenizer(command, " ");
            String cmd;
            String param1;
            String param2;
            if (commandTokens.countTokens() < 2) {
                System.out.println("Not valid command");
            } else {
                cmd = commandTokens.nextToken().trim().toLowerCase();
                param1 = commandTokens.nextToken().trim();
                switch (cmd) {
                    case "user": {
                        foundUser = upload.authUsername(param1);
                        if (foundUser) {
                            System.out.println("OK");
                            loginUsername = param1;
                        } else {
                            System.out.println("Username not found");
                        }

                        break;
                    }
                    case "pass": {
                        if (foundUser) {
                            sessionID = upload.authUser(loginUsername, param1);
                            if (sessionID == -1) {
                                System.out.println("Login failed");
                            } else {
                                System.out.println("OK");
                                isLogin = true;
                            }
                        } else {
                            System.out.println("Login failed!, not found username: " + loginUsername);
                        }
                        break;
                    }

                    default:
                        System.out.println("Not valid command!");
                }
            }
        }

        System.out.println("test");
        while (true) {
            String command = reader.readLine();
            StringTokenizer commandTokens = new StringTokenizer(command, " ");
            String cmd;
            String param1;
            String param2;

            if (commandTokens.countTokens() == 3) {

                cmd = commandTokens.nextToken().trim().toLowerCase();
                param1 = commandTokens.nextToken().trim();
                param2 = commandTokens.nextToken().trim();


                switch (cmd) {
                    case "upload": {
                        System.out.println(sessionID);

                        upload.createFile(param2, sessionID);

                        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(param1));
                        byte[] data = new byte[102400];
                        int bytesRead;
                        while ((bytesRead = bis.read(data)) != -1) {
                            System.out.println(bytesRead);
                            byte[] fitData = new byte[bytesRead];
                            System.arraycopy(data, 0, fitData, 0, bytesRead);

                            upload.transferFile(fitData, sessionID);

                        }
                        bis.close();
                        upload.closeTransfer(sessionID);

                        System.out.println("Upload successfully");
                        break;
                    }

                    case "get": {
                        byte[] data;
                        OutputStream os = new BufferedOutputStream(new FileOutputStream(param2));
                        do {
                            data = upload.getDataFromServer(param1, sessionID);

                            if (data != null)
                                os.write(data);
                            else {
                                System.out.println("Get data failed!");
                                break;
                            }

                        } while (data != null);

                        os.close();

                        System.out.println("Get data from server successfully!");
                        break;
                    }
                    default: {
                        System.out.println("Not valid command!");
                    }
                }

            }
            if (commandTokens.countTokens() == 1 && command.equals("quit")) {
                break;
            }
        }
    }


}

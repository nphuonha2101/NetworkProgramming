package socket.ftpServer;

import java.io.*;
import java.net.Socket;

public class IO {
    public static void transferFileClient(InputStream is, String path, Socket client) throws IOException {
        DataOutputStream dos = new DataOutputStream(client.getOutputStream());

        dos.writeUTF(path);
        byte[] buffer = new byte[100 * 1024];
        int bytesReadLen = 0;
        while ((bytesReadLen = is.read(buffer)) != -1) {
            dos.write(buffer, 0, bytesReadLen);
        }

        dos.close();
    }

    public static void transferFileServer(InputStream is, String filePath) throws IOException {
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));

        byte[] buffer = new byte[100 * 1024];
        int bytesReadLen = 0;

        while ((bytesReadLen = is.read(buffer)) != -1) {
            bos.write(buffer, 0, bytesReadLen);
        }

        bos.close();
    }
}

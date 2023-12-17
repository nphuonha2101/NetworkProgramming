package io.fileTransfer;

import java.io.*;

public class TransferUtils {
    public static void transfer(InputStream srcStream, OutputStream destStream) throws IOException {
        BufferedInputStream bufferedInputStream = new BufferedInputStream(srcStream);
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(destStream);

        byte[] buffer = new byte[100 * 1024];
        int bytesRead;

        while ((bytesRead = bufferedInputStream.read(buffer)) != -1) {
            bufferedOutputStream.write(buffer, 0, bytesRead);
        }

        bufferedInputStream.close();
        bufferedOutputStream.close();

    }
}

package io;

import java.io.*;

public class FileCopy {
    public static void copyFile(String sourceFile, String desFile) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(sourceFile);
        BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
        FileOutputStream fileOutputStream = new FileOutputStream(desFile);
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);

        byte[] buffer = new byte[100*1024];
        int data;

        while ((data = bufferedInputStream.read(buffer)) != -1) {
            bufferedOutputStream.write(buffer, 0, data);
        }
        bufferedInputStream.close();
        bufferedOutputStream.close();

    }

    public static void main(String[] args) throws IOException {
        FileCopy.copyFile("src/io/FileCopy.java", "src/io/FileCopy2.java");
    }
}

package io;

import java.io.*;

public class FileSplitAndJoin {

    public static void fileSplit(String src, int partSize) throws IOException {
        File srcF = new File(src);
        InputStream fis = new FileInputStream(srcF);
        File destF;
        OutputStream fos;

        int numberOfParts = (int) ((srcF.length() % partSize != 0) ? srcF.length() / partSize + 1 : srcF.length() / partSize);

        for (int i = 0; i < numberOfParts; i++) {
            destF = new File(generateFileName(srcF, i));
            fos = new FileOutputStream(destF);

            transfer(fis, fos, partSize);
        }
    }

    public static void join(String firstFile) throws IOException {
        String[] pathArr = firstFile.split("\\\\");
        // get folder path
        String folderPath = "";
        for (int i = 0; i < pathArr.length - 1; i++) {
            folderPath += pathArr[i];
        }

        String[] baseFileNameArr = pathArr[pathArr.length - 1].split("\\.");
        String baseFileName = "";
        for (int i = 0; i < baseFileNameArr.length - 1; i++) {
            baseFileName += baseFileNameArr[i] + ".";
        }

        System.out.println(baseFileName + "\t" + folderPath);

        InputStream fis;
        OutputStream fos = new FileOutputStream(folderPath + "\\" + baseFileName);
        File folder = new File(folderPath);
        File[] files = folder.listFiles();
        for (File file : files
        ) {
            if (file.isFile() && file.getName().startsWith(baseFileName)) {
                fis = new FileInputStream(file);
                transfer(fis, fos, (int) file.length());
            }
        }
    }

    public static void transfer(InputStream fis, OutputStream fos, int partSize) throws IOException {
        byte[] buff = new byte[100 * 1024];
        int remain = partSize;
        int byteReq;
        int bytesReadLen;

        while (remain > 0) {
            // kiem tra neu do dai con lai nho hon buffer length thi doc n byte con lai
            // neu khong thi doc dung theo kich thuoc buffer
            byteReq = Math.min(remain, buff.length);
            bytesReadLen = fis.read(buff, 0, byteReq);
            if (bytesReadLen == -1)
                return;
            fos.write(buff, 0, bytesReadLen);
            remain -= bytesReadLen;
        }
    }

    private static String generateFileName(File f, int level) {
        return f.getAbsolutePath() + "." + level;
    }

    public static void main(String[] args) throws IOException {
        String srcP = "D:\\test\\apache-tomcat-10.1.10.exe";
        int partSize = 1000 * 1024;

//        FileSplitAndJoin.fileSplit(srcP, partSize);
        String firstF = "D:\\test\\apache-tomcat-10.1.10.exe.0";
        FileSplitAndJoin.join(firstF);
    }

}

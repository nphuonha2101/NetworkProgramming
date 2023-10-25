package io.packNUnpack;

import java.io.*;

public class FilePackAndUnpack {

    public boolean pack(String filePath, String folderPath) throws IOException {
        File f = new File(filePath);
        RandomAccessFile raf = new RandomAccessFile(f, "rw");
        File folder = new File(folderPath);
        FileInputStream fis;
        long[] headerPos;

        File[] listFile = folder.listFiles();
        if (listFile == null)
            return false;

        headerPos = new long[listFile.length];
        // write header
        raf.writeInt(listFile.length);
        for (int i = 0; i < listFile.length; i++) {
            File file = listFile[i];
            long currentPos = raf.getFilePointer();
            headerPos[i] = currentPos;

            raf.writeLong(i);
            raf.writeUTF(file.getName());
            raf.writeLong(file.length());
        }

        // write data
        for (int i = 0; i < listFile.length; i++) {
            File file = listFile[i];
            fis = new FileInputStream(file);

            long currentPos = raf.getFilePointer();
            // update header
            raf.seek(headerPos[i]);
            raf.writeLong(currentPos);
            // return to data pos to write data
            raf.seek(currentPos);

            transfer(fis, raf, file.length());
            fis.close();
        }

        raf.close();
        return true;
    }

    private void transfer(FileInputStream fis, RandomAccessFile raf, long fileSize) throws IOException {
        byte[] buff = new byte[100 * 1024];

        long remain = fileSize;
        int byteRq;
        int byteReadLen;

        while (remain > 0) {
            byteRq = remain < buff.length ? (int) remain : buff.length;

            byteReadLen = fis.read(buff, 0, byteRq);
            if (byteReadLen == -1)
                return;
            raf.write(buff, 0, byteReadLen);
            remain -= byteReadLen;
        }
    }

    private void transfer(FileOutputStream fos, RandomAccessFile raf, long fileSize) throws IOException {
        byte[] buff = new byte[100 * 1024];

        long remain = fileSize;
        int byteRq;
        int byteReadLen;

        while (remain > 0) {
            byteRq = remain < buff.length ? (int) remain : buff.length;

            byteReadLen = raf.read(buff, 0, byteRq);
            if (byteReadLen == -1)
                return;
            fos.write(buff, 0, byteReadLen);
            remain -= byteReadLen;
        }
    }

    public boolean unpack(String filePack, String fileName, String unpackPath) throws IOException {
        File f = new File(filePack);
        RandomAccessFile raf = new RandomAccessFile(f, "r");
        FileOutputStream fos;
        Header[] headers;

        int fileListSize = raf.readInt();
        headers = new Header[fileListSize];

        for (int i = 0; i < fileListSize; i++) {
            long pos = raf.readLong();
            String fName = raf.readUTF();
            long fSize = raf.readLong();

            Header header = new Header(pos, fName, fSize);
            headers[i] = header;
        }

        // find file with name
        for (Header header : headers) {
            if (header.getFileName().contains(fileName)) {
                raf.seek(header.getPos());
                fos = new FileOutputStream(unpackPath + "\\" + header.getFileName());
                System.out.println(unpackPath + "\\" + header.getFileName());
                transfer(fos, raf, header.getFileSize());
            } else
                return false;
        }

        return true;
    }

    public static void main(String[] args) throws IOException {
        String folderPath = "C:\\Users\\NhaNguyen\\Desktop\\test";
        String packFile = "C:\\Users\\NhaNguyen\\Desktop\\data.pack";

        FilePackAndUnpack filePackAndUnpack = new FilePackAndUnpack();
        filePackAndUnpack.pack(packFile, folderPath);

        filePackAndUnpack.unpack(packFile, "apache-tomcat-10.1.10.exe", "C:\\Users\\NhaNguyen\\Desktop");


    }
}

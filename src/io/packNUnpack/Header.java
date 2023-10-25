package io.packNUnpack;

public class Header {
    private long pos;
    private String fileName;
    private long fileSize;

    public Header(long pos, String fileName, long fileSize) {
        this.pos = pos;
        this.fileName = fileName;
        this.fileSize = fileSize;
    }

    public long getPos() {
        return pos;
    }

    public void setPos(long pos) {
        this.pos = pos;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }
}

package io;

import java.io.File;

public class FileIO {
    public static boolean delete(String path) {
        File file = new File(path);
        if (!file.exists()) return false;
        if (file.listFiles() != null)
            for (File subFile : file.listFiles()) {
                delete(subFile.getAbsolutePath());
            }
        return file.delete();
    }

    public static boolean delete1(String path) {
        File file = new File(path);
        if (!file.exists()) return false;
        if (file.listFiles() != null)
            for (File subFile : file.listFiles()) {
                delete(subFile.getAbsolutePath());
            }
        return true;
    }

    public static String list(String path) {
        return listHelper(path, 0, "", 0);
    }

    private static String listHelper(String path, int level, String result, long folderSize) {
        File file = new File(path);
        if (!file.exists()) return result;
        if (file.listFiles() != null) {
            for (File subFile : file.listFiles()) {
                if (subFile.isDirectory()) {
                    result += getIndent(level) + subFile.getName().toUpperCase() + "(" + getFolderSize(subFile.getAbsolutePath()) +
                            "Bytes)" + "\n";
                    return listHelper(subFile.getAbsolutePath(), level + 1, result, folderSize);
                } else {
                    result += getIndent(level) + subFile.getName().toLowerCase() + "\n";
                    folderSize += subFile.length();
                }
            }
        }
        return result;
    }

    public static StringBuilder getIndent(int level) {
        StringBuilder result = new StringBuilder();
        result.append("   ");
        result.append("|--".repeat(Math.max(0, level)));

        result.append("++");
        return result;
    }

    public static long getFolderSize(String folderPath) {
        File file = new File(folderPath);
        if (!file.exists()) return 0;
        if (file.listFiles() != null) {
            long folderSize = 0;
            for (File subFile : file.listFiles()) {
                if (subFile.isDirectory()) {
                    folderSize += getFolderSize(subFile.getAbsolutePath());
                } else {
                    folderSize += subFile.length();
                }
            }
            return folderSize;
        }
        return 0;
    }

    public void getLengthTree(String path) {
        File dir = new File(path);
        if (dir.listFiles() != null) {
            for (File file: dir.listFiles()
                 ) {

            }
        }
        System.out.println(dir.length());
    }

    public static void main(String[] args) {
//        System.out.println(delete1("D:\\upload\\test"));
        System.out.println(list("D:\\upload\\test"));
    }
}

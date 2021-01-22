package cn.maoookai.util;

import java.io.File;
import java.util.ArrayList;

public class FileReadUtil {
    public static ArrayList<File> getFiles(String path) {
        ArrayList<File> fileList = new ArrayList<>();
        File file = new File(path);
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            assert files != null;
            for (File fileIndex : files) {
                if (fileIndex.isDirectory()) {
                    getFiles(fileIndex.getPath());
                } else {
                    fileList.add(fileIndex);
                }
            }
        }
        return fileList;
    }
}

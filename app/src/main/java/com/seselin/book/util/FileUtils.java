package com.seselin.book.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

public class FileUtils {

    public static void write(String savePath, String fileName, String content) {
        BufferedWriter out = null;
        try {
            String filePath = savePath + "/" + fileName;
            File saveDir = new File(savePath);
            if (!saveDir.exists()) {
                saveDir.mkdirs();
            }
            File file = new File(filePath);
            if (!file.isFile()) {
                file.createNewFile();
            }
            FileOutputStream outputStream = new FileOutputStream(filePath, true);
            out = new BufferedWriter(new OutputStreamWriter(outputStream));
            out.write(content + "\r\n");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}

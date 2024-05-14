package com.it.util;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class WriterFileUtils {
    private static final String prefix = "classpath:";
    public static void writeFile(String directory, String fileName, String content){
        directory = prefix + directory;
        try {
            File dir = new File(directory);
            if (!dir.exists()){
                dir.mkdir();
            }
            String filePath = directory + fileName;
            File file = new File(filePath);
            if(!file.exists()){
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(filePath);
            fw.write(content);
            fw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
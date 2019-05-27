package by.agat.system.utility;

import by.agat.system.domain.Document;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.util.*;


public class FileWorker {

    private File directory;

    public FileWorker(String path) {
        Date date = new Date(); // your date
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        File[] directories = new File(path).listFiles(File::isDirectory);
        String monthStr = String.valueOf(month).length() > 1 ? String.valueOf(month) : "0" + month;
        for (File file : directories) {
            if(file.getName().contains(year + "_" + monthStr)) {
                directory = file;
                break;
            }
        }
    }

    public static String readFromFile(String path) throws IOException {

        String encodedString = "";

        if(!path.contains("sign")) {
            byte[] fileContent = FileUtils.readFileToByteArray(new File(path));
            encodedString = Base64.getEncoder().encodeToString(fileContent);
        } else {
            encodedString = FileUtils.readFileToString(new File(path));
        }
        return encodedString;
    }



}

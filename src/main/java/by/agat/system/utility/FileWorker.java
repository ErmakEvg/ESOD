package by.agat.system.utility;

import by.agat.system.domain.Document;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


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

   /* public List<Document> getAllDocuments() {
        List<Document> documents = new ArrayList<>();
        if(directory == null) {
            return null;
        }
        else {
            File[] directoriesByBank = new File(directory.getPath()).listFiles(File::isDirectory);
            for (File f : directoriesByBank) {
                File[] documentsFile = new File(f.getPath()).listFiles(File::isFile);
                for (File document : documentsFile) {
                    Document doc = new Document();
                    doc.setCodeBank(f.getName());
                    doc.setName(document.getName());
                    doc.setType("Вклад");
                    doc.setPath(document.getPath());
                    documents.add(doc);
                }
            }
            return documents;
        }
    }*/


}

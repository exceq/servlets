package models;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyFile {
    File file;
    String formattedSize;
    String date;

    public String getFormattedSize() {
        long l = file.length();
        return  l < 1024 ? l + " Б":
                l < (1024*1024) ? l / 1024 + " КБ" :
                l < (1024 *1024*1024) ? l/(1024*1024) + " МБ":
                l / (1024*1024*1024) + " ГБ";
    }

    public String getDate() {
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");
        return df.format(new Date(file.lastModified()));
    }

    public File getFile() {
        return file;
    }

    public MyFile(File file) {
        this.file = file;
    }
}

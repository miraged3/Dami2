package cn.maoookai.scheduler;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogFileScheduler implements Runnable {
    @Override
    public void run() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd-HH.mm.ss");
        Date date = new Date(System.currentTimeMillis());
        File oldFile = new File("log/mirai.log");
        if (oldFile.exists()) {
            if (oldFile.renameTo(new File("log/" + simpleDateFormat.format(date) + ".log"))) {
                System.out.println("Old log is filed to log/" + simpleDateFormat.format(date) + ".log");
            }
            File newFile = new File("log/mirai.log");
            try {
                System.out.println(newFile.createNewFile());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

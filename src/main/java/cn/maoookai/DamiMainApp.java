package cn.maoookai;

import cn.maoookai.listener.MainListener;
import cn.maoookai.scheduler.LogFileScheduler;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.BotFactory;
import net.mamoe.mirai.auth.BotAuthorization;
import net.mamoe.mirai.utils.BotConfiguration;
import xyz.cssxsh.mirai.tool.FixProtocolVersion;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class DamiMainApp {

    public static void main(String[] args) throws Exception {
        InputStream in = new FileInputStream("config.properties");
        Properties properties = new Properties();
        properties.load(in);
        LogFileScheduler logFileScheduler = new LogFileScheduler();
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(logFileScheduler, 0, 24, TimeUnit.HOURS);
//        Bot dami = BotFactory.INSTANCE.newBot(Long.parseLong(properties.getProperty("qq.account")), properties.getProperty("qq.password"), new BotConfiguration() {{
//            setProtocol(MiraiProtocol.ANDROID_PAD);
//            fileBasedDeviceInfo();
//            File logDir = new File("log/");
//            if (!logDir.exists() && !logDir.isDirectory()) {
//                System.out.println("未发现日志文件夹，正在创建");
//                if (logDir.mkdir())
//                    System.out.println("已创建log文件夹");
//                else
//                    System.out.println("创建失败...请检查权限");
//            }
//            File oldLog = new File("log/mirai.log");
//            if (oldLog.exists())
//                System.out.println("Current log directory is " + oldLog.getAbsolutePath());
//            redirectBotLogToFile(new File("log/mirai.log"));
//        }});
        Bot dami = BotFactory.INSTANCE.newBot(Long.parseLong(properties.getProperty("qq.account")), BotAuthorization.byQRCode(),new BotConfiguration() {{
            setProtocol(MiraiProtocol.ANDROID_WATCH);
            fileBasedDeviceInfo();
            File logDir = new File("log/");
            if (!logDir.exists() && !logDir.isDirectory()) {
                System.out.println("未发现日志文件夹，正在创建");
                if (logDir.mkdir())
                    System.out.println("已创建log文件夹");
                else
                    System.out.println("创建失败...请检查权限");
            }
            File oldLog = new File("log/mirai.log");
            if (oldLog.exists())
                System.out.println("Current log directory is " + oldLog.getAbsolutePath());
            redirectBotLogToFile(new File("log/mirai.log"));
        }});
        dami.login();
        new MainListener().initListener(dami, properties);
        dami.join();
    }
}

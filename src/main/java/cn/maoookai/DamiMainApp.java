package cn.maoookai;

import cn.maoookai.listener.MainListener;
import cn.maoookai.scheduler.LogFileScheduler;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.BotFactory;
import net.mamoe.mirai.utils.BotConfiguration;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class DamiMainApp {

    //TODO: 日志自动归档
    public static void main(String[] args) throws Exception {
        InputStream in = new FileInputStream("config.properties");
        Properties properties = new Properties();
        properties.load(in);
        LogFileScheduler logFileScheduler = new LogFileScheduler();
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(logFileScheduler, 0, 24, TimeUnit.HOURS);
        Bot dami = BotFactory.INSTANCE.newBot(Long.parseLong(properties.getProperty("qq.account")), properties.getProperty("qq.password"), new BotConfiguration() {{
            setProtocol(MiraiProtocol.ANDROID_WATCH);
            fileBasedDeviceInfo();
            File oldLog = new File("mirai.log");
            if (oldLog.exists())
                System.out.println("Current log directory is " + oldLog.getAbsolutePath());
            redirectBotLogToFile(new File("mirai.log"));
        }});
        dami.login();
        new MainListener().initListener(dami, properties);
        dami.join();
    }
}

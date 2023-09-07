package cn.maoookai;

import cn.maoookai.listener.MainListener;
import cn.maoookai.scheduler.LogFileScheduler;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.BotFactory;
import net.mamoe.mirai.auth.BotAuthorization;
import net.mamoe.mirai.utils.BotConfiguration;

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

        Bot dami = BotFactory.INSTANCE.newBot(Long.parseLong(properties.getProperty("qq.account")), BotAuthorization.byQRCode(), configuration -> {
            configuration.setProtocol(BotConfiguration.MiraiProtocol.ANDROID_WATCH);
            configuration.setHeartbeatStrategy(BotConfiguration.HeartbeatStrategy.STAT_HB);
            configuration.fileBasedDeviceInfo();
            configuration.redirectBotLogToDirectory(new File("log"));
        });
        dami.login();
        new MainListener().initListener(dami, properties);
        dami.join();
    }
}

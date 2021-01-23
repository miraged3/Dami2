package cn.maoookai;

import cn.maoookai.listener.MainListener;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.BotFactory;
import net.mamoe.mirai.utils.BotConfiguration;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class DamiMainApp {

    public static void main(String[] args) throws Exception {
        InputStream in = new FileInputStream("config.properties");
        Properties properties = new Properties();
        properties.load(in);
        Bot dami = BotFactory.INSTANCE.newBot(Long.parseLong(properties.getProperty("qq.account")), properties.getProperty("qq.password"), new BotConfiguration() {{
            setProtocol(MiraiProtocol.ANDROID_PAD);
            fileBasedDeviceInfo();
            File oldLog = new File("mirai.log");
            if (oldLog.exists())
                System.out.println("旧日志删除结果为" + oldLog.delete());
            System.out.println("当前日志目录为" + oldLog.getAbsolutePath() );
            redirectBotLogToFile(new File("mirai.log"));
        }});
        dami.login();

        new MainListener().initListener();
    }
}

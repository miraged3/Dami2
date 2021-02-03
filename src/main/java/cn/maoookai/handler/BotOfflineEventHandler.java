package cn.maoookai.handler;

import net.mamoe.mirai.Bot;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.event.events.BotOfflineEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Properties;

public class BotOfflineEventHandler {
    public void onOffline(@NotNull BotOfflineEvent event, @NotNull Bot bot, @NotNull Properties properties) {
        Contact admin = bot.getFriend(Long.parseLong(properties.getProperty("qq.admin")));
        assert admin != null;
        admin.sendMessage("因为" + event.toString() + "掉线了");
        admin.sendMessage("正在尝试重新连接..." + event.getReconnect());
    }
}

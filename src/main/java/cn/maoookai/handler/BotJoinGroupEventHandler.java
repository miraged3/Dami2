package cn.maoookai.handler;

import net.mamoe.mirai.Bot;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.event.events.BotJoinGroupEvent;

import java.util.Properties;

public class BotJoinGroupEventHandler {
    public void onEvent(BotJoinGroupEvent event, Bot bot, Properties properties) {
        Contact admin = bot.getFriend(Long.parseLong(properties.getProperty("qq.admin")));
        assert admin != null;
        admin.sendMessage("加入了群【" + event.getGroup().getName() + "】（" + event.getGroup().getId() + ("）"));
    }
}

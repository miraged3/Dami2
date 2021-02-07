package cn.maoookai.handler;

import net.mamoe.mirai.Bot;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.event.events.BotLeaveEvent;

import java.util.Properties;

public class BotLeaveEventHandler {
    public void onEvent(BotLeaveEvent event, Bot bot, Properties properties) {
        Contact admin = bot.getFriend(Long.parseLong(properties.getProperty("qq.admin")));
        assert admin != null;
        admin.sendMessage("从群【" + event.getGroup().getName() + "】（" + event.getGroup().getId() + ("）") + "中被踢出");
    }
}

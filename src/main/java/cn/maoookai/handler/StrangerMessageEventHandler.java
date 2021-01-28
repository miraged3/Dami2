package cn.maoookai.handler;

import net.mamoe.mirai.Bot;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.event.events.StrangerMessageEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Properties;

public class StrangerMessageEventHandler {
    public void onMessage(@NotNull StrangerMessageEvent event, Bot bot, Properties properties) {
        Contact admin = bot.getFriend(Long.parseLong(properties.getProperty("qq.admin")));
        assert admin != null;
        admin.sendMessage("陌生人" + event.getSender().getId() + event.getSender().getNick() + "发来了消息：" + event.getMessage().contentToString());
    }
}

package cn.maoookai.handler;

import net.mamoe.mirai.Bot;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.event.events.FriendMessageEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Properties;

public class FriendMessageEventHandler {
    public void onMessage(@NotNull FriendMessageEvent event, Bot bot, Properties properties) {
        Contact admin = bot.getFriend(Long.parseLong(properties.getProperty("qq.admin")));
        assert admin != null;
        admin.sendMessage("好友" + event.getSender().getId() + event.getSender().getNick() + "发来了消息：" + event.getMessage().contentToString());
    }
}

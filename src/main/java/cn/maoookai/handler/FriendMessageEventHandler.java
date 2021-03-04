package cn.maoookai.handler;

import cn.maoookai.util.HttpGetUtil;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.event.events.FriendMessageEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Properties;

public class FriendMessageEventHandler {
    public void onMessage(@NotNull FriendMessageEvent event, @NotNull Bot bot, @NotNull Properties properties) {
        //TODO: 图片转发
        Contact admin = bot.getFriend(Long.parseLong(properties.getProperty("qq.admin")));
        assert admin != null;
        admin.sendMessage("好友" + event.getSender().getId() + event.getSender().getNick() + "发来了消息：" + event.getMessage().contentToString());
        event.getSender().sendMessage(HttpGetUtil.getHttpPlainText("https://chp.shadiao.app/api.php"));
    }
}

package cn.maoookai.handler;

import net.mamoe.mirai.Bot;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.event.events.FriendMessageEvent;
import org.jetbrains.annotations.NotNull;

public class FriendMessageEventHandler {
    public void onMessage(@NotNull FriendMessageEvent event, Bot bot) {
        Contact lemon = bot.getFriend(1220568032L);
        assert lemon != null;
        lemon.sendMessage("test");
    }
}

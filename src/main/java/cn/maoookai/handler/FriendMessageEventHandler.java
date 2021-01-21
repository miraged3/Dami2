package cn.maoookai.handler;

import net.mamoe.mirai.event.events.FriendMessageEvent;
import org.jetbrains.annotations.NotNull;

public class FriendMessageEventHandler {
    public void onMessage(@NotNull FriendMessageEvent event) {
        System.out.println(event.toString());
        System.out.println(event.getSender().toString());
    }
}

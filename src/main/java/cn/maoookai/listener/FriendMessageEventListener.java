package cn.maoookai.listener;

import cn.maoookai.handler.FriendMessageEventHandler;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.event.GlobalEventChannel;
import net.mamoe.mirai.event.Listener;
import net.mamoe.mirai.event.events.FriendMessageEvent;

import java.io.IOException;
import java.util.Properties;

public class FriendMessageEventListener {

    public FriendMessageEventListener(Bot bot, Properties properties) {
        Listener<FriendMessageEvent> friendMessageEventListener;
        friendMessageEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(FriendMessageEvent.class,
                friendMessageEvent -> {
                    try {
                        new FriendMessageEventHandler().onMessage(friendMessageEvent, bot, properties);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
        friendMessageEventListener.start();
    }
}

package cn.maoookai.listener;

import cn.maoookai.handler.FriendMessageEventHandler;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.event.GlobalEventChannel;
import net.mamoe.mirai.event.Listener;
import net.mamoe.mirai.event.events.FriendMessageEvent;

public class FriendMessageListener extends MainListener {

    public FriendMessageListener(Bot bot) {
        Listener<FriendMessageEvent> friendMessageEventListener;
        friendMessageEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(FriendMessageEvent.class, friendMessageEvent -> new FriendMessageEventHandler().onMessage(friendMessageEvent, bot));
        friendMessageEventListener.start();
    }
}

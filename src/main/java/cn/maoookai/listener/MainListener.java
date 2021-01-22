package cn.maoookai.listener;

import cn.maoookai.handler.FriendMessageEventHandler;
import cn.maoookai.handler.GroupMessageEventHandler;
import cn.maoookai.handler.StrangerMessageEventHandler;
import net.mamoe.mirai.event.GlobalEventChannel;
import net.mamoe.mirai.event.Listener;
import net.mamoe.mirai.event.events.FriendMessageEvent;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.event.events.StrangerMessageEvent;

import java.io.IOException;

public class MainListener {

    Listener<FriendMessageEvent> friendMessageEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(FriendMessageEvent.class, friendMessageEvent -> new FriendMessageEventHandler().onMessage(friendMessageEvent));
    Listener<GroupMessageEvent> groupMessageEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(GroupMessageEvent.class, groupMessageEvent -> {
        try {
            new GroupMessageEventHandler().onMessage(groupMessageEvent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    });
    Listener<StrangerMessageEvent> strangerMessageListener = GlobalEventChannel.INSTANCE.subscribeAlways(StrangerMessageEvent.class, strangerMessageEvent -> new StrangerMessageEventHandler().onMessage(strangerMessageEvent));

    public void initListener() {
        friendMessageEventListener.start();
        groupMessageEventListener.start();
        strangerMessageListener.start();
    }

}

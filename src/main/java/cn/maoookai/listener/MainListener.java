package cn.maoookai.listener;

import cn.maoookai.handler.GroupMessageEventHandler;
import cn.maoookai.handler.StrangerMessageEventHandler;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.event.GlobalEventChannel;
import net.mamoe.mirai.event.Listener;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.event.events.StrangerMessageEvent;

import java.io.IOException;

public class MainListener {

    Listener<GroupMessageEvent> groupMessageEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(GroupMessageEvent.class, groupMessageEvent -> {
        try {
            new GroupMessageEventHandler().onMessage(groupMessageEvent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    });

    public void initListener(Bot bot) {
        new FriendMessageListener(bot);
        groupMessageEventListener.start();
    }
}

package cn.maoookai.listener;

import cn.maoookai.handler.GroupMessageEventHandler;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.event.GlobalEventChannel;
import net.mamoe.mirai.event.Listener;
import net.mamoe.mirai.event.events.GroupMessageEvent;

import java.io.IOException;
import java.util.Properties;

public class GroupMessageEventListener {

    public GroupMessageEventListener(Bot bot, Properties properties) {
        Listener<GroupMessageEvent> groupMessageEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(GroupMessageEvent.class, groupMessageEvent -> {
            try {
                new GroupMessageEventHandler().onMessage(groupMessageEvent, bot, properties);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        });
        groupMessageEventListener.start();
    }
}

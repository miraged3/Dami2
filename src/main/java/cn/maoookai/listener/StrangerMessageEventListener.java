package cn.maoookai.listener;

import cn.maoookai.handler.StrangerMessageEventHandler;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.event.GlobalEventChannel;
import net.mamoe.mirai.event.Listener;
import net.mamoe.mirai.event.events.StrangerMessageEvent;

import java.util.Properties;

public class StrangerMessageEventListener {
    public StrangerMessageEventListener(Bot bot, Properties properties) {
        Listener<StrangerMessageEvent> strangerMessageListener;
        strangerMessageListener = GlobalEventChannel.INSTANCE.subscribeAlways(StrangerMessageEvent.class, strangerMessageEvent -> new StrangerMessageEventHandler().onMessage(strangerMessageEvent, bot, properties));
        strangerMessageListener.start();
    }
}

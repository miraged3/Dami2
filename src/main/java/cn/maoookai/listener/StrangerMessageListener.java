package cn.maoookai.listener;

import cn.maoookai.handler.StrangerMessageEventHandler;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.event.GlobalEventChannel;
import net.mamoe.mirai.event.Listener;
import net.mamoe.mirai.event.events.StrangerMessageEvent;

public class StrangerMessageListener {
    public StrangerMessageListener(Bot bot) {
        Listener<StrangerMessageEvent> strangerMessageListener;
        strangerMessageListener = GlobalEventChannel.INSTANCE.subscribeAlways(StrangerMessageEvent.class, strangerMessageEvent -> new StrangerMessageEventHandler().onMessage(strangerMessageEvent));
        strangerMessageListener.start();
        //TODO: 陌生人消息转发给admin（使用properties）
    }
}

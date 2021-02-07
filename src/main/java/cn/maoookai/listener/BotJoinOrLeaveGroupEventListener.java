package cn.maoookai.listener;

import cn.maoookai.handler.BotJoinGroupEventHandler;
import cn.maoookai.handler.BotLeaveEventHandler;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.event.GlobalEventChannel;
import net.mamoe.mirai.event.Listener;
import net.mamoe.mirai.event.events.BotJoinGroupEvent;
import net.mamoe.mirai.event.events.BotLeaveEvent;

import java.util.Properties;

public class BotJoinOrLeaveGroupEventListener {
    public BotJoinOrLeaveGroupEventListener(Bot bot, Properties properties) {
        Listener<BotJoinGroupEvent> joinGroupEventListener;
        Listener<BotLeaveEvent> botLeaveEventListener;
        joinGroupEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(BotJoinGroupEvent.class, botJoinGroupEvent -> new BotJoinGroupEventHandler().onEvent(botJoinGroupEvent, bot, properties));
        botLeaveEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(BotLeaveEvent.class, botLeaveEvent -> new BotLeaveEventHandler().onEvent(botLeaveEvent, bot, properties));
        joinGroupEventListener.start();
        botLeaveEventListener.start();
    }
}

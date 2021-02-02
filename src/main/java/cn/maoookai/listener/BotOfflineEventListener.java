package cn.maoookai.listener;

import cn.maoookai.handler.BotOfflineEventHandler;
import cn.maoookai.handler.FriendMessageEventHandler;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.event.GlobalEventChannel;
import net.mamoe.mirai.event.Listener;
import net.mamoe.mirai.event.events.BotOfflineEvent;

import java.util.Properties;

public class BotOfflineEventListener {
    public BotOfflineEventListener(Bot bot, Properties properties){
        Listener<BotOfflineEvent> botOfflineEventListener;
        botOfflineEventListener= GlobalEventChannel.INSTANCE.subscribeAlways(BotOfflineEvent.class,botOfflineEvent -> new BotOfflineEventHandler().onOffline(botOfflineEvent, bot, properties));
    }
}

package cn.maoookai.listener;

import cn.maoookai.handler.BotGroupPermissionChangeEventHandler;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.event.GlobalEventChannel;
import net.mamoe.mirai.event.Listener;
import net.mamoe.mirai.event.events.BotGroupPermissionChangeEvent;

import java.util.Properties;

public class BotGroupPermissionChangeEventListener {
    public BotGroupPermissionChangeEventListener(Bot bot, Properties properties) {
        Listener<BotGroupPermissionChangeEvent> listener = GlobalEventChannel.INSTANCE.subscribeAlways(BotGroupPermissionChangeEvent.class, botGroupPermissionChangeEvent -> new BotGroupPermissionChangeEventHandler().onEvent(botGroupPermissionChangeEvent, bot, properties));
        listener.start();
    }
}

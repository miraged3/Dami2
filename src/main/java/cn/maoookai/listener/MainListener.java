package cn.maoookai.listener;

import net.mamoe.mirai.Bot;

import java.util.Properties;

public class MainListener {
    public void initListener(Bot bot, Properties properties) {
        //TODO: 其他事件的监听
        new BotOfflineEventListener(bot, properties);
        new FriendMessageListener(bot, properties);
        new StrangerMessageListener(bot, properties);
        new GroupMessageListener(bot, properties);
        new MemberJoinEventListener(bot, properties);
        new BotGroupPermissionChangeEventListener(bot, properties);
    }
}

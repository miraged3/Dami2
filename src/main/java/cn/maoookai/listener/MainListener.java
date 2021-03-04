package cn.maoookai.listener;

import net.mamoe.mirai.Bot;

import java.util.Properties;

public class MainListener {
    public void initListener(Bot bot, Properties properties) {
        new BotOfflineEventListener(bot, properties);
        new FriendMessageEventListener(bot, properties);
        new StrangerMessageEventListener(bot, properties);
        new GroupMessageEventListener(bot, properties);
        new MemberJoinEventListener(bot, properties);
        new BotGroupPermissionChangeEventListener(bot, properties);
        new BotJoinOrLeaveGroupEventListener(bot,properties);
    }
}

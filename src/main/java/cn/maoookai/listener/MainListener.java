package cn.maoookai.listener;

import net.mamoe.mirai.Bot;

public class MainListener {
    public void initListener(Bot bot) {
        new FriendMessageListener(bot);
        new StrangerMessageListener(bot);
        new GroupMessageListener(bot);
    }
}

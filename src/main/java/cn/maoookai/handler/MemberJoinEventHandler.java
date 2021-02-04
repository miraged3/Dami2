package cn.maoookai.handler;

import net.mamoe.mirai.Bot;
import net.mamoe.mirai.event.events.MemberJoinEvent;
import net.mamoe.mirai.message.data.At;
import net.mamoe.mirai.message.data.PlainText;
import org.jetbrains.annotations.NotNull;

import java.util.Properties;

public class MemberJoinEventHandler {
    public void onJoin(@NotNull MemberJoinEvent event, Bot bot, @NotNull Properties properties) {
        if (event.getGroup().getId() == Long.parseLong(properties.getProperty("kro.group")))
            event.getGroup().sendMessage(new PlainText("欢迎【").plus(new At(event.getMember().getId())).plus("】加入本通知组！\n指令列表：\n/down：获取下载\n/sub：获取订阅"));
    }
}

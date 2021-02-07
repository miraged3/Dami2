package cn.maoookai.listener;

import cn.maoookai.handler.MemberJoinEventHandler;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.event.GlobalEventChannel;
import net.mamoe.mirai.event.Listener;
import net.mamoe.mirai.event.events.MemberJoinEvent;

import java.util.Properties;

public class MemberJoinEventListener {
    public MemberJoinEventListener(Bot bot, Properties properties) {
        Listener<MemberJoinEvent> memberJoinEventListener;
        memberJoinEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(MemberJoinEvent.class, memberJoinEvent -> new MemberJoinEventHandler().onJoin(memberJoinEvent, bot, properties));
        memberJoinEventListener.start();
    }
}

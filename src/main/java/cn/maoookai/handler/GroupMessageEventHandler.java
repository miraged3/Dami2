package cn.maoookai.handler;

import net.mamoe.mirai.event.events.GroupMessageEvent;
import org.jetbrains.annotations.NotNull;

public class GroupMessageEventHandler {
    public void onMessage(@NotNull GroupMessageEvent event){
        System.out.println(event.toString());
        System.out.println(event.getMessage().contentToString());
    }
}

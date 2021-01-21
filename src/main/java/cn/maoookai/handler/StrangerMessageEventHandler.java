package cn.maoookai.handler;

import net.mamoe.mirai.event.events.StrangerMessageEvent;
import org.jetbrains.annotations.NotNull;

public class StrangerMessageEventHandler {
    public void onMessage(@NotNull StrangerMessageEvent event) {
        System.out.println(event.toString());
        System.out.println(event.getMessage().contentToString());
    }
}

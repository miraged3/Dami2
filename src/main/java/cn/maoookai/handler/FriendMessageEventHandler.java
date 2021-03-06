package cn.maoookai.handler;

import cn.maoookai.service.LogService;
import cn.maoookai.service.impl.LogServiceImpl;
import cn.maoookai.util.HttpGetUtil;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.event.events.FriendMessageEvent;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

public class FriendMessageEventHandler {
    public void onMessage(@NotNull FriendMessageEvent event, @NotNull Bot bot, @NotNull Properties properties) throws IOException {
        String message = event.getMessage().contentToString();
        Contact admin = bot.getFriend(Long.parseLong(properties.getProperty("qq.admin")));
        assert admin != null;
        admin.sendMessage("好友" + event.getSender().getId() + event.getSender().getNick() + "发来了消息：" + message);
        event.getSender().sendMessage(HttpGetUtil.getHttpPlainText("https://chp.shadiao.app/api.php"));

        //主人指令
        if (event.getSender().getId()== admin.getId()){
            if (message.contains("log")){
                LogService logService =new LogServiceImpl();
                if (message.contains(" ")){
                    event.getSender().sendMessage(logService.log(Long.parseLong(message.split(" ")[1])));
                }
            }
        }
    }
}

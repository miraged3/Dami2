package cn.maoookai.handler;

import net.mamoe.mirai.Bot;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.event.events.BotGroupPermissionChangeEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Properties;

public class BotGroupPermissionChangeEventHandler {
    public void onEvent(@NotNull BotGroupPermissionChangeEvent event, @NotNull Bot bot, @NotNull Properties properties) {
        Contact admin = bot.getFriend(Long.parseLong(properties.getProperty("qq.admin")));
        assert admin != null;
        switch (event.getNew()) {
            case MEMBER:
                admin.sendMessage("在群【" + event.getGroup().getName() + "】（" + event.getGroup().getId() + "）中变为普通成员");
                break;
            case ADMINISTRATOR:
                admin.sendMessage("在群【" + event.getGroup().getName() + "】（" + event.getGroup().getId() + "）中变为管理员");
                break;
            case OWNER:
                admin.sendMessage("在群【" + event.getGroup().getName() + "】（" + event.getGroup().getId() + "）中变为群主");
                break;
        }
    }
}

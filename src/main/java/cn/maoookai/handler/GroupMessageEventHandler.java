package cn.maoookai.handler;

import cn.maoookai.DamiMainApp;
import cn.maoookai.util.FileReadUtil;
import cn.maoookai.util.ImageStitchUtil;
import cn.maoookai.util.RandomNumberUtil;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.contact.AnonymousMember;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.contact.Member;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.*;
import net.mamoe.mirai.utils.ExternalResource;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class GroupMessageEventHandler extends DamiMainApp {
    static String miraiResPath = "res/";
    static ArrayList<File> rSet;
    static ArrayList<File> srSet;
    static ArrayList<File> ssrSet;
    static ArrayList<File> spSet;

    static {
        try {
            rSet = FileReadUtil.getFiles(miraiResPath + "R");
            srSet = FileReadUtil.getFiles(miraiResPath + "SR");
            ssrSet = FileReadUtil.getFiles(miraiResPath + "SSR");
            spSet = FileReadUtil.getFiles(miraiResPath + "SP");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //TODO: 式神图片优化、图片拼接优化
    static File summon() {
        File summonResult;
        int result = RandomNumberUtil.getRandomNumber(1000);
        if (result < 762)
            summonResult = rSet.get(RandomNumberUtil.getRandomNumber(rSet.size()));
        else if (result < 962)
            summonResult = srSet.get(RandomNumberUtil.getRandomNumber(srSet.size()));
        else if (result < 987)
            summonResult = ssrSet.get(RandomNumberUtil.getRandomNumber(ssrSet.size()));
        else
            summonResult = spSet.get(RandomNumberUtil.getRandomNumber(spSet.size()));
        return summonResult;
    }

    public void onMessage(@NotNull GroupMessageEvent event, Bot bot) throws IOException {

        Contact fromGroup = event.getGroup();
        MessageChain messages = event.getMessage();
        String messageContent = messages.contentToString();

        if (event.getMessage().contentToString().equals("抽卡")) {
            Image summonImage = fromGroup.uploadImage(ExternalResource.create(summon()));
            fromGroup.sendMessage(new At(event.getSender().getId()).plus(summonImage));
        }
        if (event.getMessage().contentToString().equals("十连")) {
            File summonResult = summon();
            for (int i = 0; i < 9; i++) {
                summonResult = ImageStitchUtil.bufferedToFile(summonResult, summon());
            }
            Image summonImage = fromGroup.uploadImage(ExternalResource.create(summonResult));
            fromGroup.sendMessage(new At(event.getSender().getId()).plus(summonImage));
        }

    }
}

package cn.maoookai.handler;

import cn.maoookai.service.impl.DailyEnglishServiceImpl;
import cn.maoookai.util.FileReadUtil;
import cn.maoookai.util.HttpGetUtil;
import cn.maoookai.util.ImageStitchUtil;
import cn.maoookai.util.RandomNumberUtil;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.At;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.utils.ExternalResource;
import net.sf.json.JSONObject;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Properties;

import static cn.maoookai.util.HttpPostUtil.getArray;
import static cn.maoookai.util.HttpPostUtil.send;

public class GroupMessageEventHandler {

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

    private File summon() {
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

    private File summonOne() {
        return summon();
    }

    @NotNull
    private ArrayList<File> summonTenTimes() {
        ArrayList<File> listResult = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            listResult.add(summon());
        }
        return listResult;
    }

    public void onMessage(@NotNull GroupMessageEvent event, Bot bot, Properties properties) throws IOException, InterruptedException, ParseException {

        Contact fromGroup = event.getGroup();
        MessageChain messages = event.getMessage();
        String messageContent = messages.contentToString();

        if (messageContent.equals("抽卡")) {
            Image summonImage = fromGroup.uploadImage(ExternalResource.create(summonOne()));
            fromGroup.sendMessage(new At(event.getSender().getId()).plus("你召唤出了：").plus(summonImage));
            return;
        }
        if (messageContent.equals("十连")) {
            ArrayList<File> result = summonTenTimes();
            File pic = ImageStitchUtil.bufferedToFile(result);
            Image summonImage = fromGroup.uploadImage(ExternalResource.create(pic));
            fromGroup.sendMessage(new At(event.getSender().getId()).plus("你召唤出了：").plus(summonImage));
            return;
        }

        if (messageContent.equals("正能量") || messageContent.equals("每日一句") || messageContent.equals("学英语")) {
            DailyEnglishServiceImpl dailyEnglishService = new DailyEnglishServiceImpl();
            fromGroup.sendMessage(dailyEnglishService.today(properties.getProperty("daily.url")));
            return;
        }

        if (messageContent.contains("就不能")) {
            if (RandomNumberUtil.getRandomNumber(100) > 50) {
                Thread.sleep(5000);
                event.getGroup().sendMessage("不能");
            }
            return;
        }

        if (messageContent.contains("为什么")) {
            if (RandomNumberUtil.getRandomNumber(100) > 95) {
                Thread.sleep(5000);
                event.getGroup().sendMessage("因为，" + HttpGetUtil.getHttpPlainText("https://chp.shadiao.app/api.php"));
            }
            return;
        }

        for (String curse : properties.getProperty("curse").split(",")) {
            if (messageContent.contains(curse)) {
                event.getGroup().sendMessage(HttpGetUtil.getHttpPlainText("https://zuanbot.com/api.php?level=min&lang=zh_cn"));
                return;
            }
        }

        if (messageContent.matches("[a-zA-Z]+") && RandomNumberUtil.getRandomNumber(100) > 50) {
            JSONObject secretCode = JSONObject.fromObject(send("https://lab.magiconch.com/api/nbnhhsh/guess", new JSONObject().accumulate("text", messageContent)));
            event.getGroup().sendMessage(Objects.requireNonNull(getArray(secretCode, messageContent)).get(RandomNumberUtil.getRandomNumber(Objects.requireNonNull(getArray(secretCode, messageContent)).size())));
            return;
        }

        if (RandomNumberUtil.getRandomNumber(1000) < 2) {
            Thread.sleep(5000);
            event.getGroup().sendMessage(HttpGetUtil.getHttpPlainText("https://chp.shadiao.app/api.php"));
        }




    }
}

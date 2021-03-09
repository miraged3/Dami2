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

    //TODO: 式神图片优化
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
        }
        if (messageContent.equals("十连")) {
            ArrayList<File> result = summonTenTimes();
            File pic = ImageStitchUtil.bufferedToFile(result);
            Image summonImage = fromGroup.uploadImage(ExternalResource.create(pic));
            fromGroup.sendMessage(new At(event.getSender().getId()).plus("你召唤出了：").plus(summonImage));
        }

        if (messageContent.equals("正能量") || messageContent.equals("每日一句") || messageContent.equals("学英语")) {
            DailyEnglishServiceImpl dailyEnglishService = new DailyEnglishServiceImpl();
            fromGroup.sendMessage(dailyEnglishService.today(properties.getProperty("daily.url")));
        }

        if (messageContent.contains("就不能")) {
            if (RandomNumberUtil.getRandomNumber(100) > 50) {
                event.getGroup().sendMessage("不能");
                Thread.sleep(2000);
            }
        }

        if (messageContent.contains("为什么")) {
            if (RandomNumberUtil.getRandomNumber(100) > 50) {
                event.getGroup().sendMessage("不知道");
                Thread.sleep(2000);
            }
        }

        if (messageContent.equals("/fart"))
            event.getGroup().sendMessage(HttpGetUtil.getHttpPlainText("https://chp.shadiao.app/api.php"));

        if (messageContent.equals("/zuan"))
            event.getGroup().sendMessage(HttpGetUtil.getHttpPlainText("https://zuanbot.com/api.php?level=min&lang=zh_cn"));

        if (messageContent.matches("[a-zA-Z]+")) {
            JSONObject secretCode = JSONObject.fromObject(send("https://lab.magiconch.com/api/nbnhhsh/guess", new JSONObject().accumulate("text", messageContent)));
            event.getGroup().sendMessage(Objects.requireNonNull(getArray(secretCode)).get(RandomNumberUtil.getRandomNumber(Objects.requireNonNull(getArray(secretCode)).size())));
        }

        //Put these codes at the end of the function
        if (event.getGroup().getId() == Long.parseLong(properties.getProperty("kro.group"))) {
            switch (messageContent) {
                case "/down":
                    event.getGroup().sendMessage(new At(event.getSender().getId()).plus("选择你的平台：\n/and：Android端\n/win：Windows端\n/mac：macOS端\n/ios：iOS端"));
                    break;
                case "/and":
                    event.getGroup().sendMessage(new At(event.getSender().getId()).plus("Android端：\nClash下载：").plus(properties.getProperty("kro.androidClash")).plus("\nV2R下载：").plus(properties.getProperty("kro.androidV2")));
                    break;
                case "/win":
                    event.getGroup().sendMessage(new At(event.getSender().getId()).plus("Windows端：\nClash下载：").plus(properties.getProperty("kro.winClash")).plus("\nV2R下载：").plus(properties.getProperty("kro.winV2")));
                    break;
                case "/mac":
                    event.getGroup().sendMessage(new At(event.getSender().getId()).plus("macOS端：\nClash下载：").plus(properties.getProperty("kro.macClash")));
                    break;
                case "/ios":
                case "/iOS":
                case "/IOS":
                    event.getGroup().sendMessage(new At(event.getSender().getId()).plus("iOS端：\n需要联系克罗西丁获取 Apple Store 美区账号"));
                    break;
                case "/sub":
                    event.getGroup().sendMessage(new At(event.getSender().getId()).plus("获取哪一个订阅？\n回复指令后复制订阅节点:\n/clash\n/v2\n/Quant\n/QuantX"));
                    break;
                case "/clash":
                    event.getGroup().sendMessage(new At(event.getSender().getId()).plus(properties.getProperty("kro.clash")));
                    break;
                case "/v2":
                    event.getGroup().sendMessage(new At(event.getSender().getId()).plus(properties.getProperty("kro.v2")));
                    break;
                case "/quant":
                    event.getGroup().sendMessage(new At(event.getSender().getId()).plus(properties.getProperty("kro.quant")));
                    break;
                case "/quantx":
                    event.getGroup().sendMessage(new At(event.getSender().getId()).plus(properties.getProperty("kro.quantx")));
                    break;
            }
        }

    }
}

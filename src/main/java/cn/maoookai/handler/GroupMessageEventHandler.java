package cn.maoookai.handler;

import cn.maoookai.service.impl.DailyEnglishServiceImpl;
import cn.maoookai.util.FileReadUtil;
import cn.maoookai.util.ImageStitchUtil;
import cn.maoookai.util.RandomNumberUtil;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.At;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.utils.ExternalResource;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

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

    //TODO: 式神图片优化、图片拼接优化
    private File summonOne() {
        return summon();
    }

    @NotNull
    private List<File> summonTenTimes() {
        List<File> listResult = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            listResult.add(summon());
        }
        return listResult;
    }

    public void onMessage(@NotNull GroupMessageEvent event, Bot bot, Properties properties) throws IOException {

        Contact fromGroup = event.getGroup();
        MessageChain messages = event.getMessage();
        String messageContent = messages.contentToString();

        if (messageContent.equals("抽卡")) {
            Image summonImage = fromGroup.uploadImage(ExternalResource.create(summonOne()));
            fromGroup.sendMessage(new At(event.getSender().getId()).plus("你召唤出了：").plus(summonImage));
        }
        if (messageContent.equals("十连")) {
            List<File> result = summonTenTimes();
            File summonImage1 = result.get(0);
            File summonImage2 = result.get(4);
            File summonImage3 = new File("res/null.jpg");
            for (int i = 1; i < 4; i++)
                summonImage1 = ImageStitchUtil.bufferedToFile(summonImage1, result.get(i), true);
            for (int i = 5; i < 8; i++)
                summonImage2 = ImageStitchUtil.bufferedToFile(summonImage2, result.get(i), true);
            summonImage3 = ImageStitchUtil.bufferedToFile(summonImage3, result.get(8), true);
            summonImage3 = ImageStitchUtil.bufferedToFile(summonImage3, result.get(9), true);
            summonImage3 = ImageStitchUtil.bufferedToFile(summonImage3, new File("res/null.jpg"), true);
            File summonImage4 = ImageStitchUtil.bufferedToFile(summonImage1, summonImage2, false);
//            summonImage4 = ImageStitchUtil.bufferedToFile(summonImage4, summonImage3, false);
            Image summonImage = fromGroup.uploadImage(ExternalResource.create(summonImage4));
            fromGroup.sendMessage(new At(event.getSender().getId()).plus("你召唤出了：").plus(result.toString()).plus(summonImage));
        }

        if (messageContent.equals("正能量") || messageContent.equals("每日一句") || messageContent.equals("学英语")) {
            DailyEnglishServiceImpl dailyEnglishService = new DailyEnglishServiceImpl();
            fromGroup.sendMessage(dailyEnglishService.getTodayEnglish(properties.getProperty("daily.url")));
        }

    }
}

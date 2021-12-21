package cn.maoookai.handler;

import cn.maoookai.service.impl.DailyEnglishServiceImpl;
import cn.maoookai.util.FileReadUtil;
import cn.maoookai.util.HttpGetUtil;
import cn.maoookai.util.ImageStitchUtil;
import cn.maoookai.util.RandomNumberUtil;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.At;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.PlainText;
import net.mamoe.mirai.utils.ExternalResource;
import net.sf.json.JSONObject;
import org.apache.commons.io.FileUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Base64;
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

    public void onMessage(@NotNull GroupMessageEvent event, Properties properties) throws IOException, InterruptedException, ParseException {

        Contact fromGroup = event.getGroup();
        MessageChain messages = event.getMessage();
        String messageContent = messages.contentToString();

        if (messageContent.equals("抽卡")) {
            RandomAccessFile randomAccessFile = new RandomAccessFile(summonOne(), "r");
            Image summonImage = fromGroup.uploadImage(ExternalResource.create(randomAccessFile, "png", true));
            fromGroup.sendMessage(new At(event.getSender().getId()).plus("你召唤出了：").plus(summonImage));
            return;
        }
        if (messageContent.equals("十连")) {
            ArrayList<File> result = summonTenTimes();
            File pic = ImageStitchUtil.bufferedToFile(result);
            RandomAccessFile randomAccessFile = new RandomAccessFile(pic, "r");
            Image summonImage = fromGroup.uploadImage(ExternalResource.create(randomAccessFile, "png", true));
            fromGroup.sendMessage(new At(event.getSender().getId()).plus("你召唤出了：").plus(summonImage));
            return;
        }

        if (messageContent.equals("正能量") || messageContent.equals("每日一句") || messageContent.equals("学英语")) {
            DailyEnglishServiceImpl dailyEnglishService = new DailyEnglishServiceImpl();
            fromGroup.sendMessage(dailyEnglishService.today(properties.getProperty("daily.url")));
            return;
        }

        if (messageContent.startsWith("图片")) {
            HttpClient httpClient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(properties.getProperty("imageAddress"));
            String keyword = messageContent.replace("图片", "");
            String raw = "{\"keyword\":\"" + keyword + "\"}";
            StringEntity stringEntity = new StringEntity(raw, "UTF-8");
            stringEntity.setContentType("application/json");
            httpPost.setEntity(stringEntity);
            System.out.println(keyword);
            HttpResponse httpResponse = httpClient.execute(httpPost);
            com.alibaba.fastjson.JSONObject jsonObject = com.alibaba.fastjson.JSONObject.parseObject(EntityUtils.toString(httpResponse.getEntity()));
            String base64 = jsonObject.getString("image");
            File file = new File("img");
            byte[] decodedBytes = Base64.getDecoder().decode(base64);
            FileUtils.writeByteArrayToFile(file, decodedBytes);
            RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");
            Image baiduImage = fromGroup.uploadImage(ExternalResource.create(randomAccessFile, "jpg", true));
            fromGroup.sendMessage(new At(event.getSender().getId()).plus("你要的" + keyword + "：").plus(baiduImage));
            return;
        }

        if (messageContent.contains("就不能")) {
            if (RandomNumberUtil.getRandomNumber(100) < 5) {
                Thread.sleep(4000);
                fromGroup.sendMessage("不能");
            }
            return;
        }

        if (messageContent.endsWith("吗？") || messageContent.endsWith("吗") || messageContent.endsWith("吗?")) {
            if (RandomNumberUtil.getRandomNumber(100) < 3) {
                Thread.sleep(4000);
                fromGroup.sendMessage(messageContent.split("吗")[0] + "！");
            }
            return;
        }

        for (String curse : properties.getProperty("curse").split(",")) {
            if (messageContent.contains(curse)) {
                fromGroup.sendMessage(HttpGetUtil.getHttpPlainText("https://zuanbot.com/api.php?level=min&lang=zh_cn"));
                return;
            }
        }

        if (messageContent.matches("[a-zA-Z]+") && RandomNumberUtil.getRandomNumber(100) > 50) {
            JSONObject secretCode = JSONObject.fromObject(send("https://lab.magiconch.com/api/nbnhhsh/guess", new JSONObject().accumulate("text", messageContent)));
            fromGroup.sendMessage(Objects.requireNonNull(getArray(secretCode, messageContent)).get(RandomNumberUtil.getRandomNumber(Objects.requireNonNull(getArray(secretCode, messageContent)).size())));
            return;
        }

        if (RandomNumberUtil.getRandomNumber(1000) > 997) {
            HttpClient httpClient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(properties.getProperty("address"));
            String raw = "{\"keyword\":\"" + messageContent + "\"}";
            StringEntity stringEntity = new StringEntity(raw, "UTF-8");
            stringEntity.setContentType("application/json");
            httpPost.setEntity(stringEntity);
            HttpResponse httpResponse = httpClient.execute(httpPost);
            com.alibaba.fastjson.JSONObject jsonObject = com.alibaba.fastjson.JSONObject.parseObject(EntityUtils.toString(httpResponse.getEntity()));
            String base64 = jsonObject.getString("image");
            File file = new File("img");
            byte[] decodedBytes = Base64.getDecoder().decode(base64);
            FileUtils.writeByteArrayToFile(file, decodedBytes);
            RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");
            Image baiduImage = fromGroup.uploadImage(ExternalResource.create(randomAccessFile, "jpg", true));
            fromGroup.sendMessage(new PlainText(messageContent + "？").plus(baiduImage));
            return;
        }

        if (RandomNumberUtil.getRandomNumber(1000) < 1) {
            if (RandomNumberUtil.getRandomNumber(1) > 0) {
                HttpClient httpClient = HttpClients.createDefault();
                HttpPost httpPost = new HttpPost(properties.getProperty("yinAddress"));
                String raw = "{\"message\":\"" + messageContent + "\"}";
                StringEntity stringEntity = new StringEntity(raw, "UTF-8");
                stringEntity.setContentType("application/json");
                httpPost.setEntity(stringEntity);
                HttpResponse httpResponse = httpClient.execute(httpPost);
                com.alibaba.fastjson.JSONObject jsonObject = com.alibaba.fastjson.JSONObject.parseObject(EntityUtils.toString(httpResponse.getEntity()));
                String yinglish = jsonObject.getString("yinglish");
                fromGroup.sendMessage(yinglish);
            } else if (RandomNumberUtil.getRandomNumber(1) > 0) {
                Thread.sleep(3000);
                fromGroup.sendMessage(messageContent);
            } else {
                Thread.sleep(4000);
                fromGroup.sendMessage(HttpGetUtil.getHttpPlainText("https://chp.shadiao.app/api.php"));
            }
        }
    }
}

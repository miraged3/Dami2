package cn.maoookai.service;

import cn.maoookai.util.JsonUtil;
import net.sf.json.JSONObject;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DailyEnglishService {

    private static String icibaUrl;

    static {
        InputStream in;
        try {
            in = new FileInputStream("config.properties");
            Properties properties = new Properties();
            properties.load(in);
            icibaUrl = properties.getProperty("dailyEnglish.url");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @NotNull
    @Contract(" -> new")
    public static String getDailyEnglish() {
        return getCurrentDateFromDailySentence() + " 每日一句\n" + getDailySentenceEnglish() + '\n' + getDailySentenceChinese();
    }

    static String getDailySentenceEnglish() {
        JSONObject getDailySentence = JSONObject.fromObject(JsonUtil.jsonHandler(icibaUrl));
        return getDailySentence.getString("content");
    }

    static String getDailySentenceChinese() {
        JSONObject getDailySentence = JSONObject.fromObject(JsonUtil.jsonHandler(icibaUrl));
        return getDailySentence.getString("note");
    }

    static String getCurrentDateFromDailySentence() {
        JSONObject getDailySentence = JSONObject.fromObject(JsonUtil.jsonHandler(icibaUrl));
        return getDailySentence.getString("dateline");
    }

}

package cn.maoookai.service;

import cn.maoookai.util.JsonUtil;
import net.sf.json.JSONObject;
import org.junit.Test;

import java.io.*;
import java.util.Properties;

public class DailyEnglishService {

    private static String icibaUrl;

    static {
        InputStream in;
        try {
            in = new FileInputStream("config.properties");
            Properties properties = new Properties();
            properties.load(in);
            icibaUrl=properties.getProperty("dailyEnglish.url");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getDailySentenceEnglish() {
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

    @Test
    public void test(){
        System.out.println(getCurrentDateFromDailySentence() + " √ø»’“ªæ‰\n" + getDailySentenceEnglish() + '\n' + getDailySentenceChinese());
    }

}

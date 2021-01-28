package cn.maoookai.service.impl;

import cn.maoookai.service.DailyEnglishService;
import cn.maoookai.util.JsonUtil;
import net.sf.json.JSONObject;

public class DailyEnglishServiceImpl implements DailyEnglishService {

    public String getTodayEnglish(String url) {
        JSONObject getDailySentence = JSONObject.fromObject(JsonUtil.jsonHandler(url));
        return getDailySentence.getString("dateline") + " 每日一句\n" + getDailySentence.getString("content") + '\n' + getDailySentence.getString("note");
    }

    @Override
    public String getHistoryEnglish() {
        return null;
    }
}

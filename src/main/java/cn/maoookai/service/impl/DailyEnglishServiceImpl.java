package cn.maoookai.service.impl;

import cn.maoookai.service.DailyEnglishService;
import cn.maoookai.util.HttpGetUtil;
import net.sf.json.JSONObject;

public class DailyEnglishServiceImpl implements DailyEnglishService {

    @Override
    public String today(String url) {
        JSONObject getDailySentence = JSONObject.fromObject(HttpGetUtil.getHttpPlainText(url));
        return getDailySentence.getString("dateline") + " 每日一句\n" + getDailySentence.getString("content") + '\n' + getDailySentence.getString("note");
    }

}

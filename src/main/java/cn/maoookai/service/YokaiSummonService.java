package cn.maoookai.service;

import cn.maoookai.util.RandomNumberUtil;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.util.Random;

import static cn.maoookai.util.YokaiUtil.*;

public class YokaiSummonService {

    static String ssrSummoner() {
        int result = RandomNumberUtil.getRandomNumber(SSR.length);
        return (SSR[result]);
    }

    static String spSummoner() {
        int result = RandomNumberUtil.getRandomNumber(SP.length);
        return (SP[result]);
    }

    static String srSummoner() {
        int result = RandomNumberUtil.getRandomNumber(SR.length);
        return (SR[result]);
    }

    static String rSummoner() {
        int result = RandomNumberUtil.getRandomNumber(R.length);
        return (R[result]);
    }

    @NotNull
    static String summon() {
        Random random = new Random();
        String result;
        int cardPosition = random.nextInt(1000);
        if (cardPosition < 762)
            result = "R" + '\t' + rSummoner();
        else if (cardPosition < 962)
            result = "SR" + '\t' + srSummoner();
        else if (cardPosition < 987)
            result = "SSR" + '\t' + ssrSummoner();
        else
            result = "SP" + '\t' + spSummoner();
        return result;
    }

    @NotNull
    static String summonTenTimes() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            result.append(summon()).append('\n');
        }
        return result.toString();
    }

    @Test
    public void test(){
        System.out.println(summonTenTimes());
    }

}

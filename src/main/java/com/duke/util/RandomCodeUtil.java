package com.duke.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

/**
 * Created by dubaojiao on 2017/12/5.
 */
public class RandomCodeUtil {
    public static final String numberChar = "0123456789";
    /**
     * 年月日时分秒毫秒 + 三位随机数
     * 4+2+2+2+2+2+3+3
     * @return
     */
    public static String getTimeCode(){
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        StringBuffer stringBuffer = new StringBuffer(format.format(new Date()));
        return stringBuffer.append(randomNum(3)).toString();
    }

    /**
     * 获取num 位的一个随机数
     * @param length
     * @return
     */
    public static String randomNum(int length){
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i <length; i++) {
            sb.append(numberChar.charAt(random.nextInt(numberChar.length())));
        }
        return sb.toString();
    }

    /**
     * 前缀为 prefix 年月日时分秒毫秒 + 三位随机数
     * @param prefix 获取的编码的前缀
     * @return
     */
    public static String getTimeCode(String prefix){
        StringBuffer stringBuffer = new StringBuffer(prefix);
        return stringBuffer.append(getTimeCode()).toString();
    }

    /**
     * 获取32位的一个 uuid
     * @return
     */
    public static String getUUID(){
       return UUID.randomUUID().toString().replace("-", "");
    }

}

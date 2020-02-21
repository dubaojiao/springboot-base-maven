package com.duke.util;

/**
 * Created by dubaojiao on 2017/12/5.
 */
public class CheckUtil {
    /**
     * 字符串是否为null
     * @param str
     * @return
     */
    public static boolean stringIsNull(String str){
        if(str == null || str.isEmpty() || "".equals(str) || str.length() == 0){
            return true;
        }
        return false;
    }

    /**
     * Intger是否为null
     * @param object
     * @return
     */
    public static boolean integerIsNull(Integer object){
        if(null == object  || object == 0){
            return true;
        }
        return false;
    }
    /**
     * 对象是否为null
     * @param object
     * @return
     */
    public static boolean objectIsNull(Object object){
        if(object == null ){
            return true;
        }
        return false;
    }

    /**
     * 数组是否为null
     * @param arr
     * @return
     */
    public static boolean arrayIsNull(Object[] arr){
        if(arr == null || arr.length == 0){
            return true;
        }
        return false;
    }

}

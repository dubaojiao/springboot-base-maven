package com.duke.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期时间工具类
 * @author YunBo
 * @date   2018/01/12
 */
public final class TimeUtils {
    /**
     * 判断是否为同一天
     * @param dateA 日期A
     * @param dateB 日期B
     * @return 结果
     */
    public static Boolean isSameDay(Date dateA,Date dateB)
    {
        Calendar calDateA = Calendar.getInstance();
        calDateA.setTime(dateA);

        Calendar calDateB = Calendar.getInstance();
        calDateB.setTime(dateB);

        return calDateA.get(Calendar.YEAR) == calDateB.get(Calendar.YEAR)
                && calDateA.get(Calendar.MONTH) == calDateB.get(Calendar.MONTH)
                &&  calDateA.get(Calendar.DAY_OF_MONTH) == calDateB.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 日期相加
     * @param date
     * @param type
     * @param num
     * @return
     */
    public static Date dateAdd(Date date,Integer type, Integer num)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        switch (type)
        {
            case Calendar.YEAR:
                calendar.add(Calendar.YEAR,num);
                break;
            case Calendar.MONTH:
                calendar.add(Calendar.MONTH,num);
                break;
            case Calendar.DAY_OF_YEAR:
                calendar.add(Calendar.DAY_OF_YEAR,num);
                break;
        }
        return calendar.getTime();
    }

    /**
     * 获取年 月 日 时
     * @param d
     * @return
     */
    public static  Integer[] getYMD(Date d) {
        LocalDate date = UDateToLocalDate(d);
        LocalDateTime time = UDateToLocalDateTime(d);
        Integer[] ret = new Integer[]{date.getYear(),date.getMonthValue(),date.getDayOfMonth(),time.getHour()};
        return ret;
    }


    public static LocalDateTime UDateToLocalDateTime(Date date) {
        Instant instant = date.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        return LocalDateTime.ofInstant(instant, zone);
    }

    public static LocalDate UDateToLocalDate(Date date) {
        Instant instant = date.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
        return localDateTime.toLocalDate();
    }

    /**
     * 获取当日 是第几周
     * @param day
     * @return
     */
    public static Integer getWeek(Integer day){
        if(day<8){
            return 1;
        }else if(day<15){
            return 2;
        } else  if(day < 22){
            return 3;
        }else if(day < 29){
            return 4;
        }else {
            return 5;
        }
    }
    private static LocalDate nowDate;
    private static LocalDateTime nowTime;
    public static String getCompleteDate(){
        nowDate = LocalDate.now();
        nowTime = LocalDateTime.now();
        StringBuffer  stringBuffer = new StringBuffer(nowDate.toString());
        stringBuffer.append(" ").append(nowTime.toString());
        return stringBuffer.toString();
    }

}

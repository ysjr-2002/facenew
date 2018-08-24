package com.visitor.tengli.face.util;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * created by yangshaojie  on 2018/8/24
 * email: ysjr-2002@163.com
 */
public class DateUtil {

    public static final String HOUR_MINUTE = "HH:mm";
    public static final String YEAR_MONTH_DAY_HOUR_MINUTE = "yyyy-MM-dd HH:mm";
    public static final String YEAR_MONTH_DAY_WEEKDAY = "yyyy-MM-dd E";
    public static final String MONTH_DAY = "MM月dd日";
    public static final String YEAR_MONTH_DAY = "yyyy年MM月dd日";
    public static final String MONTH_DAY_HOUR_MINUTE = "MM月dd日 HH:mm";
    public static final String WEEKDAY_HOUR_MINUTE = "E HH:mm";

    public static String getCurrentDateTime() {

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return  df.format(new Date());
    }

    /**
     * 获取时间字符串
     * @param timestamp
     * @return
     */
    public static String getTimeString(long timestamp, String formatString){
        SimpleDateFormat df = new SimpleDateFormat(formatString, Locale.PRC);
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timestamp * 1000);
        return df.format(cal.getTime());
    }


    /**
     * 获取时间字符串
     * @param timestamp
     * @return
     */
    public static String getTimeString(long timestamp){
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timestamp);
        return df.format(cal.getTime());
    }

    /**
     * 格式化时间:2月13日11:21
     * @return
     */
    public static String formatDateMDHHmm(long time){
        SimpleDateFormat df = new SimpleDateFormat("M月d日HH:mm ss");
        return df.format(time);
    }

    public static String formatDateYYYYMMDDHHmm(long time){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return df.format(time);
    }

    /**
     * 格式化时间：10月3日
     * @param time
     * @return
     */
    public static String formatDateMD(long time){
        SimpleDateFormat df = new SimpleDateFormat("M月d日");
        return df.format(time);
    }

    /**
     * 获取当前的月，日 日期
     * @return
     */
    public static String getCurMonthDay() {
        SimpleDateFormat df = new SimpleDateFormat("M月d日");
        return df.format(new Date());
    }

    /**
     * 获取当前日期
     * @return
     */
    public static String getCurrentDate() {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        return df.format(new Date());
    }

    /**
     * 获取当前日期
     * @return
     */
    public static String getTomorrowDate() {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        return String.valueOf(Integer.valueOf(df.format(new Date())) + 1);
    }

    /**
     * 获取当前日期字符串
     * @return
     */
    public static String getCurrentDateString() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日");
        return df.format(new Date());
    }

    /**
     * 格式化年月日
     * @param year
     * @param month
     * @param date
     * @return
     */
    public static String formatDate(int year, int month, int date){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, date);
        return df.format(calendar.getTime());
    }



    /**
     * 获取当前年
     * @return
     */
    public static int getCurrentYear() {
        Calendar cal= Calendar.getInstance();
        return cal.get(Calendar.YEAR);
    }

    /**
     * 获取当前月
     * @return
     */
    public static int getCurrentMonth() {
        Calendar cal= Calendar.getInstance();
        return cal.get(Calendar.MONTH);
    }

    /**
     * 获取当前日
     * @return
     */
    public static int getCurrentDay() {
        Calendar cal= Calendar.getInstance();
        return cal.get(Calendar.DATE);
    }

    public static int getCurrentWeekDay() {
        Calendar cal= Calendar.getInstance();
        return cal.get(Calendar.DAY_OF_WEEK);
    }

    public static int getCurrentHour(){
        Calendar cal= Calendar.getInstance();
        return cal.get(Calendar.HOUR_OF_DAY);
    }

    public static int getCurrentMinute(){
        Calendar cal= Calendar.getInstance();
        return cal.get(Calendar.MINUTE);
    }

    /**
     * 将时间戳转化为字符串
     * @param showTime
     * @return
     */
    public static String formatTime2String(long showTime) {
        return formatTime2String(showTime,false);
    }

    public static String formatTime2String(long showTime , boolean haveYear) {
        String str = "";
        long distance = System.currentTimeMillis()/1000 - showTime;
        if(distance < 300){
            str = "刚刚";
        }else if(distance >= 300 && distance < 600){
            str = "5分钟前";
        }else if(distance >= 600 && distance < 1200){
            str = "10分钟前";
        }else if(distance >= 1200 && distance < 1800){
            str = "20分钟前";
        }else if(distance >= 1800 && distance < 2700){
            str = "半小时前";
        }else if(distance >= 2700){
            Date date = new Date(showTime * 1000);
            SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm");
            return sdf.format(date);
        }
        Date date = new Date(showTime * 1000);
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm");
//        str = formatDateTime(sdf.format(date) , haveYear);
        return sdf.format(date);

    }

    public static String formatDateTime(String time , boolean haveYear) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if(time == null){
            return "";
        }
        Date date = null;
        try {
            date = format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar current = Calendar.getInstance();
        Calendar today = Calendar.getInstance();
        today.set(Calendar.YEAR, current.get(Calendar.YEAR));
        today.set(Calendar.MONTH, current.get(Calendar.MONTH));
        today.set(Calendar.DAY_OF_MONTH, current.get(Calendar.DAY_OF_MONTH));
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        Calendar yesterday = Calendar.getInstance();
        yesterday.set(Calendar.YEAR, current.get(Calendar.YEAR));
        yesterday.set(Calendar.MONTH, current.get(Calendar.MONTH));
        yesterday.set(Calendar.DAY_OF_MONTH, current.get(Calendar.DAY_OF_MONTH)-1);
        yesterday.set(Calendar.HOUR_OF_DAY, 0);
        yesterday.set(Calendar.MINUTE, 0);
        yesterday.set(Calendar.SECOND, 0);

        current.setTime(date);
        if(current.after(today)){
            return "今天 "+time.split(" ")[1];
        }else if(current.before(today) && current.after(yesterday)){
            return "昨天 "+time.split(" ")[1];
        }else{
            if(haveYear) {
                int index = time.indexOf(" ");
                return time.substring(0,index);
            }else {
                int yearIndex = time.indexOf("-")+1;
                int index = time.indexOf(" ");
                return time.substring(yearIndex,time.length()).substring(0,index);
            }
        }
    }


    /**
     * 判断传递进来的时间戳，是否为今天日期
     * @param timeMillis
     * @return
     */
    public static boolean isToday(long timeMillis){
        Calendar c1 = Calendar.getInstance();
        c1.setTimeInMillis(System.currentTimeMillis());

        Calendar c2 = Calendar.getInstance();
        c2.setTimeInMillis(timeMillis);

        int year1 = c1.get(Calendar.YEAR);
        int month1 = c1.get(Calendar.MONTH);
        int day1 = c1.get(Calendar.DAY_OF_MONTH);

        int year2 = c2.get(Calendar.YEAR);
        int month2 = c2.get(Calendar.MONTH);
        int day2 = c2.get(Calendar.DAY_OF_MONTH);

        return year1==year2 && month1==month2 && day1==day2;
    }

    /**
     * 判断传递进来的时间戳，是否为本月日期
     * @param timeMillis
     * @return
     */
    public static boolean isThisMonth(long timeMillis){
        Calendar c1 = Calendar.getInstance();
        c1.setTimeInMillis(System.currentTimeMillis() / 1000);

        Calendar c2 = Calendar.getInstance();
        c2.setTimeInMillis(timeMillis);

        int year1 = c1.get(Calendar.YEAR);
        int month1 = c1.get(Calendar.MONTH);

        int year2 = c2.get(Calendar.YEAR);
        int month2 = c2.get(Calendar.MONTH);

        return year1==year2 && month1==month2;
    }

    /**
     * 由时间字符串，转换成时间格式
     * "10:30"
     * @param timeStr
     * @return
     */
    public static long timeStringToLong(String timeStr){
        if (TextUtils.isEmpty(timeStr))
            return 0;

        String[] timeArr = timeStr.split(":");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        long result = 0;

        try{
            calendar.set(Calendar.HOUR_OF_DAY, Integer.valueOf(timeArr[0]));
            calendar.set(Calendar.MINUTE, Integer.valueOf(timeArr[1]));
            result = calendar.getTimeInMillis();
        }
        catch (NumberFormatException e){
            e.printStackTrace();
        }

        return result;
    }


    public static String formatSeconds(long time){

        String milliSecondStr = "毫秒";
        String secondStr = "秒";

        long millisecond = time % 1000;

        long second = time / 1000;

        if(second > 0){
            StringBuilder s = new StringBuilder(String.valueOf(second));
            s.append(secondStr);
            s.append(millisecond);
            s.append(milliSecondStr);
            return s.toString();
        } else {
            StringBuilder s = new StringBuilder(String.valueOf(millisecond));
            s.append(milliSecondStr);
            return s.toString();
        }
    }

    /**
     * 2323分钟 -> 1天2小时3分钟
     * @return
     */
    public static String formatDurationTime(long time){

        final String dayString = "天";
        final String hourString = "小时";
        final String minuteString = "分钟";

        long day = time/3600/24;
        long hour = (time - day*24*3600) /3600 %24;
        long minute = (time-day*24*3600 - hour * 3600)/60;
        long second = time%60;

        if(time == 0){
            return "0" + minuteString;
        }

        if(day == 0){
            if(hour == 0){
                if(second == 0){
                    return String.valueOf(minute) + minuteString;// 3分钟（整）
                }else {
                    return String.valueOf(minute + 1) + minuteString;// 4分钟（三分钟多几秒）
                }
            } else {
                if(second == 0){
                    return String.valueOf(hour) + hourString + String.valueOf(minute) + minuteString; //3小时4分钟（整）
                }else {
                    return String.valueOf(hour) + hourString + String.valueOf(minute + 1) + minuteString;//3小时5分钟（3小时4分钟多几秒）
                }
            }
        } else {
            if(hour == 0){
                if(second == 0){
                    return String.valueOf(day) + dayString + String.valueOf(minute) + minuteString;//3天4分钟（整）
                }else {
                    return String.valueOf(day) + dayString + String.valueOf(minute + 1) + minuteString;//3天5分钟（3天4分钟多几秒）
                }
            } else {
                if(second == 0){
                    return String.valueOf(day) + dayString + String.valueOf(hour) + hourString + String.valueOf(minute) + minuteString;//2天3小时4分钟（整）
                }else {
                    return String.valueOf(day) + dayString + String.valueOf(hour) + hourString + String.valueOf(minute + 1) + minuteString;//2天3小时5分钟（3小时4分钟多几秒）
                }
            }
        }
    }
}

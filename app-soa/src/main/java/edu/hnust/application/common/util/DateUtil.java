package edu.hnust.application.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 
 * <一句话功能简述> <功能详细描述>
 * 
 * @author Henry(fba02)
 * @version [版本号, 2017年11月11日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class DateUtil {
    /**
     * 常用时间格式
     */
    public static final String Format_Date = "yyyy-MM-dd";    
    public static final String Format_Time = "HH:mm:ss";
    /** 用户界面显示 */
    private static final long timeMin = 60;    
    private static final long timeHalfHour = 60 * 30;    
    private static final long timeHour = 60 * 60;    
    private static final long timeDay = 60 * 60 * 24;    
    private static final long time2Day = 60 * 60 * 24 * 2;
    
    
    /**
     * 线程本地变量，确保线程间的数据安全
     */
    public static final ThreadLocal<SimpleDateFormat> SHORT_DAY = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("HH:mm");
        }
    };
    
    public static final ThreadLocal<SimpleDateFormat> MMDDHH = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("HH:mm MM-dd");
        }
    };
    
    public static final ThreadLocal<SimpleDateFormat> MDHM = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("M月d日 HH:mm");
        }
    };
    
    public static final ThreadLocal<SimpleDateFormat> MD = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("M月d日");
        }
    };
    
    public static final ThreadLocal<SimpleDateFormat> YMD = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy年M月d日");
        }
    };
    
    public static final ThreadLocal<SimpleDateFormat> YMDHM = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy年M月d日 HH:mm");
        }
    };
    
    /** 默认的日期格式化器，格式为yyyy-MM-dd */
    public static final ThreadLocal<SimpleDateFormat> DEFAULT_DATE_FORMATER = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd");
        }
    };
    
    /** 默认的时间格式化器，格式为yyyy-MM-dd HH:mm:ss */
    public static final ThreadLocal<SimpleDateFormat> DEFAULT_DATETIME_FORMATER = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };
    
    public static final ThreadLocal<SimpleDateFormat> YYYYMMDDHHMMSS = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyyMMddHHmmss");
        }
    };
    
    /**
     * 判断日期是否为同一天
     * 
     * @param dateA
     * @param dateB
     * @return
     * @author: MaoWu1
     * @date: 2014年4月2日下午6:11:27
     */
    public static boolean isSameDay(Date dateA, Date dateB) {
        Calendar calDateA = Calendar.getInstance();
        calDateA.setTime(dateA);
        
        Calendar calDateB = Calendar.getInstance();
        calDateB.setTime(dateB);
        
        return calDateA.get(Calendar.YEAR) == calDateB.get(Calendar.YEAR) && calDateA.get(Calendar.MONTH) == calDateB.get(Calendar.MONTH) && calDateA.get(Calendar.DAY_OF_MONTH) == calDateB.get(Calendar.DAY_OF_MONTH);
    }
    
    /**
     * 日期加分钟,可以向前加，向后加
     * 
     * @param date 日期
     * @param min 分钟
     * @return 返回相加后的日期
     */
    public static Date addDate(Date date, int min) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(getMillis(date) + (min * 60 * 1000L));
        
        return c.getTime();
    }
    
    /**
     * 获取当前时间的日期-时 数据，即将当前的分钟、秒置为0后的数值, *
     * 
     * @param hour 要加减的小时数，如果为0表示当前小时
     * @return
     */
    public static Date getDayHourDate(int hour) {
        Calendar today = Calendar.getInstance();
        today.setTimeInMillis(System.currentTimeMillis());
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        today.add(Calendar.HOUR_OF_DAY, hour);
        return today.getTime();
    }
    
    /**
     * 获得某天0时0分0秒的秒数
     * 
     * @param day 今天就是0，昨天就是-1，明天就是1，由此类推
     * @return
     */
    public static Date getSomeDayDate(int day) {
        // return (System.currentTimeMillis()/86400000*86400000L-(23- Calendar.ZONE_OFFSET)*3600000L +
        // day*24*3600000L)/1000;
        // 上面这种方法在服务器执行发现跟系统时间并不一致，早上8点获取到的是新的一天的数据，屏蔽掉。用系统提供的方法以免出错
        Calendar today = Calendar.getInstance();
        today.setTimeInMillis(System.currentTimeMillis());
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        today.add(Calendar.DAY_OF_YEAR, day);
        return today.getTime();
    }
    
    /**
     * 返回日期代表的毫秒
     * 
     * @param date 日期
     * @return 返回毫秒
     */
    public static long getMillis(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.getTimeInMillis();
    }
    
    /**
     * 取得当前日期（只有日期，没有时间，或者可以说是时间为0点0分0秒）
     * 
     * @return
     * @throws ParseException
     */
    public static Date getCurrentDate()
        throws ParseException {
        Date date = new Date();
        date = DateUtil.DEFAULT_DATE_FORMATER.get().parse(DateUtil.DEFAULT_DATE_FORMATER.get().format(date));//
        return date;
    }
    
    /**
     * 取得当前时间（包括日期和时间）
     * 
     * @return 当前时间
     */
    public static Date getCurrentDateTime() {
        return longTimeToDateTime(System.currentTimeMillis());
    }
    
    /**
     * 获取指定格式的当前系统日期时间
     * 
     * @param format 自定义日期格式器
     * @return 前系统日期时间
     */
    public static String getCurrentDateTime(String format) {
        SimpleDateFormat t = new SimpleDateFormat(format);
        return t.format(new Date());
    }
    
    /**
     * 用默认的日期格式，格式化一个Date对象
     * 
     * @param date 待被格式化日期
     * @return “yyyy-MM-dd”格式的日期字符串
     */
    public static String formatDate(Date date) {
        return date == null ? "" : DateUtil.DEFAULT_DATE_FORMATER.get().format(date);
    }
    
    /**
     * 根据传入的格式，将日期对象格式化为日期字符串
     * 
     * @param date 待被格式化日期
     * @param format 自定义日期格式器
     * @return 格式后的日期字符串
     */
    public static String formatDate(Date date, String format) {
        String s = "";
        
        if (date != null) {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            s = sdf.format(date);
        }
        
        return s;
    }
    
    /**
     * 用默认的日期时间格式，格式化一个Date对象
     * 
     * @param date 待被格式化日期
     * @return “yyyy-MM-dd HH:mm:ss”格式的日期时间字符串
     */
    public static String formatTime(Date date) {
        return date == null ? "" : DateUtil.DEFAULT_DATETIME_FORMATER.get().format(date);
    }
    
    /**
     * 根据传入的格式，将日期对象格式化为时间字符串
     * 
     * @param date 待被格式化日期
     * @param format 自定义日期格式器
     * @return 格式后的日期时间字符串
     */
    public static String formatTime(Date date, String format) {
        String s = "";
        if (date != null) {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            s = sdf.format(date);
        }
        
        return s;
    }
    
    /**
     * 获取指定天数后的日期
     * 
     * @param baseDate 基准日期
     * @param day 后推天数
     * @return 后推后的天数
     */
    public static Date getDateAfter(Date baseDate, int day) {
        Calendar now = Calendar.getInstance();
        now.setTime(baseDate);
        now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
        return now.getTime();
    }
    
    /**
     * 利用默认的格式（yyyy-MM-dd）将一个表示日期的字符串解析为日期对象
     * 
     * @param dateStr 待格式化日期字符串
     * @return 格式化后日期对象
     * @throws RuntimeException
     */
    public static Date parseDate(String dateStr) {
        Date date = null;
        try {
            date = DateUtil.DEFAULT_DATE_FORMATER.get().parse(dateStr);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        
        return date;
    }
    
    /**
     * 利用默认的格式（yyyy-MM-dd HH:mm:ss）将一个表示时间的字符串解析为日期对象
     * 
     * @param timeStr 时间字符串
     * @return 格式化后的日期对象
     * @throws ParseException
     */
    public static Date parseTime(String timeStr) {
        try {
            return DateUtil.DEFAULT_DATETIME_FORMATER.get().parse(timeStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }
    
    /**
     * 将一个字符串，按照特定格式，解析为日期对象
     * 
     * @param datetimeStr 日期、时间、日期时间字符串
     * @param format 自定义日期格式器
     * @return 格式化后的日期对象
     * @throws ParseException
     */
    public static Date parseDateTime(String datetimeStr, String format)
        throws ParseException {
        Date date = null;
        try {
            date = (new SimpleDateFormat(format)).parse(datetimeStr);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        
        return date;
    }
    
    /**
     * 得到当前年份
     * 
     * @return 当前年份
     */
    public static int getCurrentYear() {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.YEAR);
    }
    
    /**
     * 得到当前月份（1至12）
     * 
     * @return 当前月份（1至12）
     */
    public static int getCurrentMonth() {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.MONTH) + 1;
    }
    
    /**
     * 获取yyyy-MM-dd格式的当前系统日期
     * 
     * @return 当前系统日期
     */
    public static String getCurrentDateAsString() {
        return new SimpleDateFormat(DateUtil.Format_Date).format(new Date());
    }
    
    /**
     * 获取指定格式的当前系统日期
     * 
     * @param format 自定义日期格式器
     * @return 当前系统日期
     */
    public static String getCurrentDateAsString(String format) {
        SimpleDateFormat t = new SimpleDateFormat(format);
        return t.format(new Date());
    }
    
    /**
     * 获取HH:mm:ss格式的当前系统时间
     * 
     * @return 当前系统时间
     */
    public static String getCurrentTimeAsString() {
        return new SimpleDateFormat(DateUtil.Format_Time).format(new Date());
    }
    
    /**
     * 获取指定格式的当前系统时间
     * 
     * @param format 自定义日期格式器
     * @return 当前系统时间
     */
    public static String getCurrentTimeAsString(String format) {
        SimpleDateFormat t = new SimpleDateFormat(format);
        return t.format(new Date());
    }
    
    /**
     * 获取格式为yyyy-MM-dd HH:mm:ss的当前系统日期时间
     * 
     * @return 当前系统日期时间
     */
    public static String getCurrentDateTimeAsString() {
        return DateUtil.getCurrentDateTime("yyyy-MM-dd HH:mm:ss");
    }
    
    /**
     * 获取当前为星期几,从星期日~星期六对应的值是1~7
     * 
     * @return 星期几
     * @date: 2013年12月31日下午3:35:08
     */
    public static int getDayOfWeek() {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.DAY_OF_WEEK);
    }
    
    /**
     * 获取指定日期为星期几,从星期日~星期六对应的值是1~7
     * 
     * @param date 指定日期
     * @return 星期几
     * @date: 2013年12月31日下午3:45:35
     */
    public static int getDayOfWeek(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_WEEK);
    }
    
    /**
     * 获取星期几的中文名称
     * 
     * @param date 指定日期
     * @return 星期几
     */
    public static String getChineseDayOfWeek() {
        Calendar cal = Calendar.getInstance();
        return DateUtil.getChineseDayOfWeek(cal.getTime());
    }
    
    /**
     * 获取星期几的中文名称
     * 
     * @param date 指定日期
     * @return 星期几
     */
    public static String getChineseDayOfWeek(String date) {
        return DateUtil.getChineseDayOfWeek(DateUtil.parseDate(date));
    }
    
    /**
     * 获取星期几的中文名称
     * 
     * @param date 指定日期
     * @return 星期几
     */
    public static String getChineseDayOfWeek(Date date) {
        int dateOfWeek = DateUtil.getDayOfWeek(date);
        if (dateOfWeek == Calendar.MONDAY) {
            return "星期一";
        } else if (dateOfWeek == Calendar.TUESDAY) {
            return "星期二";
        } else if (dateOfWeek == Calendar.WEDNESDAY) {
            return "星期三";
        } else if (dateOfWeek == Calendar.THURSDAY) {
            return "星期四";
        } else if (dateOfWeek == Calendar.FRIDAY) {
            return "星期五";
        } else if (dateOfWeek == Calendar.SATURDAY) {
            return "星期六";
        } else if (dateOfWeek == Calendar.SUNDAY) {
            return "星期日";
        }
        return null;
    }
    
    /**
     * 获取当天为几号
     * 
     * @return 几号
     * @date: 2013年12月31日下午3:50:11
     */
    public static int getDayOfMonth() {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.DAY_OF_MONTH);
    }
    
    /**
     * 获取指定日期为几号
     * 
     * @param date 指定的日期
     * @return 几号
     * @date: 2013年12月31日下午3:50:40
     */
    public static int getDayOfMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_MONTH);
    }
    
    /**
     * 获取指定日期所在月份的最后一天是几号
     * 
     * @param date 指定日期
     * @return 指定日期所在月份的最后一天是几号
     * @date: 2013年12月31日下午3:51:07
     */
    public static int getMaxDayOfMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
    }
    
    /**
     * 获取指定日期所在月份的第一天
     * 
     * @param date 指定日期
     * @return 指定日期所在月份的第一天
     * @date: 2013年12月31日下午4:16:56
     */
    public static String getFirstDayOfMonth(String date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(DateUtil.parseDate(date));
        cal.set(Calendar.DAY_OF_MONTH, 1);
        return new SimpleDateFormat(DateUtil.Format_Date).format(cal.getTime());
    }
    
    /**
     * 获取当天为一年中第几天
     * 
     * @return 一年中第几天
     * @date: 2013年12月31日下午4:03:57
     */
    public static int getDayOfYear() {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.DAY_OF_YEAR);
    }
    
    /**
     * 获取指定日期为一年中第几天
     * 
     * @param date 指定日期
     * @return 一年中第几天
     * @date: 2013年12月31日下午4:04:21
     */
    public static int getDayOfYear(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_YEAR);
    }
    
    /**
     * 获取指定日期为星期几,从星期日~星期六对应的值是1~7
     * 
     * @param date 指定日期
     * @return 星期几
     * @date: 2013年12月31日下午3:45:35
     */
    public static int getDayOfWeek(String date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(DateUtil.parseDate(date));
        return cal.get(Calendar.DAY_OF_WEEK);
    }
    
    /**
     * 获取指定日期为几号
     * 
     * @param date 指定的日期
     * @return 几号
     * @date: 2013年12月31日下午3:50:40
     */
    public static int getDayOfMonth(String date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(DateUtil.parseDate(date));
        return cal.get(Calendar.DAY_OF_MONTH);
    }
    
    /**
     * 获取指定日期为一年中第几天
     * 
     * @param date 指定日期
     * @return 一年中第几天
     * @date: 2013年12月31日下午4:04:21
     */
    public static int getDayOfYear(String date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(DateUtil.parseDate(date));
        return cal.get(Calendar.DAY_OF_YEAR);
    }
    
    /**
     * 时间戳转换:把距离GMT时间的毫秒数转为日期，中国处在东八区，所以是：GMT时间+8小时
     * 
     * @param time 距离GTM时刻的毫秒数
     * @return 获取到的北京时区日期时间字符串
     */
    public static String longTimeToDateTimeString(Long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String d = format.format(time);
        return d;
    }
    
    /**
     * 时间戳转换:把距离GMT时间的毫秒数转为日期，中国处在东八区，所以是：GMT时间+8小时
     * 
     * @param time 距离GTM时刻的毫秒数
     * @return 获取到的北京时区日期时间对象
     */
    public static Date longTimeToDateTime(long time) {
        String d = DateUtil.DEFAULT_DATETIME_FORMATER.get().format(time);
        return DateUtil.parseTime(d);
    }
   
    /**
     * 1分钟内： 刚刚 半小时内： XX分钟前 今天： 今天**：**（时：分） 昨天： 昨天**：**（时：分） 前天： 前天**：**（时：分） 早于前天： mm月dd日 **：**（时：分） 早于今年： yyyy年mm月dd日 **：**（时：分）
     * 
     * @param displayTime
     * @param isShowHM
     * @return
     */
    public static String getDisplayTime(long displayTime, boolean isShowHM) {
        Calendar now = Calendar.getInstance();
        Calendar show = Calendar.getInstance();
        show.setTimeInMillis(displayTime);
        Date mDownloadDate = show.getTime();
        long time = (now.getTimeInMillis() - mDownloadDate.getTime()) / 1000;
        if (time < (-5) * DateUtil.timeMin) {
            return DateUtil.YMDHM.get().format(mDownloadDate);
        }
        if (time < DateUtil.timeMin) {
            return "刚刚";
        }
        if (time < DateUtil.timeHalfHour) {
            return (time / 60) + "分钟前";
        }
        long todayPassTime = now.get(Calendar.HOUR_OF_DAY) * DateUtil.timeHour + now.get(Calendar.MINUTE) * DateUtil.timeMin + now.get(Calendar.SECOND);
        if (time < todayPassTime) {
            return "今天" + (isShowHM ? DateUtil.SHORT_DAY.get().format(mDownloadDate) : "");
        }
        if (time < DateUtil.timeDay + todayPassTime) {
            return "昨天" + (isShowHM ? DateUtil.SHORT_DAY.get().format(mDownloadDate) : "");
        }
        if (time < DateUtil.time2Day + todayPassTime) {
            return "前天" + (isShowHM ? DateUtil.SHORT_DAY.get().format(mDownloadDate) : "");
        }
        SimpleDateFormat format = (isShowHM) ? DateUtil.YMDHM.get() : DateUtil.YMD.get();
        if (now.get(Calendar.YEAR) == show.get(Calendar.YEAR)) {
            format = (isShowHM) ? DateUtil.MDHM.get() : DateUtil.MD.get();
        }
        return format.format(mDownloadDate);
    }
    
    // timeStamp,linux时间戳
    public static long getNow() {
        return System.currentTimeMillis() / 1000L;
    }
    
    public static void main(String[] args) {
        // System.out.println(getDayOfWeek(parseDate("2013-12-30")));
        System.out.println(DateUtil.getFirstDayOfMonth((("2013-03-32"))));
        // System.out.println(longTimeToDateTimeString(1999L));
        System.out.println(DateUtil.formatDate(DateUtil.addDate(new Date(), 2), "yyyy-MM-dd HH:mm:ss"));
    }
}

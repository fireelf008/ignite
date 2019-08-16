package com.test.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.apache.commons.math3.exception.NullArgumentException;

import java.math.BigDecimal;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 日期工具类
 */
public class DateUtils
{
//    private static Logger logger = LoggerFactory.getLogger(DateUtils.class);

    //默认显示日期的格式
    public static final String DATAFORMAT_STR = "yyyy-MM-dd";

    //默认显示日期的格式
    public static final String YYYY_MM_DATAFORMAT_STR = "yyyy-MM";

    //默认显示日期时间的格式
    public static final String DATATIMEF_STR = "yyyy-MM-dd HH:mm:ss";

    public static final String DATETIME_STR2 = "yyyy-MM-dd HH";

    //默认显示简体中文日期的格式
    public static final String ZHCN_DATAFORMAT_STR = "yyyy年MM月dd日";

    //默认显示简体中文日期时间的格式
    public static final String ZHCN_DATATIMEF_STR = "yyyy年MM月dd日HH时mm分ss秒";

    //默认显示简体中文日期时间的格式
    public static final String ZHCN_DATATIMEF_STR_4YMMDDHHMM = "yyyy年MM月dd日HH时mm分";

    public static final String NUM_DATATIME_STR = "yyyyMMddHHmmssSSS";

    public static final String DATEFORMAT_DB_STR = "yyyyMMddHHmmss";

    public static final String DATEFORMAT_SHORT_DB_STR = "yyyyMMdd";

    public static final String YYYY_MM_DD_DATAFORMAT_STR = "yyyy-MM-dd HH:mm";

    public static final FastDateFormat dateFormatDB = FastDateFormat.getInstance(DATEFORMAT_DB_STR);

    public static final FastDateFormat dateFormatShortDB = FastDateFormat.getInstance(DATEFORMAT_SHORT_DB_STR);

    public static final FastDateFormat dateFormat = FastDateFormat.getInstance(DATAFORMAT_STR);

    public static final FastDateFormat dateTimeFormat = FastDateFormat.getInstance(DATATIMEF_STR);

    public static final FastDateFormat zhcnDateFormat = FastDateFormat.getInstance(ZHCN_DATAFORMAT_STR);

    public static final FastDateFormat zhcnDateTimeFormat = FastDateFormat.getInstance(ZHCN_DATATIMEF_STR);

    public static final FastDateFormat numDateTimeFormat = FastDateFormat.getInstance(NUM_DATATIME_STR);

    private DateUtils(){

    }

    public static String getFileNameDate()
    {
        return numDateTimeFormat.format(new Date());
    }

    public static FastDateFormat getDateFormat(String formatStr)
    {
        if (formatStr.equalsIgnoreCase(DATAFORMAT_STR)) {
            return dateFormat;
        } else if (formatStr.equalsIgnoreCase(DATATIMEF_STR)) {
            return dateTimeFormat;
        } else if (formatStr.equalsIgnoreCase(ZHCN_DATAFORMAT_STR)) {
            return zhcnDateFormat;
        } else if (formatStr.equalsIgnoreCase(ZHCN_DATATIMEF_STR)) {
            return zhcnDateTimeFormat;
        } else {
            return FastDateFormat.getInstance(formatStr);
        }
    }

    /**
     * 按照默认显示日期时间的格式"yyyy-MM-dd HH:mm:ss"，转化dateTimeStr为Date类型
     * dateTimeStr必须是"yyyy-MM-dd HH:mm:ss"的形式
     * @param dateTimeStr
     * @return
     */
    public static Date getDate(String dateTimeStr)
    {
        return getDate(dateTimeStr, DATAFORMAT_STR);
    }

    /**
     * 按照默认formatStr的格式，转化dateTimeStr为Date类型
     * dateTimeStr必须是formatStr的形式
     * @param dateTimeStr
     * @param formatStr
     * @return
     */
    public static Date getDate(String dateTimeStr, String formatStr) {
        if (StringUtils.isNotBlank(dateTimeStr)) {
            FastDateFormat sdf = getDateFormat(formatStr);
            try {
                return sdf.parse(dateTimeStr);
            } catch (ParseException e) {
//                logger.error(e.getMessage(), e);
            }
        }
        return null;
    }

    /**
     * 获取日期的yyyy-MM-dd格式
     * @param date
     * @param formatStr
     */
    public static Date getDate(Date date, String formatStr) {
        return DateUtils.getDate(DateUtils.dateToDateString(date), formatStr);
    }
    /**
     * 将YYYYMMDD转换成Date日期
     * @param date
     * @throws
     */
    public static Date transferDate(String date) {
        if (date == null || date.length() < 1) {
            return null;
        }
        String con = "-";

        String yyyy = date.substring(0, 4);
        String mm = date.substring(4, 6);
        String dd = date.substring(6, 8);

        String str = yyyy + con + mm + con + dd;
        return getDate(str, DateUtils.DATAFORMAT_STR);
    }

    /**
     * 将YYYY－MM－DD日期转换成yyyymmdd格式字符串
     * @param date
     * @return
     */
    public static String getYYYYMMDDDate(Date date)
    {
        if (date == null)
            return null;
        String yyyy = String.valueOf(getYear(date));
        String mm = String.valueOf(getMonth(date));
        String dd = String.valueOf(getDay(date));

        mm = StringUtils.rightPad(mm, 2, "0");
        dd = StringUtils.rightPad(dd, 2, "0");
        return yyyy + mm + dd;
    }

    /**
     * 将YYYY－MM－DD日期转换成YYYYMMDDHHMMSS格式字符串
     * @param date
     * @return
     */
    public static String getYYYYMMDDHHMMSSDate(Date date)
    {
        if (date == null)
            return null;
        String yyyy = String.valueOf(getYear(date));
        String mm = String.valueOf(getMonth(date));
        String dd = String.valueOf(getDay(date));
        String hh = String.valueOf(getHour(date));
        String min = String.valueOf(getMin(date));
        String ss = String.valueOf(getSecond(date));

        mm = StringUtils.rightPad(mm, 2, "0");
        dd = StringUtils.rightPad(dd, 2, "0");
        hh = StringUtils.rightPad(hh, 2, "0");
        min = StringUtils.rightPad(min, 2, "0");
        ss = StringUtils.rightPad(ss, 2, "0");

        return yyyy + mm + dd + hh + min + ss;
    }

    /**
     * 将YYYY－MM－DD日期转换成yyyymmdd格式字符串
     * @param date
     * @return
     */
    public static String getYYYYMMDDDate(String date)
    {
        return getYYYYMMDDDate(getDate(date, DATAFORMAT_STR));
    }

    /**
     * 将Date转换成字符串“yyyy-mm-dd hh:mm:ss”的字符串
     * @param date
     * @return
     */
    public static String dateToDateString(Date date)
    {
        return dateToDateString(date, DATATIMEF_STR);
    }

    /**
     * 将Date转换成formatStr格式的字符串
     * @param date
     * @param formatStr
     * @return
     */
    public static String dateToDateString(Date date, String formatStr)
    {
        FastDateFormat df = getDateFormat(formatStr);
        return df.format(date);
    }

    /**
     * 返回一个yyyy-MM-dd HH:mm:ss 形式的日期时间字符串中的HH:mm:ss
     * @param dateTime
     * @return
     */
    public static String getTimeString(String dateTime)
    {
        return getTimeString(dateTime, DATATIMEF_STR);
    }

    /**
     * 返回一个formatStr格式的日期时间字符串中的HH:mm:ss
     * @param dateTime
     * @param formatStr
     * @return
     */
    public static String getTimeString(String dateTime, String formatStr)
    {
        Date d = getDate(dateTime, formatStr);
        String s = dateToDateString(d);
        return s.substring(DATATIMEF_STR.indexOf('H'));
    }

    /**
     * 获取当前日期yyyy-MM-dd的形式
     * @return
     */
    public static String getCurDate()
    {
        return dateToDateString(Calendar.getInstance().getTime(), DATAFORMAT_STR);
    }
    /**
     * 获取当前日期yyyy-MM的形式
     * @return
     */
    public static String getCurDateMonth()
    {
        return dateToDateString(Calendar.getInstance().getTime(), YYYY_MM_DATAFORMAT_STR);
    }

    /**
     * 获取当前日期yyyy年MM月dd日的形式
     * @return
     */
    public static String getCurZhCNDate()
    {
        return dateToDateString(new Date(), ZHCN_DATAFORMAT_STR);
    }

    /**
     * 获取当前日期时间yyyy-MM-dd HH:mm:ss的形式
     * @return
     */
    public static String getCurDateTime()
    {
        return dateToDateString(new Date(), DATATIMEF_STR);
    }

    /**
     * 获取当前日期时间yyyy年MM月dd日HH时mm分ss秒的形式
     * @return
     */
    public static String getCurZhCNDateTime()
    {
        return dateToDateString(new Date(), ZHCN_DATATIMEF_STR);
    }

    /**
     * 获取日期d的days天后的一个Date
     * @param d
     * @param days
     * @return
     */
    public static Date getInternalDateByDay(Date d, int days)
    {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.setTime(d);
        now.add(Calendar.DATE, days);
        return now.getTime();
    }

    public static Date getInternalDateByMon(Date d, int months)
    {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.setTime(d);
        now.add(Calendar.MONTH, months);
        return now.getTime();
    }

    public static Date getInternalDateByYear(Date d, int years)
    {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.setTime(d);
        now.add(Calendar.YEAR, years);
        return now.getTime();
    }

    public static Date getInternalDateBySec(Date d, int sec)
    {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.setTime(d);
        now.add(Calendar.SECOND, sec);
        return now.getTime();
    }

    public static Date getInternalDateByMin(Date d, int min)
    {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.setTime(d);
        now.add(Calendar.MINUTE, min);
        return now.getTime();
    }

    public static Date getInternalDateByHour(Date d, int hours)
    {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.setTime(d);
        now.add(Calendar.HOUR_OF_DAY, hours);
        return now.getTime();
    }

    /**
     * 根据一个日期字符串，返回日期格式，目前支持4种
     * 如果都不是，则返回null
     * @param dateString
     * @return
     */
    public static String getFormateStr(String dateString)
    {
        //yyyy-MM-dd
        String patternStr1 = "[0-9]{4}-[0-9]{1,2}-[0-9]{1,2}";
        //yyyy-MM-dd HH:mm:ss
        String patternStr2 = "[0-9]{4}-[0-9]{1,2}-[0-9]{1,2}\\s[0-9]{1,2}:[0-9]{1,2}:[0-9]{1,2}";
        //yyyy年MM月dd日
        String patternStr3 = "[0-9]{4}年[0-9]{1,2}月[0-9]{1,2}日";
        //yyyy年MM月dd日HH时mm分ss秒
        String patternStr4 = "[0-9]{4}年[0-9]{1,2}月[0-9]{1,2}日[0-9]{1,2}时[0-9]{1,2}分[0-9]{1,2}秒";

        Pattern p = Pattern.compile(patternStr1);
        Matcher m = p.matcher(dateString);
        boolean b = m.matches();
        if (b)
            return DATAFORMAT_STR;
        p = Pattern.compile(patternStr2);
        m = p.matcher(dateString);
        b = m.matches();
        if (b)
            return DATATIMEF_STR;

        p = Pattern.compile(patternStr3);
        m = p.matcher(dateString);
        b = m.matches();
        if (b)
            return ZHCN_DATAFORMAT_STR;

        p = Pattern.compile(patternStr4);
        m = p.matcher(dateString);
        b = m.matches();
        if (b)
            return ZHCN_DATATIMEF_STR;
        return null;
    }

    /**
     * 将一个"yyyy-MM-dd HH:mm:ss"字符串，转换成"yyyy年MM月dd日HH时mm分ss秒"的字符串
     * @param dateStr
     * @return
     */
    public static String getZhCNDateTime(String dateStr)
    {
        Date d = getDate(dateStr);
        return dateToDateString(d, ZHCN_DATATIMEF_STR);
    }

    /**
     * 将一个"yyyy-MM-dd"字符串，转换成"yyyy年MM月dd日"的字符串
     * @param dateStr
     * @return
     */
    public static String getZhCNDate(String dateStr)
    {
        Date d = getDate(dateStr, DATAFORMAT_STR);
        return dateToDateString(d, ZHCN_DATAFORMAT_STR);
    }

    /**
     * 将dateStr从fmtFrom转换到fmtTo的格式
     * @param dateStr
     * @param fmtFrom
     * @param fmtTo
     * @return
     */
    public static String getDateStr(String dateStr, String fmtFrom, String fmtTo)
    {
        Date d = getDate(dateStr, fmtFrom);
        return dateToDateString(d, fmtTo);
    }

    /**
     * 比较两个"yyyy-MM-dd HH:mm:ss"格式的日期，之间相差多少毫秒,time2-time1
     * @param time1
     * @param time2
     * @return
     */
    public static long compareDateStr(String time1, String time2)
    {
        Date d1 = getDate(time1);
        Date d2 = getDate(time2);
        return d2.getTime() - d1.getTime();
    }

    /**
     * 将小时数换算成返回以毫秒为单位的时间
     * @param hours
     * @return
     */
    public static long getMicroSec(BigDecimal hours)
    {
        BigDecimal bd;
        bd = hours.multiply(new BigDecimal(3600 * 1000));
        return bd.longValue();
    }

    /**
     * 获取Date中的分钟
     * @param d
     * @return
     */
    public static int getMin(Date d)
    {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.setTime(d);
        return now.get(Calendar.MINUTE);
    }

    /**
     * 获取Date中的小时(24小时)
     * @param d
     * @return
     */
    public static int getHour(Date d)
    {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.setTime(d);
        return now.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 获取Date中的秒
     * @param d
     * @return
     */
    public static int getSecond(Date d)
    {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.setTime(d);
        return now.get(Calendar.SECOND);
    }

    /**
     * 获取xxxx-xx-xx的日
     * @param d
     * @return
     */
    public static int getDay(Date d)
    {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.setTime(d);
        return now.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取月份，1-12月
     * @param d
     * @return
     */
    public static int getMonth(Date d)
    {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.setTime(d);
        return now.get(Calendar.MONTH) + 1;
    }

    /**
     * 获取19xx,20xx形式的年
     * @param d
     * @return
     */
    public static int getYear(Date d)
    {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.setTime(d);
        return now.get(Calendar.YEAR);
    }

    /**
     * 得到d的上个月的年份+月份,如200505
     * @return
     */
    public static String getYearMonthOfLastMon(Date d)
    {
        Date newdate = getInternalDateByMon(d, -1);
        String year = String.valueOf(getYear(newdate));
        String month = String.valueOf(getMonth(newdate));
        return year + month;
    }

    /**
     * 得到当前日期的年和月如200509
     * @return String
     */
    public static String getCurYearMonth()
    {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        FastDateFormat sdf = FastDateFormat.getInstance("yyyyMM", TimeZone.getDefault());
        return sdf.format(now.getTime());
    }

    public static Date getNextMonth(String year, String month)
    {
        String datestr = year + "-" + month + "-01";
        Date date = getDate(datestr, DATAFORMAT_STR);
        return getInternalDateByMon(date, 1);
    }

    public static Date getLastMonth(String year, String month)
    {
        String datestr = year + "-" + month + "-01";
        Date date = getDate(datestr, DATAFORMAT_STR);
        return getInternalDateByMon(date, -1);
    }

    /**
     * 得到日期d，按照页面日期控件格式，如"2001-3-16"
     * @param d
     * @return
     */
    public static String getSingleNumDate(Date d)
    {
        return dateToDateString(d, DATAFORMAT_STR);
    }

    /**
     * 得到d半年前的日期,"yyyy-MM-dd"
     * @param d
     * @return
     */
    public static String getHalfYearBeforeStr(Date d)
    {
        return dateToDateString(getInternalDateByMon(d, -6), DATAFORMAT_STR);
    }

    /**
     * 得到当前日期D的月底的前/后若干天的时间,<0表示之前，>0表示之后
     * @param d
     * @param days
     * @return
     */
    public static String getInternalDateByLastDay(Date d, int days)
    {

        return dateToDateString(getInternalDateByDay(d, days), DATAFORMAT_STR);
    }

    /**
     * 日期中的年月日相加
     *  @param field int  需要加的字段  年 月 日
     * @param amount int 加多少
     * @return String
     */
    public static String addDate(int field, int amount)
    {
        int temp = 0;
        if (field == 1) {
            temp = Calendar.YEAR;
        } else if (field == 2) {
            temp = Calendar.MONTH;
        } else if (field == 3) {
            temp = Calendar.DATE;
        }

        FastDateFormat sdf = getDateFormat(DateUtils.DATAFORMAT_STR);
        Calendar cal = Calendar.getInstance(TimeZone.getDefault());
        cal.add(temp, amount);
        return sdf.format(cal.getTime());

    }

    /**
     * 获得系统当前月份的天数
     * @return
     */
    public static int getCurentMonthDay()
    {
        Date date = Calendar.getInstance().getTime();
        return getMonthDay(date);
    }

    /**
     * 获得指定日期月份的天数
     * @return
     */
    public static int getMonthDay(Date date)
    {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.getActualMaximum(Calendar.DAY_OF_MONTH);

    }

    /**
     * 获得指定日期月份的天数  yyyy-mm-dd
     * @return
     */
    public static int getMonthDay(String date)
    {
        Date strDate = getDate(date, DATAFORMAT_STR);
        return getMonthDay(strDate);

    }

    public static String getStringDate(Calendar cal)
    {

        FastDateFormat format = getDateFormat(DateUtils.DATAFORMAT_STR);
        return format.format(cal.getTime());
    }

    /**
     * 计算两个日期相差天数，仅计算yyyy-MM-dd部分
     * @param date1 日期1
     * @param date2 日期2
     * @return
     */
    public static int getDifferDateDay(Date date1, Date date2)
    {
        long differ = 0;
        FastDateFormat sdf = getDateFormat(DateUtils.DATAFORMAT_STR);
        try {
            Date formatDate1 = sdf.parse(sdf.format(date1));
            Date formatDate2 = sdf.parse(sdf.format(date2));
            long time1 = formatDate1.getTime();
            long time2 = formatDate2.getTime();
            differ = (time1 < time2) ? time2 - time1 : time1 - time2;
        } catch (ParseException e) {
//            logger.error(e.getMessage(), e);
        }
        return (int) (differ / (1000 * 60 * 60 * 24));
    }

    /**
     * 计算两个日期相差小时数
     * @param date1 日期1
     * @param date2 日期2
     * @return
     */
    public static int getDifferDateHour(Date date1, Date date2)
    {
        long differ = 0;
        FastDateFormat sdf = getDateFormat(DateUtils.DATATIMEF_STR);
        try {
            Date formatDate1 = sdf.parse(sdf.format(date1));
            Date formatDate2 = sdf.parse(sdf.format(date2));
            long time1 = formatDate1.getTime();
            long time2 = formatDate2.getTime();
            differ = (time1 < time2) ? time2 - time1 : time1 - time2;
        } catch (ParseException e) {
//            logger.error(e.getMessage(), e);
        }
        return (int) (differ / (1000 * 60 * 60));
    }

    /**
     * 计算两个日期相差分钟数
     * @param date1 日期1
     * @param date2 日期2
     * @return
     */
    public static int getDifferDateMinute(Date date1, Date date2)
    {
        long differ = 0;
        FastDateFormat sdf = getDateFormat(DateUtils.DATATIMEF_STR);
        try {
            Date formatDate1 = sdf.parse(sdf.format(date1));
            Date formatDate2 = sdf.parse(sdf.format(date2));
            long time1 = formatDate1.getTime();
            long time2 = formatDate2.getTime();
            differ = (time1 < time2) ? time2 - time1 : time1 - time2;
        } catch (ParseException e) {
//            logger.error(e.getMessage(), e);
        }
        return (int) (differ / (1000 * 60));
    }

    /**
     * 计算两个日期相差秒数
     * @param date1 日期1
     * @param date2 日期2
     * @return
     */
    public static int getDifferDateSecond(Date date1, Date date2)
    {
        long differ = 0;
        FastDateFormat sdf = getDateFormat(DateUtils.DATATIMEF_STR);
        try {
            Date formatDate1 = sdf.parse(sdf.format(date1));
            Date formatDate2 = sdf.parse(sdf.format(date2));
            long time1 = formatDate1.getTime();
            long time2 = formatDate2.getTime();
            differ = (time1 < time2) ? time2 - time1 : time1 - time2;
        } catch (ParseException e) {
//            logger.error(e.getMessage(), e);
        }
        return (int) (differ / 1000);
    }

    /**
     * 判断日期是星期几
     * @param date 日期
     * @param fmt 格式，0表示返回数字格式，1表示返回中文格式，2返回英文前3位
     * @return
     */
    public static String getDayOfWeek(Date date, int fmt)
    {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int dayOfWeek = (c.get(Calendar.DAY_OF_WEEK) == 1) ? 7 : c.get(Calendar.DAY_OF_WEEK) - 1;
        String dayOfWeekStr = null;
        switch (dayOfWeek) {
            case 1:
                dayOfWeekStr = (0 == fmt) ? "1" : ((1 == fmt) ? "一" : "Mon");
                break;
            case 2:
                dayOfWeekStr = (0 == fmt) ? "2" : ((1 == fmt) ? "二" : "Tue");
                break;
            case 3:
                dayOfWeekStr = (0 == fmt) ? "3" : ((1 == fmt) ? "三" : "Wed");
                break;
            case 4:
                dayOfWeekStr = (0 == fmt) ? "4" : ((1 == fmt) ? "四" : "Thu");
                break;
            case 5:
                dayOfWeekStr = (0 == fmt) ? "5" : ((1 == fmt) ? "五" : "Fri");
                break;
            case 6:
                dayOfWeekStr = (0 == fmt) ? "6" : ((1 == fmt) ? "六" : "Sat");
                break;
            case 7:
                dayOfWeekStr = (0 == fmt) ? "7" : ((1 == fmt) ? "七" : "Sun");
                break;
            default:
        }
        return  dayOfWeekStr;
    }

    /**
     * 月份的转换，从数字转3位英文或从3位英文转数字
     * @param month 月份
     * @param type 转换类型，0从数字转3位英文，1从三位英文转数字
     * @return
     */
    public static String getMonth(String month, int type)
    {
        String result = null;
        if (0 == type) {
            if ("1".equals(month) || "01".equals(month)) {
                result = "Jan";
            } else if ("2".equals(month) || "02".equals(month)) {
                result = "Feb";
            } else if ("3".equals(month) || "03".equals(month)) {
                result = "Mar";
            } else if ("4".equals(month) || "04".equals(month)) {
                result = "Apr";
            } else if ("5".equals(month) || "05".equals(month)) {
                result = "May";
            } else if ("6".equals(month) || "06".equals(month)) {
                result = "Jun";
            } else if ("7".equals(month) || "07".equals(month)) {
                result = "Jul";
            } else if ("8".equals(month) || "08".equals(month)) {
                result = "Aug";
            } else if ("9".equals(month) || "09".equals(month)) {
                result = "Sep";
            } else if ("10".equals(month)) {
                result = "Oct";
            } else if ("11".equals(month)) {
                result = "Nov";
            } else if ("12".equals(month)) {
                result = "Dec";
            }
        } else if (1 == type) {
            if ("JAN".equalsIgnoreCase(month)) {
                result = "01";
            } else if ("FEB".equalsIgnoreCase(month)) {
                result = "02";
            } else if ("MAR".equalsIgnoreCase(month)) {
                result = "03";
            } else if ("APR".equalsIgnoreCase(month)) {
                result = "04";
            } else if ("MAY".equalsIgnoreCase(month)) {
                result = "05";
            } else if ("JUN".equalsIgnoreCase(month)) {
                result = "06";
            } else if ("JUL".equalsIgnoreCase(month)) {
                result = "07";
            } else if ("AUG".equalsIgnoreCase(month)) {
                result = "08";
            } else if ("SEP".equalsIgnoreCase(month)) {
                result = "09";
            } else if ("OCT".equalsIgnoreCase(month)) {
                result = "10";
            } else if ("NOV".equalsIgnoreCase(month)) {
                result = "11";
            } else if ("DEC".equalsIgnoreCase(month)) {
                result = "12";
            }
        }
        return result;
    }

    /**
     * 在某一个日期上加上或减去一个时间量
     * @param date 日期
     * @param field Calendar类中的日历字段
     * @param amount 要加上或减去的时间量
     * @return
     */
    public static Date calendarAdd(Date date, int field, int amount)
    {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(field, amount);
        return c.getTime();
    }

    /**
     * 在某一个日期上加上或减去一个时间量
     * @param date 日期
     * @param field Calendar类中的日历字段
     * @param amount 要加上或减去的时间量
     * @param formatStr 格式化字符串
     * @return
     */
    public static String calendarAddToString(Date date, int field, int amount, String formatStr)
    {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(field, amount);
        return dateToDateString(c.getTime(), formatStr);
    }

    public static String dateFormatDBDateToString(Date date){
        return dateFormatDB.format(date);
    }

    public static Date dateFormatDBStringToDate(String date){
        try {
            return dateFormatDB.parse(date);
        } catch (ParseException e) {
            return null;
        }
    }

    public static String dateFormatShortDBDateToString(Date date){
        if(date == null){
            return "";
        }
        return dateFormatShortDB.format(date);
    }

    public static Date dateFormatShortDBStringToDate(String date) {
        try {
            return dateFormatShortDB.parse(date);
        } catch (ParseException e) {
            return null;
        }
    }

    public static String getDDMMMYY(String dateStr)
    {
        String []dateArry = dateStr.split("\\s")[0].split("-");
        return dateArry[2] + DateUtils.getMonth(dateArry[1], 0) + dateArry[0].substring(2);
    }

    /**
     * date2比date1多的天数
     * @param date1
     * @param date2
     * @return
     */
    public static int differentDays(Date date1,Date date2)
    {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        int day1= cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);

        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        if(year1 != year2)   //同一年
        {
            int timeDistance = 0 ;
            for(int i = year1 ; i < year2 ; i ++)
            {
                if(i%4==0 && i%100!=0 || i%400==0)    //闰年
                {
                    timeDistance += 366;
                }
                else    //不是闰年
                {
                    timeDistance += 365;
                }
            }

            return timeDistance + (day2-day1) ;
        }
        else    //不同年
        {
            return day2-day1;
        }
    }

    /**
     * 获取当年的第一天
     * @return
     */
    public static Date getCurrYearFirst(){
        Calendar currCal=Calendar.getInstance();
        int currentYear = currCal.get(Calendar.YEAR);
        return getYearFirst(currentYear);
    }

    /**
     * 获取当年的最后一天
     * @return
     */
    public static Date getCurrYearLast(){
        Calendar currCal=Calendar.getInstance();
        int currentYear = currCal.get(Calendar.YEAR);
        return getYearLast(currentYear);
    }

    /**
     * 获取某年第一天日期
     * @param year 年份
     * @return Date
     */
    public static Date getYearFirst(int year){
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        Date currYearFirst = calendar.getTime();
        return currYearFirst;
    }

    /**
     * 获取某年最后一天日期
     * @param year 年份
     * @return Date
     */
    public static Date getYearLast(int year){
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.HOUR,23);
        calendar.set(Calendar.MINUTE,59);
        calendar.set(Calendar.SECOND,59);
        calendar.roll(Calendar.DAY_OF_YEAR, -1);
        Date currYearLast = calendar.getTime();
        return currYearLast;
    }

    /**
     * 获取当月的第一天
     * @return
     */
    public static Date getCurrMonthFirst(){
        Calendar currCal=Calendar.getInstance();
        int currentMonth = currCal.get(Calendar.MONTH);
        return getMonthFirst(currentMonth);
    }

    /**
     * 获取当月的最后一天
     * @return
     */
    public static Date getCurrMonthLast(){
        Calendar currCal=Calendar.getInstance();
        int currentMonth = currCal.get(Calendar.MONTH);
        return getMonthLast(currentMonth);
    }

    /**
     * 获取某月第一天日期
     * @param month 月份
     * @return Date
     */
    public static Date getMonthFirst(int month){
        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        calendar.clear();
        calendar.set(Calendar.YEAR,currentYear);
        calendar.set(Calendar.MONTH, month);
        Date currMonthFirst = calendar.getTime();
        return currMonthFirst;
    }

    /**
     * 获取某月最后一天日期
     * @param month 月份
     * @return Date
     */
    public static Date getMonthLast(int month){
        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        calendar.clear();
        calendar.set(Calendar.YEAR,currentYear);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.HOUR,23);
        calendar.set(Calendar.MINUTE,59);
        calendar.set(Calendar.SECOND,59);
        calendar.roll(Calendar.DAY_OF_MONTH, -1);
        Date currMonthLast = calendar.getTime();
        return currMonthLast;
    }
    /**
     * 给定年月返回这个月多少天
     * @param year
     * @param month
     * @return
     */
    public static int getDays(int year, int month) {
        int days = 0;
        boolean isLeapYear = false;
        //是闰年
        if (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0)) {
            isLeapYear = true;
            //不是闰年
        } else {
            isLeapYear = false;
        }
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                days = 31;
                break;
            case 2:
                if (isLeapYear) {
                    days = 29;
                } else {
                    days = 28;
                }
                break;
            case 4:
            case 6:
            case 9:
            case 11:
                days = 30;
                break;
            default:
                System.out.println("error!!!");
                break;
        }
        return days;
    }
    /**
     * 获取过去第几天的日期
     */
    public static String getPastDate(long past, Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DATE, (int) (calendar.get(Calendar.DATE) - past));
        Date today = calendar.getTime();
        FastDateFormat sdf = getDateFormat(DateUtils.DATAFORMAT_STR);
        String result = sdf.format(today);
        return result;
    }
    /**
     * 求回两个时间相差的天数(日期差值忽略时间)
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return  long
     */
    public static long getDeviationValue(Date startDate , Date endDate){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        long startTime = calendar.getTimeInMillis();
        calendar.setTime(endDate);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        long endTime = calendar.getTimeInMillis();
        long days = (endTime - startTime)%(86400000L) == 0? (endTime - startTime)/(86400000L) : (endTime - startTime)/(86400000L)+1;
        return Math.abs(days);
    }

    public static Date getYesterday() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    public static String getYesterdayStr(){
          return LocalDate.now().minusDays(1L).toString();
    }

    public static Date getBeforeYesterday(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -2);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    public static String getBeforeYesterdayStr(){
        return LocalDate.now().minusDays(2L).toString();
    }

    public static Date getYesterdayOfLastYear(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        calendar.add(Calendar.YEAR, -1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    public static String getYesterdayOfLastYearStr(){
        LocalDate localDate = LocalDate.now().minusDays(1L);
        LocalDate of = LocalDate.of(localDate.getYear() - 1, localDate.getMonth(), localDate.getDayOfMonth());
        return of.toString();
    }
    public static String  formatMonth(String time){
        String[] split = time.split("-");
        if(split.length > 0){
            if(Integer.parseInt(split[1]) < 10 && split[1].length() == 1)
                split[1] = "0" + split[1];

            return StringUtils.join(split, "-");
        }
        return time;
    }

    /**
     * 返回从某个日期开始的连续N天的日期
     * @param date 开始日期
     * @param n N天
     * @param type 1向前，2向后
     * @param sort 1升序，2降序
     * @return
     */
    public static List<String> getIntervalDay(Date date, int n, int type, int sort) {
        List<String> list = new ArrayList<>();
        for (int i = n - 1; i >= 0; i--) {
            String dateStr = DateUtils.calendarAddToString(date, Calendar.DATE, (1 == type) ? -i : ((2 == type) ? i : -i), DateUtils.DATAFORMAT_STR);
            list.add(dateStr);
        }
        list.sort((d1, d2) -> (1 == sort) ? d1.compareTo(d2) : ((2 == sort) ? d2.compareTo(d1) : d1.compareTo(d2)));
        return list;
    }

    /**
     * 返回从某个日期开始的连续N天的日期作为key的LinkedHashMap
     * @param date 开始日期
     * @param n N天
     * @param type 1向前，2向后
     * @param sort 1升序，2降序
     * @return
     */
    public static LinkedHashMap<String, Object> getIntervalDayToMap(Date date, int n, int type, int sort) {
        List<String> list = DateUtils.getIntervalDay(date, n, type, sort);
        LinkedHashMap<String, Object> map = new LinkedHashMap();
        list.forEach(l -> {
            map.put(l, null);
        });
        return map;
    }
}
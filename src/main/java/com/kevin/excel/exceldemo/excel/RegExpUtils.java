package com.kevin.excel.exceldemo.excel;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Jinyugai
 * @description: 正则表达式
 * @date: Create in 16:52 2019/11/23
 * @modified By:
 */
public class RegExpUtils {
    /**
     * 日期正则表达式
     * 匹配:yyyy-MM-dd
     */
    public static final String REG_DATE = "^(\\d{4})-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])$";
    /**
     * 日期时间正则表达式
     * 匹配:yyyy-MM-dd HH:mm:ss
     */
    public static final String REG_DATETIME = "^(\\d{4})-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]) ([01]\\d|2[0-3]):([0-5]\\d):([0-5]\\d)$";
    /**
     * 以日期时间精确到毫秒无分隔符结尾的正则表达式
     * 匹配:(任意字符)_yyyyMMddHHmmssSSS
     */
    public static final String REG_DATETIMEMS = "^.*_(\\d{4})(0[1-9]|1[0-2])(0[1-9]|[12]\\d|3[01])([01]\\d|2[0-3])([0-5]\\d)([0-5]\\d{4})$";
    /**
     * 时间正则表达式
     * 匹配:HH:mm:ss
     */
    public static final String REG_TIME = "^(([01]\\d|2[0-3]):[0-5]\\d:([0-5]\\d))$";

    /**
     * 匹配以逗号间隔的ids
     */
    public static final String REG_IDS = "^[\\d,]*$";

    /**
     * 数字+英文
     */
    public static final String REG_EN_AND_NUM = "^[\\da-zA-Z]+$";


    /**
     * 数字+英文，允许为空字符串
     */
    public static final String REG_EN_AND_NUM_AND_EMPTY = "^[\\da-zA-Z]+|(\\s&&[^\\f\\n\\\\r\\t\\v])*$";

    /**
     * 英文
     */
    public static final String REG_EN = "^[a-zA-Z]+$|^$";

    /**
     * 英文开头
     */
    public static final String REG_START_WITH_EN = "^[a-zA-Z].*$";

    /**
     * 名称约束，下面是非法字符
     */
    public static final String REG_NAME = "^.*[\\\\/:*?\\\"<|'%>&\\\\/:*?\\\"<|'%>：？“”‘’|%&]+.*$";

    public static final String REG_RIGHT_NAME = "^((?![\\\\/:*?\\\"<|'%>&\\\\/:*?\\\"<|'%>：？“”‘’|%&]).)*$";

    public static final String GROUP_NAME = ".*[`~!@#$%^&*()_+<>?:\\\"{},.\\\\/;'\\\\[\\\\]·！#￥（——）？、：；“”‘、，|《。》【】]+.*";

    public static final String REG_NAME_EXCLUSIVE = "((?!%).)*";

    public static final String REG_CAMERA_NAME = "[^*&':<>|%?\"‘’：？“”]+";

    /**
     * 截取String中IP
     */
    public static final String REG_IP_PORT = "((\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\:\\d{1,5})";


    /**
     * 空字符串
     */
    public static final String REG_EMPTY_STRING = "^(\\s&&[^\\f\\n\\\\r\\t\\v])*$";

    /**
     * 实数
     */
    public static final String REG_DECIMAL = "^[-+]?\\d+(\\.\\d+)?$";

    /**
     * 自然数
     */
    public static final String REG_CODE_NUM = "^[0-9]*$";

    /**
     * 数字
     */
    public static final String REG_CAPTURE_JOB_LEVEL = "^[1|2|3]";
    public static final String REG_CAPTURE_JOB_TYPE = "^[0|1]";
    public static final String REG_CAPTURE_JOB_WEEK = "^[1|2|3|4|5|6|7]";
    public static final String REG_CAPTURE_JOB_MONTH = "^((0?[1-9])|((1|2)[0-9])|30|31)$";
    public static final String REG_CAPTURE_JOB_EXECTIME = "^([0-1]?[0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])$";

    public static final String REG_PTZ_DIRECTION = "^[0-9]|10|11";//操作命令：0-上，1-下，2-左，3-右，4-左上，5-左下，6-右上，7-右下，8-放大，9-缩小，10-近焦距，11-远焦距
    public static final String REG_PTZ_SPEED = "^[0|1|2]";//云台速度：0-慢，1-适中，2-快

    public static final String EXPORT_RECORD_TYPE = "^[1|2|3|4|5|6|7]";
    public static final String VIDEO_STATE = "^[1|2|3|4]";
    /**
     * 设备类型
     */
    public static final String REG_DEVICE_TYPE = "(ehome|ezviz|GB28181)";

    /**
     * 中文
     */
    public static final String REG_NAME_ZH = "^.*[\u4e00-\u9fa5]+.*$";

    /**
     * IP地址
     */
    public static final String REG_IP = "^$|^((25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))\\.){3}(25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))$";

    /**
     * 端口正则表达式
     */
    public static final String REG_PORT = "^$|^([0-9]|[1-9]\\d{1,3}|[1-5]\\d{4}|6[0-5]{2}[0-3][0-5])$";

    /**
     * 邮箱校验
     */
    public static final String EMAIL = "^$|^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\\.[a-zA-Z0-9_-]{2,3}){1,2})$";

    /**
     * 企业电话
     */
    public static final String TEL_1 = "^[0][1-9]{2,3}-[1-9]{1}[0-9]{6,8}$";
    public static final String TEL_2 = "^[1-9]{1}[0-9]{6,8}$";
    public static final String TEL="^$|^[0][1-9]{2,3}-[1-9]{1}[0-9]{6,8}$|^[1-9]{1}[0-9]{6,8}$";
    public static final String PHONE_NO = "^$|^[1][1-9][0-9]{9}$";
    public static final String MOBILE_PHONE = "^1[3-9]\\d{9}$";

    public static final String COMPARATOR = "(==|!=|>=|<=|>|<)";

    public static boolean match(String src, String regExp) {
        if (src == null) {
            return false;
        }
        Pattern pattern = Pattern.compile(regExp);
        Matcher matcher = pattern.matcher(src);
        return matcher.matches();
    }

    public static boolean find(String src, String regExp) {
        if (src == null) {
            return false;
        }
        Pattern pattern = Pattern.compile(regExp);
        Matcher matcher = pattern.matcher(src);
        return matcher.find();
    }

    public static String getMatchString(String src, String regExp) {
        Pattern pattern = Pattern.compile(regExp);// 匹配的模式
        Matcher m = pattern.matcher(src);
        if (m.find()) {
            return m.group();
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println(match("#*" , REG_RIGHT_NAME));
    }
}



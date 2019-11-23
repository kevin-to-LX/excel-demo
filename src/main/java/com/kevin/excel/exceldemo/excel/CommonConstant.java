package com.kevin.excel.exceldemo.excel;

import org.springframework.util.ClassUtils;

/**
 * @author Jinyugai
 * @description:
 * @date: Create in 17:13 2019/11/23
 * @modified By:
 */

public class CommonConstant {
    /**
     * controller层操作成功默认返回code
     */
    public static final String DEFAULT_SUCCESS_CODE = "0";
    /**
     * 删除标记 0 表示已删除，1表示未删除
     */
    public static final int DELFLAG_DELETE = 0;
    public static final int DELFLAG_NORMAL = 1;

    /**
     * lep异常码前缀
     */
    public static final String EXCEPTION_CODE = "lep.exception.";

    /**
     * 字符编码
     */
    public static final String CHARSET_UTF_8 = "UTF-8";
    public static final String CHARSET_ISO_8859_1 = "ISO-8859-1";
    public static final String CHARSET_GB2312 = "GB2312";


    //1-全部，2-运维人员，3-企业用户
    public static final int DETAIL_1 = 1;
    public static final int DETAIL_2 = 2;
    public static final int DETAIL_3 = 3;


    public static final int CHECK_SELECT = 0;
    public static final int CHECK_INPAGE = 1;
    public static final int CHECK_UPDATE = 2;
    public static final int CHECK_INSERT = 3;
    public static final int CHECK_DELETE = 4;
    public static final int CHECK_PASSWORD = 5;
    public static final int CHECK_SAVE = 6;
    public static final int CHECK_RESETPWD = 7;

    public static final Integer DB_COM_NUM = 0;//数据库操作返回值比较值
    public static final Integer DB_ERR_NO = -1;//数据库操作返回异常值

    public static final Integer ZERO = 0;//零


    public static final String REG_NAME = "&':<>|/%\\?\"";//名称约束

    //数据库
    public static final String DB_USER_NAME = "pg.username";//数据库用户名
    public static final String DB_PWD = "pg.password";//数据库密码

    /**
     * classpath目录
     */
    public static final String CLASS_PATH = ClassUtils.getDefaultClassLoader().getResource("").getPath();
    //Thread.currentThread().getContextClassLoader().getResource("/").getPath();

    /**
     * oss共享license目录
     */
    public static final String SHARE_PATH = CLASS_PATH + "/share/license";

    public static final String ENTERPRISE_FIELD_CODE = "enterprise.field.code";

    public final static String USER_SESSION = "user_session";//用户session连接字段

    public final static int USER_SESSION_TIME = 1800;//登入用户SessionId保存时间

    /**
     * 数据校验失败默认提示
     */
    public final static String BIND_EXCEPTION_NULL = "bind.exception.null";
    public final static String BIND_EXCEPTION_NOT_NULL = "bind.exception.not.null";
    public final static String BIND_EXCEPTION_NOT_EMPTY = "bind.exception.not.empty";
    public final static String BIND_EXCEPTION_NOT_BLANK = "bind.exception.not.blank";
    public final static String BIND_EXCEPTION_SIZE = "bind.exception.size";
    public final static String BIND_EXCEPTION_LENGTH = "bind.exception.size";
    public final static String BIND_EXCEPTION_PATTERN = "bind.exception.pattern";
    public final static String BIND_EXCEPTION_MIN = "bind.exception.min";
    public final static String BIND_EXCEPTION_MAX = "bind.exception.max";
    public final static String BIND_EXCEPTION_DECIMAL_MAX = "bind.exception.decimal.max";
    public final static String BIND_EXCEPTION_DECIMAL_MIN = "bind.exception.decimal.min";
    public final static String BIND_EXCEPTION_RANGE = "bind.exception.range";
    public final static String BIND_EXCEPTION_EMAIL = "bind.exception.email";
    public final static String BIND_EXCEPTION_DEFAULT = "bind.exception.default";

    public final static String BIND_EXCEPTION_EN_AND_NUM = "bind.exception.en.num";
    public final static String BIND_EXCEPTION_EN = "bind.exception.en";
    public final static String BIND_EXCEPTION_HTTP_ONLY = "bind.exception.http.only";
    public final static String BIND_EXCEPTION_INVALID_CHARACTER = "bind.exception.invalid.character";
    public final static String BIND_EXCEPTION_INVALID_PERCENT = "bind.exception.invalid.percent";
    public final static String BIND_EXCEPTION_START_WITH_EN = "bind.exception.start.en";

    public static final String PHONE_INVALID = "phone.invalid";

    public final static Integer LOGIN_TYPE_WEB = 0;
    public final static Integer LOGIN_TYPE_MOBILE = 1;
}


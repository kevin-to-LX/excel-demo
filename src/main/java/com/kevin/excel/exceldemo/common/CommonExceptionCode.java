package com.kevin.excel.exceldemo.common;

/**
 * @author Jinyugai
 * @description:
 * @date: Create in 17:45 2019/11/23
 * @modified By:
 */
public class CommonExceptionCode {
    public static final String UNKNOWN_EXCEPTION = "00010";//未知异常
    public static final String DB_EXCEPTION = "00020";//数据库异常
    public static final String DELETE_ASSOCIATION_EXCEPTION = "00021";//删除数据库关联异常
    public static final String INSERT_ASSOCIATION_EXCEPTION = "00022";//添加数据库关联异常
    public static final String NULL_EXCEPTION = "00030";//空值异常
    public static final String PARAM_EXCEPTION = "00031";//参数错误
    public static final String VERIFYCODE_INTERVAL_TIME = "00032";//验证码间隔时间一分钟
    public static final String PARAM_TYPE_EXCEPTION = "00033";//参数类型有误

    public static final String NO_PRIVILEGE_EXCEPTION = "00040";//无权限异常
    public static final String SELECT_INPAGE_EXCEPTION = "00050";//分页查询异常
    public static final String AUTHCODE_GET_EXCEPTION = "00060";//获取授权码失败
    public static final String ACCESSTOKEN_GET_EXCEPTION = "00061";//获取Token失败
    public static final String TOKEN_LOSE_EXCEPTION = "00062";//Token已失效
    public static final String SCHEMA_NULL_EXCEPTION = "00070";//企业标识为空
    public static final String SCHEME = "00071";//数据库模式不能为空
    public static final String PERMISSION_DENIED = "00080"; //用户权限不足
    public static final String DOWNLOAD_NULL="00090";//下载内容不存在

    public static final String PROVINCE_CODE_EXCEPTION="00100";//省行政区划代码有误
    public static final String CITY_CODE_EXCEPTION="00101";//市行政区划代码有误

    public static final String CAN_NOT_BE_NULL_EXCEPTION = "01001";//{0}不能为空
    public static final String TOO_SHORT_STRING_EXCEPTION = "01002";//{0}长度不能小于{1}
    public static final String TOO_LONG_STRING_EXCEPTION = "01003";//{0}长度不能大于{1}
    public static final String INVALID_CHARACTER_EXCEPTION = "01004";//{0}不能包含下列字符：{1}
    public static final String INVALID_PERIOD_EXCEPTION = "01005";//{0}不能大于{1}

    public static final String PHONE_NULL_EXCEPTION = "02000";//手机号为空
    public static final String PHONE_CHECK_EXCEPTION = "02001";//手机格式有误

    public static final String CODE_INVALID_EXCEPTION = "45008";
}

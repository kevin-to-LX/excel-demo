package com.kevin.excel.exceldemo.i18n;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * @author Jinyugai
 * @description:
 * @date: Create in 17:36 2019/11/23
 * @modified By:
 */
@Component
public class I18nUtil {
    private static Logger logger = LoggerFactory.getLogger(I18nUtil.class);

    private static MessageSource messageSource;
    /**
     * 根据key获取资源,不存在则返回key
     * @param key
     * @return
     */
    public static String getTextValue(String key) {
        return getTextValue(key, null);
    }

    public static String getTextValue(String key, Object... obj) {
        try {
            return messageSource.getMessage(key, obj, Locale.SIMPLIFIED_CHINESE);
        } catch (NoSuchMessageException e) {
            logger.trace("Cannot find key: {}, return key.", key);
            return key;
        }
    }

    @Autowired
    public void setMessageSource(MessageSource messageSource) {
        I18nUtil.messageSource = messageSource;
    }
}

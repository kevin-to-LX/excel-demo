package com.kevin.excel.exceldemo.excel;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.InputStream;

/**
 * @author Jinyugai
 * @description: 配置文件加载类
 * @date: Create in 17:27 2019/11/23
 * @modified By:
 */
public class TemplateLoader {
    private static Logger log= LoggerFactory.getLogger(TemplateLoader.class);

    /**
     * 加载配置文件
     * @param file
     * @return
     */
    public Document loadDocument(File file){
        Document doc=null;
        SAXReader reader=new SAXReader();
        try {
            doc=reader.read(file);
        } catch (DocumentException e) {
            log.debug("TemplateLoader loadDocument exception",e);
        }
        return doc;
    }

    public Document loadDocument(InputStream inputStream){
        Document doc=null;
        SAXReader reader=new SAXReader();
        try {
            doc=reader.read(inputStream);
        } catch (DocumentException e) {
            log.debug("TemplateLoader loadDocument exception",e);
        }
        return doc;
    }
}

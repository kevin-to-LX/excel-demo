package com.kevin.excel.exceldemo.excel;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.lang.reflect.Field;

/**
 * @author Jinyugai
 * @description:
 * @date: Create in 17:24 2019/11/23
 * @modified By:
 */
public abstract class XmlTemplateFactory
        implements TemplateFactory,TemplateDefinitionRegistry{
    private static Logger log= LoggerFactory.getLogger(XmlTemplateFactory.class);
    protected String xmlTemplatePath;
    protected XmlTemplateReader reader;

    protected XmlTemplateFactory(String path){
        this.xmlTemplatePath=path;
        this.reader=new XmlTemplateReader(this);
    }

    @Override
    public void createTemplates(){}

    @Override
    public void registryTemplateDefinition(Document doc){}

    /**
     * 解析标签属性
     * @param element
     * @param clazz
     * @return
     */
    public Object coverAttribute(Element element, Class clazz){
        Object obj=null;
        try {
            obj=clazz.newInstance();
        } catch (Exception e) {
            log.debug("XmlTemplateFactory coverAttribute exception", e);
        }

        Field[] fields=clazz.getFields();
        for(Field field:fields){
            Attribute attribute=element.attribute(field.getName());
            if(attribute==null){
                continue;
            }
            String value=attribute.getStringValue();
            field.setAccessible(true);
            try {
                if(field.getType().isAssignableFrom(Integer.class)){
                    Integer i= Integer.parseInt(value);
                    field.set(obj, i);
                }else if(field.getType().isAssignableFrom(Boolean.class)){
                    Boolean b= Boolean.parseBoolean(value);
                    field.set(obj, b);
                }else if(field.getType().isAssignableFrom(Long.class)){
                    Long l= Long.parseLong(value);
                    field.set(obj, l);
                }else if(field.getType().isAssignableFrom(Short.class)){
                    Short s= Short.parseShort(value);
                    field.set(obj, s);
                }else if(field.getType().isAssignableFrom(Class.class)){
                    Class c= Class.forName(value);
                    field.set(obj,c);
                }
                else{
                    field.set(obj, value);
                }
            } catch (Exception e) {
                log.debug("XmlTemplateFactory coverAttribute exception", e);
            }
            field.setAccessible(false);
        }
        return obj;
    }

}


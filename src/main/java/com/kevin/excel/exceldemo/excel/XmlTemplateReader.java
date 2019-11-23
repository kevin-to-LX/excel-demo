package com.kevin.excel.exceldemo.excel;

import org.dom4j.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ClassUtils;
import org.springframework.core.io.Resource;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Jinyugai
 * @description:
 * @date: Create in 17:26 2019/11/23
 * @modified By:
 */
public class XmlTemplateReader {
    private static Logger log= LoggerFactory.getLogger(XmlTemplateReader.class);
    protected TemplateDefinitionRegistry registry;
    protected TemplateLoader loader;
    public XmlTemplateReader(TemplateDefinitionRegistry registry){
        this.registry=registry;
        this.loader=new TemplateLoader();
    }

    /**
     * 读取文件
     * @param xmlTemplatePath
     */
    public void read(String xmlTemplatePath) {
        InputStream inputStream= ClassUtils.getDefaultClassLoader().getResourceAsStream(xmlTemplatePath);
        /*BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
        Resource resource=new ClassPathResource(xmlTemplatePath);
        read(resource);*/
        Document doc=this.loader.loadDocument(inputStream);
        if(doc==null){
            log.error("Excel Template XML reader failure");
            return;
        }
        this.registry.registryTemplateDefinition(doc);
    }
    public void read(Resource resource){
        if(resource.exists()){
            try {
                File file=resource.getFile();
                Document doc=this.loader.loadDocument(file);
                if(doc==null){
                    log.error("Excel Template XML reader failure");
                    return;
                }
                this.registry.registryTemplateDefinition(doc);
            } catch (IOException e) {
                log.debug("XmlTemplateReader read IoException",e);
            }
        }
    }
}

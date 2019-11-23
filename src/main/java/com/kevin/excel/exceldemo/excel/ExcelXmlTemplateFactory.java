package com.kevin.excel.exceldemo.excel;

import org.dom4j.Document;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * @author Jinyugai
 * @description:
 * @date: Create in 17:23 2019/11/23
 * @modified By:
 */
public class ExcelXmlTemplateFactory extends XmlTemplateFactory {
    private static Logger log= LoggerFactory.getLogger(ExcelXmlTemplateFactory.class);
    private static Map<String,ExcelTemplate> excelTemplates=new HashMap<>();

    public ExcelXmlTemplateFactory(String path){
        super(path);
        this.reader.read(xmlTemplatePath);
    }

    /**
     * 注册模板信息,只为模板中添加标题信息，
     * 默认使用Excel中第一个sheet，第一行row，
     * 模板示例：
     * name:字段名
     * value:字段值
     * column：哪一列，从0开始
     * <templates>
     *     <template name="templateTest" type="xls" path="/template/templateTest.xls" count="1000">
     *         <cell name="" value="">
     *             <property name="" value="可选，优先级2">可选，优先级1</property>
     *         </cell>
     *     </template>
     * </templates>
     *
     *
     * @param doc
     */
    @Override
    public void registryTemplateDefinition(Document doc) {
        Element rootElement=doc.getRootElement();
        Iterator templateIterator=rootElement.elementIterator(TemplateConst.TEMPLATE);
        while(templateIterator.hasNext()){
            Element templateElement=(Element)templateIterator.next();
            ExcelTemplate excelTemplate=analysisExcelTemplate(templateElement);
            String templateName;
            if(StringUtils.isBlank(excelTemplate.getName())){
                templateName= TemplateConst.TEMPLATE+excelTemplates.size();
            }else{
                templateName=excelTemplate.getName();
            }
            excelTemplates.put(templateName,excelTemplate);
        }

    }

    /**
     * 解析template标签
     * @param templateElement
     * @return
     */
    public ExcelTemplate analysisExcelTemplate(Element templateElement){
        ExcelTemplate excelTemplate=(ExcelTemplate)coverAttribute(templateElement,ExcelTemplate.class);
        Iterator rowIterator=templateElement.elementIterator(TemplateConst.ROW);
        List<ExcelRow> excelRows=new ArrayList<>();
        while(rowIterator.hasNext()){
            Element rowElement=(Element)rowIterator.next();
            ExcelRow excelRow=analysisExcelRow(rowElement);
            excelRows.add(excelRow);
        }
        excelTemplate.setRows(excelRows);
        return excelTemplate;
    }
    public ExcelRow analysisExcelRow(Element rowElement){
        ExcelRow excelRow=(ExcelRow)coverAttribute(rowElement,ExcelRow.class);
        Iterator cellIterator=rowElement.elementIterator(TemplateConst.CELL);
        List<ExcelCell> excelCells=new ArrayList<ExcelCell>();
        while(cellIterator.hasNext()){
            Element cellElement=(Element)cellIterator.next();
            ExcelCell excelCell= analysisExcelCell(cellElement);
            excelCells.add(excelCell);
        }
        excelRow.setCells(excelCells);
        return excelRow;
    }

    /**
     * 解析cell标签
     * @param cellElement
     * @return
     */
    public ExcelCell analysisExcelCell(Element cellElement){
        ExcelCell excelCell=(ExcelCell)coverAttribute(cellElement,ExcelCell.class);
        Iterator propertyIterator=cellElement.elementIterator(TemplateConst.PROPERTY);
        List<ExcelProperty> excelProperties=new ArrayList<ExcelProperty>();
        while(propertyIterator.hasNext()){
            Element propertyElement=(Element)propertyIterator.next();
            ExcelProperty excelProperty=analysisExcelProperty(propertyElement);
            excelProperties.add(excelProperty);
        }
        excelCell.setProperties(excelProperties);
        return excelCell;
    }

    /**
     * 解析property标签
     * @param propertyElement
     * @return
     */
    public ExcelProperty analysisExcelProperty(Element propertyElement){
        ExcelProperty excelProperty=(ExcelProperty)coverAttribute(propertyElement,ExcelProperty.class);
        if(StringUtils.isNotEmpty(propertyElement.getStringValue())){
            excelProperty.setValue(propertyElement.getStringValue());
        }
        return excelProperty;
    }


    /**
     * 创建模板
     */
    @Override
    public void createTemplates() {
        for(ExcelTemplate excelTemplate:excelTemplates.values()){
            try {
                ExcelTool.createExcel(excelTemplate);
            } catch (Exception e) {
                log.debug("ExcelXmlTemplateFactory createTemplates exception",e);
            }
        }
    }

    /**
     *
     * @param name
     * @return
     */
    public static ExcelTemplate getTemplate(String name){
        return excelTemplates.get(name);
    }

}

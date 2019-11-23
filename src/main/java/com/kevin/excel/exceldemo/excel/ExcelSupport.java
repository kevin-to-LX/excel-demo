package com.kevin.excel.exceldemo.excel;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Jinyugai
 * @description: excel支持 参数类
 * @date: Create in 16:26 2019/11/23
 * @modified By:
 */
@Data
public class ExcelSupport {
    //sheet序号默认0
    int sheetNum;
    //所有创建的sheet可以写入数据的最大值
    int maxSize;
    //之前创建的sheet已经写入的数据量（不包含当前sheet）
    int subtrahend;
    //前一条数据用于分组的字段值
    String preGroupStr;
    //当前sheet中分组数据所在开始行
    int preRowNum;

    int recordNum;

    int allNum;

    int rowNum;

    int status;

    ExcelTemplate excelTemplate;

    ExcelTypeEnum excelTypeEnum;

    Map<String,Integer> name2ColumnMap;

    public ExcelSupport(){
        this.sheetNum=TemplateConst.ZERO;
        this.maxSize=TemplateConst.ZERO;
        this.subtrahend=TemplateConst.ZERO;
        this.preGroupStr="";
        this.preRowNum=TemplateConst.ZERO;
        this.recordNum=TemplateConst.ZERO;
        this.allNum=TemplateConst.ZERO;
        this.name2ColumnMap=new HashMap<>();
        this.status=TemplateConst.ZERO;
        this.excelTypeEnum=ExcelTypeEnum.NOMERGE;
    }
}

package com.kevin.excel.exceldemo.excel;

import lombok.Data;

/**
 * @author Jinyugai
 * @description: 模板常量类
 * @date: Create in 16:28 2019/11/23
 * @modified By:
 */
@Data
public class TemplateConst {
    //模板xml中的名字
    public static final String TEMPLATE="template";
    public static final String ROW="row";
    public static final String CELL="cell";
    public static final String PROPERTY="property";

    //excel格式
    public static final String XLS="xls";
    public static final String XLSX="xlsx";

    //模板标题行
    public static final int ROWNUM=0;
    public static final short ROWHEIGHT=20;

    public static final short FONTSIZE=16;
    public static final String FONT="宋体";
    //sheet号
    public static final int SHEETNUM=0;
    //记录在Excel中的行号
    public static final String RECNUM="recordNum";

    //get方法前缀
    public static final String METHODGET="get";
    //大小写字母ASCII偏移
    public static final int DEVIATION=32;

    public static final int ZERO=0;

    //一个sheet中最多数据条数
    public static final int maxSheetCount=1048575;
    public static final String sheetName="sheet";

    public static final String filePath="/temp/templateExcel";
}

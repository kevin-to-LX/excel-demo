package com.kevin.excel.exceldemo.excel;

/**
 * @author Jinyugai
 * @description:
 * @date: Create in 17:33 2019/11/23
 * @modified By:
 */
public class ExceptionCodeConstant {

    //50-EXCEL
    //excel路径空值异常
    public static final String EXCEL_PATH_NULL_EXCEPTION = "21001";
    //EXCEL文件空值异常
    public static final String EXCEL_FILE_NULL_EXCEPTION = "22001";
    //EXCEL 文件读写异常
    public static final String EXCEL_FILE_IO_EXCEPTION = "22002";
    //文件解析异常
    public static final String EXCEL_FILE_ANALYSIS_EXCEPTION = "22003";
    //workbook 空值异常
    public static final String EXCEL_WORKBOOK_NULL_EXCEPTION = "23001";
    //workbook 读写异常
    public static final String EXCEL_WORKBOOK_IO_EXCEPTION = "23002";
    //sheet 空值异常
    public static final String EXCEL_SHEET_NULL_EXCEPTION = "24001";
    //excelTemplate 空值异常
    public static final String EXCEL_EXECELTEMPLATE_NULL_EXCEPTION = "25001";
    //未使用正确模板
    public static final String EXCEL_EXECELTEMPLATE_WRONG_FORMAT_EXCEPTION = "25002";
    //excelCells 空值异常
    public static final String EXCEL_EXCELCELLS_NULL_EXCEPTION = "26001";
    //超出数量限制
    public static final String EXCEL_COUNT_OVER_EXCEPTION = "27001";
    //excelRows 空值异常
    public static final String EXCEL_EXCELROES_NULL_EXCEPTION = "28001";
    //文件生成失败
    public static final String EXCEL_CREATE_FAILURE = "29001";
}

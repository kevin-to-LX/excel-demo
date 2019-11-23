package com.kevin.excel.exceldemo.excel;

import lombok.Data;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * @author Jinyugai
 * @description: 支持分页
 * @date: Create in 16:47 2019/11/23
 * @modified By:
 */
@Data
public class ExcelSupportPage<T> {
    private String template;
    private Workbook workbook;
    private ExcelSupport excelSupport;
    private LepResultPage<T> List;
    //之前所有页已经存了多少数据
    private int prePageNum;

    public ExcelSupportPage(String template){
        this.prePageNum=0;
        this.template=template;
        this.excelSupport=new ExcelSupport();
    }
}

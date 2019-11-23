package com.kevin.excel.exceldemo.excel;

import lombok.Data;

import java.util.List;

/**
 * @author Jinyugai
 * @description: excel 行
 * @date: Create in 16:24 2019/11/23
 * @modified By:
 */
@Data
public class ExcelRow {
    public Integer rowNum;
    //标题行高度
    public Short height;
    public List<ExcelCell> cells;

    public ExcelRow(){
        this.rowNum=0;
        this.height=600;
    }
}

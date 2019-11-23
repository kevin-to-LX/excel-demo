package com.kevin.excel.exceldemo.excel;

import lombok.Data;

import java.awt.*;
import java.util.List;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.FillPatternType;

/**
 * @author Jinyugai
 * @description: excel 模板类
 * @date: Create in 16:34 2019/11/23
 * @modified By:
 */
@Data
public class ExcelTemplate {
    /**
    模板名称
     */
    public String name;
    /**
     模板类型 xls，xlsx
     */
    public String type;
    /**
    模板路径
     */
    public String path;
    /**
    模板条数限制
     */
    public Integer count=1000;
    //标题行高度
    //public Short height=600;
    /**
     * 字体
     */
    public String font="宋体";
    /**
    字体大小
     */
    public Short fontSize=16;
    /**
      默认黑色
     */
    public Short fontColor=new  Short("255");
    /**
      背景颜色 默认白色
     */
    public Short color;
    /**
      背景填充方式 参考CellStyle类,默认无
     */
    public Short pattern= FillPatternType.NO_FILL.getCode();
    public Integer columnNum=0;
    /**
     是否更新进度
     */
    public Boolean updateStatus=false;

    public List<ExcelRow> rows;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    /*public Short getHeight() {
        return height;
    }

    public void setHeight(Short height) {
        this.height = height;
    }*/

    public String getFont() {
        return font;
    }

    public void setFont(String font) {
        this.font = font;
    }

    public Short getFontSize() {
        return fontSize;
    }

    public void setFontSize(Short fontSize) {
        this.fontSize = fontSize;
    }

    public List<ExcelRow> getRows() {
        return rows;
    }

    public void setRows(List<ExcelRow> rows) {
        this.rows = rows;
        for(ExcelRow excelRow:rows){
            if(excelRow.getCells().size()>this.getColumnNum()){
                setColumnNum(excelRow.getCells().size());
            }
        }
    }

    public Short getColor() {
        return color;
    }

    public void setColor(Short color) {
        this.color = color;
    }

    public Short getPattern() {
        return pattern;
    }

    public void setPattern(Short pattern) {
        this.pattern = pattern;
    }

    public Short getFontColor() {
        return fontColor;
    }

    public void setFontColor(Short fontColor) {
        this.fontColor = fontColor;
    }

    public Integer getColumnNum() {
        return columnNum;
    }

    public void setColumnNum(Integer columnNum) {
        this.columnNum = columnNum;
    }

    public Boolean isUpdateStatus() {
        return updateStatus;
    }

    public void setUpdateStatus(Boolean updateStatus) {
        this.updateStatus = updateStatus;
    }
}

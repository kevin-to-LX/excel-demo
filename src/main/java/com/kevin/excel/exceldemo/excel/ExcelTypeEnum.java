package com.kevin.excel.exceldemo.excel;

import lombok.Data;

/**
 * @author Jinyugai
 * @description: excel 类型枚举
 * @date: Create in 16:44 2019/11/23
 * @modified By:
 */

public enum ExcelTypeEnum {
    //不合并单元格
    NOMERGE(0,0),
    //流量导出样式合并单元格
    FLUXMERGE(1,3),
    //上下线状态统计样式
    ONOFFLINESTATUS(2,5);

    /**
     * 类型
     */

    private int type;
    /**
     * 列
     */

    private int column;


    ExcelTypeEnum(int type,int column){
        this.type=type;
        this.column=column;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }
}

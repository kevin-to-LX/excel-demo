package com.kevin.excel.exceldemo.excel;

import lombok.Data;

import java.util.List;

/**
 * @author Jinyugai
 * @description: excel cell
 * @date: Create in 16:21 2019/11/23
 * @modified By:
 */
@Data
public class ExcelCell {
    public String name;
    public String value;
    public Integer width=6000;
    public String method;
    public String def;
    public Boolean common=false;
    public Boolean group=false;
    public Boolean groupPath=false;
    public String groupPathMethod="findGroupPath";
    public Integer size=1;
    public Boolean startRow=false;
    public Boolean endRow=false;
    public String valueType;
    public List<ExcelProperty> properties;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getDef() {
        return def;
    }

    public void setDef(String def) {
        this.def = def;
    }

    public List<ExcelProperty> getProperties() {
        return properties;
    }

    public void setProperties(List<ExcelProperty> properties) {
        this.properties = properties;
    }

    public Boolean isCommon() {
        return common;
    }

    public void setCommon(Boolean common) {
        this.common = common;
    }

    public Boolean isGroup() {
        return group;
    }

    public void setGroup(Boolean group) {
        this.group = group;
    }

    public Boolean isGroupPath() {
        return groupPath;
    }

    public void setGroupPath(Boolean groupPath) {
        this.groupPath = groupPath;
    }

    public String getGroupPathMethod() {
        return groupPathMethod;
    }

    public void setGroupPathMethod(String groupPathMethod) {
        this.groupPathMethod = groupPathMethod;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Boolean isStartRow() {
        return startRow;
    }

    public void setStartRow(Boolean startRow) {
        this.startRow = startRow;
    }

    public Boolean isEndRow() {
        return endRow;
    }

    public void setEndRow(Boolean endRow) {
        this.endRow = endRow;
    }

    public String getValueType() {
        return valueType;
    }

    public void setValueType(String valueType) {
        this.valueType = valueType;
    }
}

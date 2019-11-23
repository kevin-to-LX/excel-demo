package com.kevin.excel.exceldemo.excel;

/**
 * @author Jinyugai
 * @description: excel方法调用返回 结果
 * @date: Create in 17:16 2019/11/23
 * @modified By:
 */
public interface ExcelMethodCallBack {
    /**
     * 获取分组完整路径
     * @param groupId
     * @param scheme
     * @return
     */
    default String findGroupPath(Long groupId, String scheme){
        return null;
    }

    /**
     * 更新导出进度
     * @param status
     * @param exportRecordId
     * @param scheme
     */
    default void updateExportStatus(Integer status,Long exportRecordId,String scheme){}
}

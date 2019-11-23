package com.kevin.excel.exceldemo.excel;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Pattern;

/**
 * @author Jinyugai
 * @description:
 * @date: Create in 16:52 2019/11/23
 * @modified By:
 */
@Data
public class BasePageVo {
    @Range(min = 1, max = 1000)
    private Integer pageSize;//页面大小

    @Range(min = 1,max = 400000)
    private Integer pageNo;//页码

    @Length(max = 32)
    @Pattern(regexp = RegExpUtils.REG_EN, message = CommonConstant.BIND_EXCEPTION_EN)
    private String scheme;//企业标识

    private Integer selectFlag = 0;//查询记录日志标记

    private String keyword;//查询关键字
}

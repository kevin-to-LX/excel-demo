package com.kevin.excel.exceldemo.excel;

import lombok.Data;

/**
 * @author Jinyugai
 * @description:
 * @date: Create in 17:21 2019/11/23
 * @modified By:
 */
@Data
public class LepException extends RuntimeException {
    private String code;
    private String message;
    private Object[] args;

    public LepException() {

    }

    public LepException(String code) {
        this.code = code;
    }

    public LepException(String code, Object... args) {
        this.code = code;
        this.args = args;
    }
}

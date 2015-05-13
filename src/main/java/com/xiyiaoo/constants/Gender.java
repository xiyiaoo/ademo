package com.xiyiaoo.constants;

/**
 * User: xiyiaoo@gmail.com
 * Date: 15-4-28 上午10:02
 * 性别
 */
public enum Gender implements CodeEnum<Gender> {
    MALE("M"), FEMALE("F");
    /**
     * 代码
     */
    private String code;

    Gender(String code) {
        this.code = code;
    }


    @Override
    public String getCode() {
        return code;
    }

}

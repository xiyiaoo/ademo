package com.xiyiaoo.constants;

/**
 * User: xiyiaoo@gmail.com
 * Date: 15-4-28 上午11:18
 */
public enum ResourceType implements CodeEnum<ResourceType> {
    /**
     * 菜单组,会显示在菜单中,作为菜单的上级,无url
     */
    MENU_GROUP("0"),
    /**
     * 菜单,会显示在菜单中,会对应一个可访问的url
     */
    MENU("1"),
    /**
     * 按钮,不会显示在菜单中,一般是页面的按钮或者链接
     */
    BUTTON("2");

    private String code;

    ResourceType(String code) {
        this.code = code;
    }

    @Override
    public String getCode() {
        return code;
    }
}

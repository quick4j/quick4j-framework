package com.github.quick4j.plugin.datagrid.meta;

import org.springframework.beans.BeanUtils;

/**
 * @author zhaojh
 */
public class Toolbutton implements Cloneable {
    private String code;
    private String text;
    private String iconCls;
    private String handler;

    public Toolbutton(){}

    public Toolbutton(String code, String iconCls, String handler) {
        this.iconCls = iconCls;
        this.code = code;
        this.handler = handler;
        this.text = code;
    }

    public Toolbutton(String code, String text, String iconCls, String handler) {
        this.code = code;
        this.text = text;
        this.iconCls = iconCls;
        this.handler = handler;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getIconCls() {
        return iconCls;
    }

    public void setIconCls(String iconCls) {
        this.iconCls = iconCls;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getHandler() {
        return handler;
    }

    public void setHandler(String handler) {
        this.handler = handler;
    }

    @Override
    public Toolbutton clone() throws CloneNotSupportedException {
        Toolbutton toolbutton = new Toolbutton();
        BeanUtils.copyProperties(this, toolbutton);
        return toolbutton;
    }

    @Override
    public String toString() {
        return "Toolbutton{" +
                "text='" + text + '\'' +
                ", iconCls='" + iconCls + '\'' +
                ", handler='" + handler + '\'' +
                '}';
    }
}

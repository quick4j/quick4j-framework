package com.github.quick4j.plugin.dictionary.entity;

import com.github.quick4j.core.entity.Entity;
import com.github.quick4j.core.mybatis.annotation.MapperNamespace;

/**
 * @author zhaojh
 */
@MapperNamespace("com.github.quick4j.plugin.dictionary.entity.DicItemMapper")
public class DicItem extends Entity{
    private String code;
    private String name;
    private String value;
    private String text;
    private int index=1;

    public DicItem() {}

    public DicItem(String code, String name, String text, String value) {
        this.code = code;
        this.name = name;
        this.value = value;
        this.text = text;
    }

    public DicItem(String code, String name, String text, String value, Integer index) {
        this.code = code;
        this.name = name;
        this.value = value;
        this.text = text;
        this.index = index;
    }

    @Override
    public String getMetaData() {
        return "字典条目";
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}

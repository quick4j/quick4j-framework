package com.github.quick4j.plugin.datagrid.meta;

import org.springframework.beans.BeanUtils;

/**
 * @author zhaojh
 */
public class Column implements Cloneable{
    private String title;
    private String field;
    private int width = 200;
    private String align;
    private boolean sortable;
    private boolean hidden;
    private boolean checkbox;
    private String formatter;

    public Column(){}

    public Column(String title, String field) {
        this.title = title;
        this.field = field;
    }

    public Column(String title, String field, int width) {
        this.title = title;
        this.field = field;
        this.width = width;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public String getAlign() {
        return align;
    }

    public void setAlign(String align) {
        this.align = align;
    }

    public boolean isSortable() {
        return sortable;
    }

    public void setSortable(boolean sortable) {
        this.sortable = sortable;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public boolean isCheckbox() {
        return checkbox;
    }

    public void setCheckbox(boolean checkbox) {
        this.checkbox = checkbox;
    }

    public String getFormatter() {
        return formatter;
    }

    public void setFormatter(String formatter) {
        this.formatter = formatter;
    }

    @Override
    public Column clone() throws CloneNotSupportedException {
        Column column = new Column();
        BeanUtils.copyProperties(this, column);
        return column;
    }

    @Override
    public String toString() {
        return "Column{" +
                "title='" + title + '\'' +
                ", field='" + field + '\'' +
                ", width=" + width +
                ", align='" + align + '\'' +
                ", sortable=" + sortable +
                ", hidden=" + hidden +
                ", checkbox=" + checkbox +
                ", formatter=" + formatter +
                '}';
    }
}

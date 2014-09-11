package com.github.quick4j.entity;

import com.github.quick4j.core.entity.Entity;
import com.github.quick4j.core.mybatis.annotation.MapperNamespace;
import com.github.quick4j.plugin.logging.annontation.Auditable;

import java.util.List;

/**
 * @author zhaojh
 */
@Auditable
@MapperNamespace("com.github.quick4j.entity.StudentMapper")
public class Student extends Entity{
    private String name;

    public Student(String name) {
        this.name = name;
    }

    @Override
    public String getMetaData() {
        return "学生-" + this.name;
    }

    @Override
    public List<? extends Entity> getSlave() {
        return null;
    }

    public String getName() {
        return name;
    }
}

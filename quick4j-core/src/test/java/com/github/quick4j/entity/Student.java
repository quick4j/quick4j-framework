package com.github.quick4j.entity;

import com.github.quick4j.core.entity.AbstractEntity;
import com.github.quick4j.core.entity.Entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.List;

/**
 * @author zhaojh
 */
@Table(name = "test_student")
public class Student extends AbstractEntity{
    @Id
    private String id;
    @Column(name = "stu_name")
    private String name;
    @Column(name = "stu_age")
    private int age;
    private String masterId;

    public Student() {}

    public Student(String name) {
        this.name = name;
    }

    @Override
    public String getChineseName() {
        return "学生";
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<? extends Entity> getSlave() {
        return null;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }
}

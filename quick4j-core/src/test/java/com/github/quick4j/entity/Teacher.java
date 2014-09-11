package com.github.quick4j.entity;

import com.github.quick4j.core.entity.Entity;
import com.github.quick4j.core.mybatis.annotation.MapperNamespace;
import com.github.quick4j.plugin.logging.annontation.Auditable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhaojh
 */
@Auditable
@MapperNamespace("com.github.quick4j.entity.TeacherMapper")
public class Teacher extends Entity{
    private String name;
    private List<Student> students = new ArrayList<Student>();

    public Teacher(String name) {
        this.name = name;
    }

    @Override
    public String getMetaData() {
        return "教师-" + this.name;
    }

    @Override
    public List<? extends Entity> getSlave() {
        return students;
    }

    public void addStudent(Student student){
        students.add(student);
    }

    public String getName() {
        return name;
    }
}

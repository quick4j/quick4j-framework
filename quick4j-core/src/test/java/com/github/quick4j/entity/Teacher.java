package com.github.quick4j.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.quick4j.core.entity.AbstractEntity;
import com.github.quick4j.core.entity.Entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author zhaojh
 */
@Table(name = "test_teacher")
public class Teacher extends AbstractEntity{
    @Id
    private String id;
    @Column(name = "teacher_name")
    private String name;
    @Column(name = "teacher_age")
    private Integer age;
    @Column(name = "teacher_tel")
    private String tel;
    @Column(name = "teacher_birthday")
    private Date birthDay;
    private String masterId;
    private List<Student> students = new ArrayList<Student>();

    public Teacher() {}

    public Teacher(String name) {
        this.name = name;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setMasterId(String id) {
        this.masterId = id;
    }

    @Override
    public String getMasterId() {
        return masterId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    @Override
    @JsonIgnore
    public String getMetaData() {
        return "教师-" + this.name;
    }

    @Override
    @JsonIgnore
    public List<? extends Entity> getSlave() {
        return students;
    }

    public void addStudent(Student student){
        students.add(student);
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", tel='" + tel + '\'' +
                ", birthDay=" + birthDay +
                '}';
    }
}

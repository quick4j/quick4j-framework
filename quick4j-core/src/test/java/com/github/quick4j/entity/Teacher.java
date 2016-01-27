package com.github.quick4j.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.quick4j.core.entity.BaseEntity;
import com.github.quick4j.plugin.logging.annontation.Auditable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author zhaojh
 */
//@Auditable
@Entity
@Table(name = "test_teacher")
public class Teacher extends BaseEntity {

  @Id
  @Column(name = "id", length = 32)
  private String id;
  @Column(name = "teacher_name", length = 100)
  private String name;
  @Column(name = "teacher_age")
  private Integer age;
  @Column(name = "teacher_tel", length = 100)
  private String tel;
  @Column(name = "teacher_birthday")
  private Date birthDay;
  @Transient
  private List<Student> students = new ArrayList<Student>();

  public Teacher() {
  }

  public Teacher(String name) {
    this.name = name;
  }

  @Override
  public String getChineseName() {
    return "教师";
  }

  @Override
  public void setId(String id) {
    this.id = id;
  }

  @Override
  public String getId() {
    return id;
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
  public List<? extends BaseEntity> getSlave() {
    return null;
  }

  public void addStudent(Student student) {
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

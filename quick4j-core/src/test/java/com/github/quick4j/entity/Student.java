package com.github.quick4j.entity;

import com.github.quick4j.core.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import java.util.List;

/**
 * @author zhaojh
 */
@Entity
@Table(name = "test_student")
public class Student extends BaseEntity {

  @Id
  @Column(name = "id", length = 32)
  private String id;
  @Column(name = "stu_name", length = 100)
  private String name;
  @Column(name = "stu_age")
  private int age;

  public Student() {}

  public Student(String name) {
    this.name = name;
  }

  @Override
  public String getChineseName() {
    return "学生";
  }

  public String getName() {
    return name;
  }

  @Override
  public List<? extends BaseEntity> getSlave() {
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

package com.github.quick4j.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.quick4j.core.service.Criteria;
import com.github.quick4j.core.service.SimpleCrudService;
import com.github.quick4j.entity.Student;
import com.github.quick4j.entity.Teacher;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

/**
 * @author zhaojh
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
                          "/spring-config.xml",
                          "/spring-config-mybatis.xml"
                      })
public class SimpleCrudServiceTest {
  @Resource
  private SimpleCrudService<Teacher> crudService;

  @Test
  @Transactional
  public void insert() {
    Teacher teacher = new Teacher("zhang");
    teacher.setAge(30);
    teacher.setTel("110");
    crudService.save(teacher);

    Teacher other = crudService.newCriteria(Teacher.class).selectOne(teacher.getId());
    assertThat(other).isEqualTo(teacher);
  }

  @Test
  @Transactional
  public void insertBatch() {
    List<Teacher> teachers = prepareData();
    crudService.save(teachers);

    Criteria<Teacher> criteria = crudService.newCriteria(Teacher.class);
    assertThat(criteria.selectList()).containsAll(teachers);
  }

  @Test
  @Transactional
  public void update() {
    Teacher teacher = new Teacher("zhang");
    teacher.setAge(30);
    teacher.setTel("110");
    crudService.save(teacher);

    teacher.setAge(32);
    teacher.setTel("220");
    crudService.save(teacher);

    Teacher other = crudService.newCriteria(Teacher.class).selectOne(teacher.getId());
    assertThat(other.getAge()).isEqualTo(32);
    assertThat(other.getTel()).isEqualToIgnoringCase("220");
  }

  @Test
  @Transactional
  public void updateBatch() {
    List<Teacher> teachers = prepareData();
    crudService.save(teachers);

    for (Teacher teacher : teachers) {
      teacher.setAge(teacher.getAge() + 1);
    }
    crudService.save(teachers);

    Criteria<Teacher> criteria = crudService.newCriteria(Teacher.class);
    assertThat(criteria.selectList()).containsAll(teachers);
  }

  @Test
  @Transactional
  public void insertAndUpdate() {
    Teacher t1 = new Teacher("one");
    t1.setAge(40);
    t1.setTel("110");

    crudService.save(t1);

    Teacher other = crudService.newCriteria(Teacher.class).selectOne(t1.getId());
    other.setTel("911");

    List<Teacher> teachers = prepareData();
    teachers.add(other);
    crudService.save(teachers);
    assertThat(crudService.newCriteria(Teacher.class).selectList()).containsAll(teachers);
  }

  @Test
  @Transactional
  public void deleteById() {
    Teacher t1 = new Teacher("one");
    t1.setAge(40);
    t1.setTel("110");

    crudService.save(t1);

    Criteria<Teacher> criteria = crudService.newCriteria(Teacher.class);

    assertThat(criteria.selectOne(t1.getId())).isEqualTo(t1);

    criteria.delete(t1.getId());
    assertThat(criteria.selectOne(t1.getId())).isNull();
  }

  @Test
  @Transactional
  public void deleteByIds() {
    List<Teacher> teachers = prepareData();
    crudService.save(teachers);

    Criteria<Teacher> criteria = crudService.newCriteria(Teacher.class);
    assertThat(criteria.selectList()).containsAll(teachers);

    List<String> ids = new ArrayList<String>();
    for (Teacher teacher : teachers) {
      ids.add(teacher.getId());
    }
    criteria.delete(ids.toArray(new String[]{}));
    assertThat(criteria.selectList()).isEmpty();
  }

  @Test
  @Transactional
  public void deleteBatch() {
    List<Teacher> teachers = prepareData();
    crudService.save(teachers);

    Criteria<Teacher> criteria = crudService.newCriteria(Teacher.class);
    assertThat(criteria.selectList()).containsAll(teachers);

    criteria.delete(teachers);
    assertThat(criteria.selectList()).isEmpty();
  }

  @Test
  @Transactional
  public void deleteEntity() {
    List<Teacher> teachers = prepareData();
    crudService.save(teachers);

    Criteria<Teacher> criteria = crudService.newCriteria(Teacher.class);
    assertThat(criteria.selectList()).containsAll(teachers);

    Teacher template = new Teacher("zhang");
    criteria.delete(template);
    assertThat(criteria.selectList()).doesNotContain(teachers.get(0))
        .contains(teachers.get(1), teachers.get(2));
  }

    @Test
    @Transactional
    public void testSaveMasterAndSlave(){
        Teacher wang = new Teacher("wang");
        Student jack = new Student("Jack");
        Student marry = new Student("Marry");

        wang.addStudent(jack);
        wang.addStudent(marry);

        crudService.save(wang);

        Criteria<Teacher> teacherCriteria = crudService.newCriteria(Teacher.class);
        assertThat(teacherCriteria.selectOne(wang.getId())).isEqualTo(wang);

        Criteria<Student> studentCriteria = crudService.newCriteria(Student.class);
        assertThat(studentCriteria.selectList()).contains(jack, marry);
    }

  private List<Teacher> prepareData() {
    List<Teacher> teachers = new ArrayList<Teacher>();
    String[] teacherNames = new String[]{"zhang", "wang", "zhao"};

    for (int i = 0; i < teacherNames.length; i++) {
      Teacher teacher = new Teacher(teacherNames[i]);
      teacher.setAge(20 + i + 1);
      teacher.setTel(String.valueOf((i + 1) * 100));
      teachers.add(teacher);
    }

    return teachers;
  }
}

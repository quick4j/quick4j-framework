package com.github.quick4j.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.quick4j.core.mybatis.paging.model.DataPaging;
import com.github.quick4j.core.mybatis.paging.model.PageRequest;
import com.github.quick4j.core.repository.mybatis.MybatisRepository;
import com.github.quick4j.core.repository.mybatis.support.Order;
import com.github.quick4j.core.repository.mybatis.support.Sort;
import com.github.quick4j.entity.Teacher;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.util.*;

/**
 * @author zhaojh.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
                          "/spring-config.xml",
                          "/spring-config-mybatis.xml",
                          "/spring-config-jpa.xml"
                      })
public class RepositoryTest {

  @Resource
  private MybatisRepository mybatisRepository;


  @Test
  public void testJPA() {
  }

  @Test
  @Transactional
  public void insert() {
    Teacher teacher = new Teacher("Jack");
    teacher.setAge(32);
    teacher.setTel("110");
    teacher.setBirthDay(new Date());

    mybatisRepository.insert(teacher);

    Teacher t2 = mybatisRepository.selectById(Teacher.class, teacher.getId());
    assertThat(t2.getName()).isEqualToIgnoringCase("Jack");
  }

  @Test
  @Transactional
  public void batchInsert() {
    List<Teacher> theList = prepareData();

    mybatisRepository.insert(theList);

    List<Teacher> others = mybatisRepository.selectList(Teacher.class, null);
    assertThat(others).contains(theList.get(0), theList.get(1), theList.get(2));
  }

  @Test
  @Transactional
  public void selectByIds() {
    List<Teacher> theList = prepareData();

    mybatisRepository.insert(theList);

    List<String> ids = new ArrayList<String>();
    for (Teacher teacher : theList) {
      ids.add(teacher.getId());
    }

    List<Teacher>
        others =
        mybatisRepository.selectByIds(Teacher.class, ids.toArray(new String[]{}));
    assertThat(others).contains(theList.get(0), theList.get(1), theList.get(2));
  }

  @Test
  @Transactional
  public void selectByIdsAndSorting() {
    List<Teacher> theList = prepareData();
    System.out.println(theList);

    mybatisRepository.insert(theList);

    List<String> ids = new ArrayList<String>();
    for (Teacher teacher : theList) {
      ids.add(teacher.getId());
    }

    Sort sort = new Sort("age", Order.DESC);
    List<Teacher>
        others =
        mybatisRepository.selectByIds(Teacher.class, ids.toArray(new String[]{}), sort);
    assertThat(others).containsSequence(theList.get(2), theList.get(1), theList.get(0));
  }

  @Test
  @Transactional
  public void selectListByParamMap() {
    List<Teacher> theList = prepareData();
    mybatisRepository.insert(theList);

    Map<String, Object> parameters = new HashMap<String, Object>();
    parameters.put("age", 22);
    parameters.put("tel", "201");

    List<Teacher> anotherList = mybatisRepository.selectList(Teacher.class, parameters);
    assertThat(anotherList).contains(theList.get(1));
  }

  @Test
  @Transactional
  public void selectListByParamEntity() {
    List<Teacher> theList = prepareData();
    mybatisRepository.insert(theList);

    Teacher template = new Teacher();
    template.setAge(22);
    template.setTel("201");
    List<Teacher> anotherList = mybatisRepository.selectList(Teacher.class, template);
    assertThat(anotherList).contains(theList.get(1));
  }

  @Test
  @Transactional
  public void selectListAndSorting() {
    List<Teacher> theList = prepareData();
    mybatisRepository.insert(theList);

    Teacher template = new Teacher();
    template.setTel("201");
    Sort sort = new Sort("age", Order.DESC);
    List<Teacher> anotherList = mybatisRepository.selectList(Teacher.class, template, sort);

    assertThat(anotherList).containsSequence(theList.get(2), theList.get(1), theList.get(0));
  }

  @Test
  @Transactional
  public void updateById() {
    Teacher teacher = new Teacher("Jack");
    teacher.setAge(32);
    teacher.setTel("110");
    mybatisRepository.insert(teacher);

    teacher.setTel("120");
    teacher.setAge(30);
    mybatisRepository.updateById(teacher);

    Teacher t = mybatisRepository.selectById(Teacher.class, teacher.getId());
    assertThat(t.getTel()).isEqualToIgnoringCase("120");
    assertThat(t.getAge()).isEqualTo(30);
  }

  @Test
  @Transactional
  public void updateByIds() {
    List<Teacher> theList = prepareData();
    mybatisRepository.insert(theList);

    for (Teacher teacher : theList) {
      teacher.setAge(teacher.getAge() + 1);
    }

    mybatisRepository.updateById(theList);
    assertThat(mybatisRepository.selectList(Teacher.class, null))
        .contains(theList.toArray(new Teacher[]{}));
  }

  @Test
  @Transactional
  public void deleteById() {
    Teacher teacher = new Teacher("Jack");
    teacher.setAge(32);
    teacher.setTel("110");
    mybatisRepository.insert(teacher);

    Teacher another = mybatisRepository.selectById(Teacher.class, teacher.getId());
    Assert.assertEquals(another, teacher);

    mybatisRepository.deleteById(Teacher.class, teacher.getId());
    Teacher result = mybatisRepository.selectById(Teacher.class, teacher.getId());
    Assert.assertNull(result);
  }

  @Test
  @Transactional
  public void deleteByIds() {
    List<Teacher> theList = prepareData();
    mybatisRepository.insert(theList);

    List<String> ids = new ArrayList<String>();
    ids.add(theList.get(0).getId());
    ids.add(theList.get(1).getId());

    mybatisRepository.deleteByIds(Teacher.class, ids.toArray(new String[]{}));

    assertThat(mybatisRepository.selectList(Teacher.class, null))
        .doesNotContain(theList.get(0), theList.get(1))
        .contains(theList.get(2));
  }

  @Test
  @Transactional
  public void deleteByParams() {
    List<Teacher> theList = prepareData();
    mybatisRepository.insert(theList);

    Teacher template = new Teacher();
    template.setAge(22);
    template.setTel("201");
    mybatisRepository.deleteByParams(template);

    assertThat(mybatisRepository.selectList(Teacher.class, null))
        .doesNotContain(theList.get(1))
        .contains(theList.get(0), theList.get(2));
  }

  @Test
  @Transactional
  public void selectPaging() {
    List<Teacher> onePage = new ArrayList<Teacher>();
    List<Teacher> twoPage = new ArrayList<Teacher>();
    List<Teacher> threePage = new ArrayList<Teacher>();
    List<Teacher> fourPage = new ArrayList<Teacher>();
    for (int i = 0; i < 5; i++) {
      Teacher teacher = new Teacher(String.format("teacher-%d", i));
      teacher.setAge(20 + i);
      teacher.setTel("120");
      onePage.add(teacher);
    }

    for (int i = 5; i < 10; i++) {
      Teacher teacher = new Teacher(String.format("teacher-%d", i));
      teacher.setAge(20 + i);
      teacher.setTel("120");
      twoPage.add(teacher);
    }

    for (int i = 10; i < 15; i++) {
      Teacher teacher = new Teacher(String.format("teacher-%d", i));
      teacher.setAge(20 + i);
      teacher.setTel("120");
      threePage.add(teacher);
    }

    for (int i = 15; i < 18; i++) {
      Teacher teacher = new Teacher(String.format("teacher-%d", i));
      teacher.setAge(20 + i);
      teacher.setTel("120");
      fourPage.add(teacher);
    }

    List<Teacher> list = new ArrayList<Teacher>();
    list.addAll(onePage);
    list.addAll(twoPage);
    list.addAll(threePage);
    list.addAll(fourPage);

    mybatisRepository.insert(list);

    PageRequest<Teacher> pageRequest = new PageRequest<Teacher>(1, 5);
    pageRequest.setSort(new Sort("age"));
    DataPaging<Teacher> data1 = mybatisRepository.selectPaging(Teacher.class, pageRequest);
    assertThat(data1.getRows()).containsAll(onePage);

    PageRequest<Teacher> pageRequest4 = new PageRequest<Teacher>(4, 5);
    pageRequest4.setSort(new Sort("age"));
    DataPaging<Teacher> data4 = mybatisRepository.selectPaging(Teacher.class, pageRequest4);
    assertThat(data4.getRows()).containsAll(fourPage);
  }

  private List<Teacher> prepareData() {
    List<Teacher> theList = new ArrayList<Teacher>();
    String[] teacherNames = new String[]{"one", "two", "three"};

    for (int i = 0; i < teacherNames.length; i++) {
      Teacher teacher = new Teacher(teacherNames[i]);
      teacher.setAge(20 + i + 1);
//      teacher.setTel(String.valueOf((i+1)*100 + i));
      teacher.setTel("201");
      theList.add(teacher);
    }
    return theList;
  }
}

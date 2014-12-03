package com.github.quick4j.service;

import static org.hamcrest.CoreMatchers.*;

import com.github.quick4j.core.mybatis.paging.model.DataPaging;
import com.github.quick4j.core.mybatis.paging.model.PageRequest;
import com.github.quick4j.core.mybatis.paging.model.Pageable;
import com.github.quick4j.core.repository.mybatis.Repository;
import com.github.quick4j.core.repository.mybatis.support.Direction;
import com.github.quick4j.core.repository.mybatis.support.Order;
import com.github.quick4j.core.repository.mybatis.support.Sort;
import com.github.quick4j.entity.Teacher;
import org.apache.ibatis.binding.MapperMethod;
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
@ContextConfiguration({"/spring-config.xml", "/spring-config-mybatis.xml"})
public class repositoryTest {
    @Resource
    private Repository repository;

    @Test
    @Transactional
    public void testInsertAndFindOne(){
        Teacher teacher = new Teacher("Jack");
        teacher.setAge(32);
        teacher.setTel("110");
        teacher.setBirthDay(new Date());
        repository.insert(teacher);

        Teacher result = repository.find(Teacher.class, teacher.getId());
        Assert.assertThat(result.getName(), is("Jack"));
    }

    @Test
    @Transactional
    public void testUpdateForOne(){
        Teacher teacher = new Teacher("Jack");
        teacher.setAge(32);
        teacher.setTel("110");
        repository.insert(teacher);

        teacher.setTel("120");
        teacher.setAge(30);
        repository.updateById(teacher);

        Teacher t = repository.find(Teacher.class, teacher.getId());
        Assert.assertThat(t.getAge(), is(30));
        Assert.assertThat(t.getTel(), is("120"));
    }

    @Test
    @Transactional
    public void testBatchInsertAndUpdate(){
        List<Teacher> theList = new ArrayList<Teacher>();
        String[] teacherNames = new String[]{"one", "two", "three"};

        for (int i=0; i<teacherNames.length; i++){
            Teacher teacher = new Teacher(teacherNames[i]);
            teacher.setAge(20+i+1);
            teacher.setTel(String.valueOf((i+1)*100 + i));
            theList.add(teacher);
        }

        repository.insert(theList);

        List<Teacher> anotherList = repository.findAll(Teacher.class);
        Assert.assertThat(anotherList, hasItem(theList.get(0)));
        Assert.assertThat(anotherList, hasItem(theList.get(1)));
        Assert.assertThat(anotherList, hasItem(theList.get(2)));

        for (int i=0; i<theList.size(); i++){
            Teacher teacher = theList.get(i);
            teacher.setTel(String.valueOf((i+2)*100));
        }
        repository.updateById(theList);

        for (Teacher teacher : theList){
            Teacher another = repository.find(Teacher.class, teacher.getId());
            Assert.assertEquals(another.getTel(), teacher.getTel());
        }
    }

    @Test
    @Transactional
    public void testDeleteById(){
        Teacher teacher = new Teacher("Jack");
        teacher.setAge(32);
        teacher.setTel("110");
        repository.insert(teacher);

        Teacher another = repository.find(Teacher.class, teacher.getId());
        Assert.assertEquals(another, teacher);

        repository.delete(Teacher.class, teacher.getId());
        Teacher result = repository.find(Teacher.class, teacher.getId());
        Assert.assertNull(result);
    }

    @Test
    @Transactional
    public void testDeleteByIds(){
        List<Teacher> theList = new ArrayList<Teacher>();
        String[] teacherNames = new String[]{"one", "two", "three"};

        for (int i=0; i<teacherNames.length; i++){
            Teacher teacher = new Teacher(teacherNames[i]);
            teacher.setAge(20+i+1);
            teacher.setTel(String.valueOf((i+1)*100 + i));
            theList.add(teacher);
        }

        repository.insert(theList);

        List<Teacher> anotherList = repository.findAll(Teacher.class);
        List<String> ids = new ArrayList<String>();
        for (Teacher teacher : anotherList){
            ids.add(teacher.getId());
            boolean isContains = theList.contains(teacher);
            Assert.assertTrue(isContains);
        }

        repository.delete(Teacher.class, ids);
        anotherList = repository.findAll(Teacher.class);
        Assert.assertTrue(anotherList.isEmpty());
    }

    @Test
    @Transactional
    public void testDeleteEntity(){
        Teacher teacher = new Teacher("Jack");
        teacher.setAge(30);
        teacher.setTel("120");
        repository.insert(teacher);

        Teacher another = repository.find(Teacher.class, teacher.getId());
        Assert.assertThat(another.getName(), is("Jack"));

        repository.delete(another);

        Teacher result = repository.find(Teacher.class, teacher.getId());
        Assert.assertNull(result);
    }

    @Test
    @Transactional
    public void testFindAllByIds(){
        List<Teacher> theList = new ArrayList<Teacher>();
        String[] teacherNames = new String[]{"one", "two", "three"};

        for (int i=0; i<teacherNames.length; i++){
            Teacher teacher = new Teacher(teacherNames[i]);
            teacher.setAge(20+i+1);
            teacher.setTel(String.valueOf((i+1)*100 + i));
            theList.add(teacher);
        }

        repository.insert(theList);

        List<String> ids = new ArrayList<String>();
        for(int i=0; i<2; i++){
            Teacher teacher = theList.get(i);
            ids.add(teacher.getId());
        }
        List<Teacher> anotherList = repository.findByIds(Teacher.class, ids.toArray(new String[]{}));
        for (Teacher teacher : anotherList){
            boolean isContains = ids.contains(teacher.getId());
            Assert.assertTrue(isContains);
        }
    }

    @Test
    @Transactional
    public void testFindAllByParamMap(){
        List<Teacher> theList = new ArrayList<Teacher>();
        String[] teacherNames = new String[]{"one", "two", "three"};

        for (int i=0; i<teacherNames.length; i++){
            Teacher teacher = new Teacher(teacherNames[i]);
            teacher.setAge(30);
            teacher.setTel("110");
            theList.add(teacher);
        }

        repository.insert(theList);

        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("age", 30);
        parameters.put("tel", "110");
        List<Teacher> anotherList = repository.findByParams(Teacher.class, parameters);
        for (Teacher teacher : anotherList){
            boolean isContains = theList.contains(teacher);
            Assert.assertTrue(isContains);
        }
    }

    @Test
    @Transactional
    public void testFindAllByParamEntity(){
        List<Teacher> theList = new ArrayList<Teacher>();
        String[] teacherNames = new String[]{"one", "two", "three"};

        for (int i=0; i<teacherNames.length; i++){
            Teacher teacher = new Teacher(teacherNames[i]);
            teacher.setAge(30);
            teacher.setTel("110");
            theList.add(teacher);
        }

        repository.insert(theList);

        Teacher template = new Teacher();
        template.setAge(30);
        template.setTel("110");
        List<Teacher> anotherList = repository.findByParams(Teacher.class, template);
        for (Teacher teacher : anotherList){
            boolean isContains = theList.contains(teacher);
            Assert.assertTrue(isContains);
        }
    }

    @Test
    @Transactional
    public void testFindByParamAndSorting(){
        List<Teacher> theList = new ArrayList<Teacher>();
        String[] teacherNames = new String[]{"one", "two", "three"};

        for (int i=0; i<teacherNames.length; i++){
            Teacher teacher = new Teacher(teacherNames[i]);
            teacher.setAge(20 + i);
            teacher.setTel("110");
            teacher.setBirthDay(new Date());
            theList.add(teacher);
        }

        repository.insert(theList);

        Sort sort = new Sort(new Order(Direction.DESC, "age"));
        Teacher teacherParam = new Teacher();
        teacherParam.setTel("110");
        List<Teacher> anotherList = repository.findByParamsAndSorting(Teacher.class, teacherParam, sort);
        System.out.println(anotherList);
    }

    @Test
    @Transactional
    public void testFindPaging(){
        List<Teacher> theList = new ArrayList<Teacher>();
        for(int i=0; i<20; i++){
            Teacher teacher = new Teacher(String.format("teacher-%d", i));
            teacher.setAge(20+i);
            teacher.setTel("120");
            theList.add(teacher);
        }

        repository.insert(theList);


        for(int i=1; i<=4; i++){
            Pageable<Teacher> pageable = new PageRequest<Teacher>(i, 5);
            ((PageRequest)pageable).setSort(new Sort(new Order(Direction.ASC, "age")));
            DataPaging<Teacher> dataPaging = repository.findPaging(Teacher.class, pageable);
            Assert.assertThat(dataPaging.getRows(), hasItem(theList.get(i*5-3)));
            Assert.assertThat(dataPaging.getRows().size(), is(5));
        }
    }
}

package com.github.quick4j.service;

import static org.hamcrest.CoreMatchers.*;

import com.github.quick4j.core.mybatis.interceptor.model.DataPaging;
import com.github.quick4j.core.mybatis.interceptor.model.PageRequest;
import com.github.quick4j.core.service.Criteria;
import com.github.quick4j.core.service.CrudService;
import com.github.quick4j.core.service.PagingCriteria;
import com.github.quick4j.entity.Student;
import com.github.quick4j.entity.Teacher;
import com.github.quick4j.entity.User;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    private CrudService<User, Map> simpleCrudService;

    @Resource
    private CrudService<Teacher, Map> baseCrudService;

    @Test
    @Transactional
    @Rollback
    public void testCrud(){
        User jack = new User();
        jack.setLoginName("jack");
        jack.setName("Jack Chen");

        simpleCrudService.save(jack);

        Criteria<User, Map> criteria = simpleCrudService.createCriteria(User.class);
        User user1 = criteria.findOne(jack.getId());
        Assert.assertThat(user1.getLoginName(), is("jack"));

        jack.setPassword("1234");
        User user2 = simpleCrudService.save(jack);
        Assert.assertThat(user2.getPassword(), is("1234"));

        criteria.delete(user1.getId());

        User user3 = criteria.findOne(jack.getId());
        Assert.assertNull(user3);
    }

    @Test
    @Transactional
    @Rollback
    public void testSaveList(){
        User jack = new User();
        jack.setLoginName("jack");
        jack.setName("Jack Chen");
        simpleCrudService.save(jack);

        User tom = new User();
        tom.setLoginName("tom");
        tom.setName("Tom");
        tom.setPassword("123");

        List<User> list = new ArrayList<User>();
        jack.setPassword("456");
        list.add(jack);
        list.add(tom);
        simpleCrudService.save(list);

        Criteria<User, Map> criteria = simpleCrudService.createCriteria(User.class);
        User jack1 = criteria.findOne(jack.getId());
        User tom1 = criteria.findOne(tom.getId());

        System.out.println(jack1);
        System.out.println(tom1);

        Assert.assertThat(jack1.getPassword(), is("456"));
        Assert.assertThat(tom1.getLoginName(), is("tom"));
    }

    @Test
    @Transactional
    @Rollback
    public void testBatchDelete(){
        User jack = new User();
        jack.setLoginName("jack");
        jack.setName("Jack Chen");
        jack.setPassword("456");

        User tom = new User();
        tom.setLoginName("tom");
        tom.setName("Tom");
        tom.setPassword("123");

        List<User> list = new ArrayList<User>();
        list.add(jack);
        list.add(tom);
        simpleCrudService.save(list);

        Criteria<User, Map> criteria = simpleCrudService.createCriteria(User.class);
        criteria.delete(new String[]{jack.getId(), tom.getId()});
    }

    @Test
    @Transactional
    @Rollback
    public void testSaveMasterAndSlave(){
        Teacher wang = new Teacher("wang");
        Student jack = new Student("Jack");
        Student marry = new Student("Marry");

        wang.addStudent(jack);
        wang.addStudent(marry);

        baseCrudService.save(wang);

    }

    @Test
    @Transactional
    @Rollback
    @Ignore
    public void selectPagingTest(){
        List<User> users = prepare();
        simpleCrudService.save(users);

        PagingCriteria<User, Map> criteria = simpleCrudService.createPagingCriteria(User.class);
        DataPaging<User> dataPaging = criteria.findAll(new PageRequest<Map>(1, 10));

        Assert.assertThat(dataPaging.getRows(), hasItem(users.get(11)));
    }


    private List<User> prepare(){
        List<User> list = new ArrayList<User>();
        for(int i=0; i<20; i++){
            User user = new User();
            user.setName("user"+i);
            user.setLoginName("user"+i);
            user.setPassword("123");
            list.add(user);
        }
        return list;
    }
}

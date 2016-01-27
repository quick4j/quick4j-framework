package com.github.quick4j.logging;

import com.github.quick4j.core.service.SimpleCriteria;
import com.github.quick4j.core.service.SimpleCrudService;
import com.github.quick4j.entity.User;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhaojh
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
                          "/spring-config.xml",
                          "/spring-config-mybatis.xml",
                          "/spring-config-jpa.xml"
                      })
public class TestLogger {

  @Resource
  private SimpleCrudService<User> simpleCrudService;
  @Resource
  private HelloService helloService;

  @Test
  @Transactional
  public void testCreateEntity() {
    User jack = new User();
    jack.setName("Jack Chen");
    jack.setLoginName("jack");
    jack.setPassword("123");

    simpleCrudService.save(jack);
  }

  @Test
  @Transactional
  public void testCreateManyEntity() {
    User jack = new User();
    jack.setName("Jack Chen");
    jack.setLoginName("jack");
    jack.setPassword("123");

    User tom = new User();
    tom.setName("Tom Wang");
    tom.setLoginName("tom");
    tom.setPassword("123");

    List<User> list = new ArrayList<User>();
    list.add(jack);
    list.add(tom);

    simpleCrudService.save(list);
  }

  @Test
  @Transactional
  public void testModifyEntity() {
    User jack = new User();
    jack.setName("Jack Chen");
    jack.setLoginName("jack");
    jack.setPassword("123");
    simpleCrudService.save(jack);

    jack.setPassword("456");
    simpleCrudService.save(jack);
  }

  @Test
  @Transactional
  public void testModifyManyEntity() {
    User jack = new User();
    jack.setName("Jack Chen");
    jack.setLoginName("jack");
    jack.setPassword("123");
    simpleCrudService.save(jack);

    jack.setPassword("456");

    User tom = new User();
    tom.setName("Tom Wang");
    tom.setLoginName("tom");
    tom.setPassword("123");

    List<User> list = new ArrayList<User>();
    list.add(jack);
    list.add(tom);

    simpleCrudService.save(list);
  }

  @Test
  @Transactional
  public void testDeleteEntity() {
    User jack = new User();
    jack.setName("Jack Chen");
    jack.setLoginName("jack");
    jack.setPassword("123");
    simpleCrudService.save(jack);

    SimpleCriteria<User> criteria = simpleCrudService.newCriteria(User.class);
    criteria.delete(jack);
  }

  @Test
  @Transactional
  public void testDeleteById() {
    User jack = new User();
    jack.setName("Jack Chen");
    jack.setLoginName("jack");
    jack.setPassword("123");
    simpleCrudService.save(jack);

    SimpleCriteria<User> criteria = simpleCrudService.newCriteria(User.class);
    criteria.delete(jack.getId());
  }

  @Test
  @Transactional
  public void testDeleteByIds(){
    User jack = new User();
    jack.setName("Jack Chen");
    jack.setLoginName("jack");
    jack.setPassword("123");
    simpleCrudService.save(jack);

    User zhang = new User();
    zhang.setName("zhang");
    zhang.setLoginName("zhang");
    zhang.setPassword("123");
    simpleCrudService.save(zhang);

    SimpleCriteria<User> criteria = simpleCrudService.newCriteria(User.class);
    criteria.delete(new String[]{zhang.getId(), jack.getId()});
  }

  @Test
  @Transactional
  public void testWriteLogAnnontation() {
    User boy = new User();
    boy.setName("Jack");
    helloService.sayHello(boy);

    System.out.println("===========================");

    User girl = new User();
    girl.setName("Marry");
    helloService.sayLove(boy, girl);

    System.out.println("===========================");

    Map<String, String> one = new HashMap<String, String>();
    one.put("name", "Jack");

    Map<String, String> other = new HashMap<String, String>();
    other.put("name", "Marry");
    helloService.saySorry(one, other);

    helloService.sayLala();
  }

  @Test
  @Transactional
  public void testCustomLogBuilder() {
    helloService.sayGoodbye();
  }
}

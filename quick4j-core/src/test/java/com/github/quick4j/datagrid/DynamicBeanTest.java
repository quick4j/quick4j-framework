package com.github.quick4j.datagrid;

import com.github.quick4j.core.beans.DynamicBean;
import com.github.quick4j.core.util.JsonUtils;
import com.github.quick4j.core.util.UUIDGenerator;
import com.github.quick4j.entity.Action;
import com.github.quick4j.entity.Path;
import com.github.quick4j.entity.PathInfo;
import org.junit.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.cglib.beans.BeanGenerator;
import org.springframework.cglib.beans.BeanMap;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhaojh
 */
public class DynamicBeanTest {
    @Test
    public void test2(){
        PathInfo pathInfo = new PathInfo();
        pathInfo.setId(UUIDGenerator.generate32RandomUUID());
        pathInfo.setName("角色管理");
        pathInfo.setIcon("icon-role");
        pathInfo.setPid("123123");

        Action action = new Action();
        action.setId(UUIDGenerator.generate32RandomUUID());
        List<Action> actions = new ArrayList<Action>();
        actions.add(action);
        pathInfo.setActions(actions);

        DynamicBean dynamicBean = new DynamicBean(pathInfo);

        System.out.println(JsonUtils.toJson(dynamicBean.toMap()));
    }

    @Test
    public void test3(){
        BeanMap beanMap = BeanMap.create(new Person("Tom", 24));
        Map<String, Object> map = new HashMap<String, Object>(beanMap);
        map.put("sex", "man");
        System.out.println(JsonUtils.toJson(beanMap));
        System.out.println(JsonUtils.toJson(map));
    }

    @Test
    public void test4(){
        Person person = new Person("Tom", 24);


        BeanGenerator generator = new BeanGenerator();
        PropertyDescriptor[] descriptors = BeanUtils.getPropertyDescriptors(Person.class);
        for (PropertyDescriptor descriptor : descriptors){
            if(descriptor.getName().equals("class")) continue;
            generator.addProperty(descriptor.getName(), descriptor.getPropertyType());
        }

        Object object = generator.create();
        BeanUtils.copyProperties(person, object);
        System.out.println(JsonUtils.toJson(object));
    }

    public class Person{
        private String name;
        private int age;

        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }
}

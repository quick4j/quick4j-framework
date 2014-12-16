package com.github.quick4j.generic;

import com.github.quick4j.entity.User;
import org.junit.Ignore;
import org.junit.Test;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author zhaojh
 */
public class GenericTest {

    @Test
    @Ignore
    public void test1(){
        GenericClass<User> gc = new GenericClass<User>();

        User user = gc.select("1");

        System.out.println("======Interfaces======");
        Class[] interfaces = gc.getClass().getInterfaces();
        for(Class clazz : interfaces){
            System.out.println(clazz);
        }

        System.out.println("======Generic Interfaces======");
        Type[] gType = gc.getClass().getGenericInterfaces();
        for (Type type : gType){
            System.out.println(type instanceof ParameterizedType);
            System.out.println(type);
//            System.out.println(((ParameterizedType)type).getActualTypeArguments()[0]);
        }


        System.out.println("======Generic Superclass======");
        Type gs = gc.getClass().getGenericSuperclass();
        System.out.println(gs instanceof ParameterizedType);
    }
}

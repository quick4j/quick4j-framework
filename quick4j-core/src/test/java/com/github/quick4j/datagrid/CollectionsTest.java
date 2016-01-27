package com.github.quick4j.datagrid;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author zhaojh
 */
public class CollectionsTest {

  @Test
  public void testCopy1() {
    List<String> src = new ArrayList<String>();
    List<String> dest = new ArrayList<String>();
    for (int i = 0; i < 1000; i++) {
      src.add("hello-" + i);
      dest.add("world-" + i);
    }

    long begin = System.currentTimeMillis();
    Collections.copy(dest, src);
    System.out.println((System.currentTimeMillis() - begin));
  }

  @Test
  public void testCopy2() {
    List<Person> src = new ArrayList<Person>();
    List<Person> dest = new ArrayList<Person>();
    for (int i = 0; i < 5000; i++) {
      src.add(new Person("tom-" + i));
      dest.add(new Person("marry-" + i));
    }

    long begin = System.currentTimeMillis();
    Collections.copy(dest, src);
    System.out.println((System.currentTimeMillis() - begin));
  }

  public class Person {

    private String name;

    public Person(String name) {
      this.name = name;
    }
  }
}

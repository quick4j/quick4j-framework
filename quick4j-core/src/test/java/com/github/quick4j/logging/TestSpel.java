package com.github.quick4j.logging;

import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

/**
 * @author zhaojh
 */
public class TestSpel {

  public static class Foo {

    private String username;
    private Object[] args;

    public Foo(String username, Object[] args) {
      this.username = username;
      this.args = args;
    }

    public String getUsername() {
      return username;
    }

    public Object[] getArgs() {
      return args;
    }
  }

  public static class Person {

    private String name;
    private int age;

    public Person(String name, int age) {
      this.name = name;
      this.age = age;
    }

    public String getName() {
      return name;
    }

    public int getAge() {
      return age;
    }
  }

  public static void main(String[] args) {
    ExpressionParser parser = new SpelExpressionParser();

    Person person = new Person("Tom", 30);

    EvaluationContext
        context =
        new StandardEvaluationContext(new Foo("admin", new Object[]{person}));
    String
        result1 =
        (String) parser.parseExpression("#{username}向系统中录入了： #{args[0].name} 今年#{args[0].age}岁",
                                        new TemplateParserContext())
            .getValue(context);
    System.out.println("result1: " + result1);

    String s = ",args[0],args[1],";
    System.out.println(s.replaceAll("^\\,|\\,$", ""));
  }
}

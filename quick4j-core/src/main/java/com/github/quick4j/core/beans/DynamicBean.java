package com.github.quick4j.core.beans;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.springframework.beans.BeanUtils;
import org.springframework.cglib.beans.BeanGenerator;
import org.springframework.cglib.beans.BeanMap;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhaojh
 */
public class DynamicBean {

  private BeanMap beanMap;

  public DynamicBean(Object object) {
    Object bean = generateBean(object);
    BeanUtils.copyProperties(object, bean);
    beanMap = BeanMap.create(bean);
  }

  public Map<String, Object> toMap() {
    return new HashMap<String, Object>(beanMap);
  }

  private Object generateBean(Object object) {
    BeanGenerator generator = new BeanGenerator();
    PropertyDescriptor[] descriptors = BeanUtils.getPropertyDescriptors(object.getClass());
    for (PropertyDescriptor descriptor : descriptors) {
      Method readMethod = descriptor.getReadMethod();
      if (readMethod.isAnnotationPresent(JsonIgnore.class)) {
        continue;
      }
      if (descriptor.getName().equals("class")) {
        continue;
      }

      generator.addProperty(descriptor.getName(), descriptor.getPropertyType());
    }
    return generator.create();
  }
}

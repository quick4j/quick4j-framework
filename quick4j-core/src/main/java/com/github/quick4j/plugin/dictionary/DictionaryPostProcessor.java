package com.github.quick4j.plugin.dictionary;

import com.github.quick4j.plugin.dictionary.service.DictionaryService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * @author zhaojh
 */
@Component
public class DictionaryPostProcessor implements BeanPostProcessor {

  private static final Logger logger = LoggerFactory.getLogger(DictionaryPostProcessor.class);

  @Override
  public Object postProcessBeforeInitialization(Object bean, String beanName)
      throws BeansException {
    System.out.println("对象[" + beanName + "]开始实例化.");
    return bean;
  }

  @Override
  public Object postProcessAfterInitialization(Object bean, String beanName)
      throws BeansException {
    if (bean instanceof DictionaryService) {
      logger.info("加载动态字典数据。");
      System.out.println("加载动态字典数据。");
    }
    System.out.println("对象[" + beanName + "]实例化完成.");
    return bean;
  }
}

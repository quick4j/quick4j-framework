package com.github.quick4j.core.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.IOException;
import java.util.Properties;

/**
 * @author zhaojh. http://stackoverflow.com/questions/3888832/does-spring-messagesource-support-multiple-class-path
 */
public class ClasspathAllResourceBundleMessageSource extends ReloadableResourceBundleMessageSource {

  private Logger logger = LoggerFactory.getLogger(ClasspathAllResourceBundleMessageSource.class);

  private static final String PROPERTIES_SUFFIX = ".properties";

  ResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();

  @Override
  protected PropertiesHolder refreshProperties(String filename, PropertiesHolder propHolder) {
    return refreshClassPathProperties(filename, propHolder);
  }

  private PropertiesHolder refreshClassPathProperties(String filename,
                                                      PropertiesHolder propHolder) {
    Properties properties = new Properties();
    long lastModified = -1;
    try {
      Resource[] resources = resourceResolver.getResources(filename + PROPERTIES_SUFFIX);
      for (Resource resource : resources) {
        logger.info("加载文件:{}", resource.getURI().toString());
        String sourcePath = resource.getURI().toString().replace(PROPERTIES_SUFFIX, "");
        PropertiesHolder holder = super.refreshProperties(sourcePath, propHolder);
        properties.putAll(holder.getProperties());
        if (lastModified < resource.lastModified()) {
          lastModified = resource.lastModified();
        }
      }
    } catch (IOException ignored) {
    }

    return new PropertiesHolder(properties, lastModified);
  }
}

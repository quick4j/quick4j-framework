package com.github.quick4j.plugin.dictionary.service.support;

import com.github.quick4j.core.service.support.SimpleCrudService;
import com.github.quick4j.plugin.dictionary.entity.DicItem;
import com.github.quick4j.plugin.dictionary.service.DictionaryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author zhaojh
 */
@Service("DictionaryService")
public class DictionaryServiceImpl extends SimpleCrudService<DicItem>
        implements DictionaryService<DicItem>{
    private static final Logger logger = LoggerFactory.getLogger(DictionaryServiceImpl.class);

}

package com.github.quick4j.core.i18n;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.MessageSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Locale;

/**
 * @author zhaojh.
 */
@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration({"/config/spring-config.xml"})
public class I18nTest {
    @Resource
    private MessageSource messageSource;

//    @Test
    public void testMessage(){
        String message1 = messageSource.getMessage("exception.notFound", new Object[]{"Foo"}, Locale.CHINA);
        String message2 = messageSource.getMessage("exception.datagrid.options.notfound", new Object[]{"User"}, Locale.CHINA);

        System.out.println("message1: " + message1);
        System.out.println("message2: " + message2);
    }
}

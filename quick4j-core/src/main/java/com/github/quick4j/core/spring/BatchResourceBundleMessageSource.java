package com.github.quick4j.core.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

/**
 * 支持Ant-style风格的国际化文件批量加载。
 * @author zhaojh
 */
public class BatchResourceBundleMessageSource extends ResourceBundleMessageSource {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final String CLASSPATH_ALL_URL_PREFIX = "classpath*:";
    private static final String CLASSPATH_URL_PREFIX = "classpath:";
    private static final String RESOURCE_FILE_SUFFIX = ".properties";

    @Override
    public void setBasename(String basename) {
        if(basename.startsWith(CLASSPATH_URL_PREFIX) ||
                basename.startsWith(CLASSPATH_ALL_URL_PREFIX)){

            logger.info("Dynamic load i18n resource bundles.");
            ResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();

            try{
                Resource[] resources = null;
                if(basename.endsWith(RESOURCE_FILE_SUFFIX)){
                    resources = resourceResolver.getResources(basename);
                }else{
                    resources = resourceResolver.getResources(basename + RESOURCE_FILE_SUFFIX);
                }

                if(null != resources){
                    String[] basenames = new String[resources.length];
                    for(int i=0, length=resources.length; i<length; i++){
                        basenames[i] = getResourceBasenameFormPath(resources[i].getFile().getAbsolutePath());
                        logger.info("found i18n resource: {}", basenames[i]);
                    }

                    logger.info("i18n resource bundles found finish.");
                    setBasenames(basenames);
                }
            }catch (Exception e){
                throw new RuntimeException("Dynamic load i18n resource bundles fails.", e);
            }
        }else{
            logger.info("Static load resource bundles.");
            super.setBasename(basename);
        }
    }

    private String getResourceBasenameFormPath(String fullname){
        String basename = fullname.substring(0, fullname.lastIndexOf("."));

        if(basename.indexOf(".jar!/") > -1){
            return basename.substring(basename.indexOf(".jar!/")+6, basename.length());
        }

        if(basename.indexOf("classes/") > -1){
            return basename.substring(basename.indexOf("classes/")+8, basename.length());
        }

        return fullname;
    }
}

package com.github.quick4j.plugin.datagrid;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.github.quick4j.core.exception.NotFoundException;
import com.github.quick4j.core.util.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

/**
 * @author zhaojh
 */
@Component("dataGridManager")
public class DataGridManager implements InitializingBean, BeanPostProcessor {
    private static final Logger logger = LoggerFactory.getLogger(DataGridManager.class);

    private Map<String, DataGrid> dataGridMap = new ConcurrentHashMap<String, DataGrid>();
    private ObjectMapper mapper = null;
    private SimpleModule module = null;
    private String configLocations = "classpath*:config/datagrid/*.json";

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        mapper = new ObjectMapper();
        module = new SimpleModule();
        module.addDeserializer(DataGrid.class, new DataGridDeserializer(mapper));
        mapper.registerModule(module);
        logger.info("configLocations: {}", configLocations);
        build(configLocations);
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if(bean instanceof DataGrid){
            registDataGrid((DataGrid) bean);
        }

        if(bean instanceof DataSetProcessor){
            DataSetProcessor postProcessor = (DataSetProcessor)bean;
            String dataGridName = postProcessor.getName();
            DataGrid dataGrid = getDataGrid(dataGridName);
            dataGrid.setPostProcessor(postProcessor);
            logger.info("{}被注册到{}", postProcessor.getClass().getName(), dataGridName);
        }
        return bean;
    }

    public void setConfigLocations(String configLocations) {
        this.configLocations = configLocations;
    }

    public DataGrid buildCopy(String name){
        return getDataGrid(name).copySelf();
    }

    public void build(String configLocations){
        logger.info("scan DataGrid configure.");
        ResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();
        try {
            Resource[] resources = resourceResolver.getResources(configLocations);
            logger.info("resources length: {}", resources.length);
            for (Resource resource : resources){
                logger.info(resource.getFilename());
                DataGrid dataGrid = mapper.readValue(resource.getFile(), DataGrid.class);
                registDataGrid(dataGrid);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void registDataGrid(DataGrid dataGrid){
        if(!dataGridMap.containsKey(dataGrid.getName())){
            logger.info("regist dataGrid: {}", JsonUtils.toJson(dataGrid));
            dataGridMap.put(dataGrid.getName(), dataGrid);
        }
    }

    private DataGrid getDataGrid(String name){
        if(!dataGridMap.containsKey(name)){
            throw new NotFoundException("exception.datagrid.notfound", new Object[]{name});
        }
        return dataGridMap.get(name);
    }
}

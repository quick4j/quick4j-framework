package com.github.quick4j.plugin.datagrid;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zhaojh
 */
@Service("dataGridFactory")
public class DataGridFactoryBean implements InitializingBean {
    private static final Logger logger = LoggerFactory.getLogger(DataGridFactoryBean.class);
    private Map<String, DataGrid> dataGridMap = new ConcurrentHashMap<String, DataGrid>();
    private ObjectMapper mapper = null;
    private SimpleModule module = null;
    private String configLocations = "classpath*:config/datagrid/*.json";

    public void setConfigLocations(String configLocations) {
        this.configLocations = configLocations;
    }

    public DataGrid buildCopy(String name){
        if(dataGridMap.containsKey(name)){
            return dataGridMap.get(name).clone();
        }else{
            return null;
        }
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
                if(!dataGridMap.containsKey(dataGrid.getName())){
                    dataGridMap.put(dataGrid.getName(), dataGrid);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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
}

package com.github.quick4j.core.mybatis.mapping.builder;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhaojh.
 */
public class EntityAssistant {
    private static Map<Class, EntityPersistentInfo> entityPersistentInfoMap = new HashMap<Class, EntityPersistentInfo>();

    public static EntityPersistentInfo parse(Class entityClass){
        if(entityPersistentInfoMap.containsKey(entityClass)){
            return entityPersistentInfoMap.get(entityClass);
        }

        EntityPersistentInfo persistentInfo = new EntityPersistentInfo(entityClass);
        entityPersistentInfoMap.put(entityClass, persistentInfo);
        return persistentInfo;
    }
}

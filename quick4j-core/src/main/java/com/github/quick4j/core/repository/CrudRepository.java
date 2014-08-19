package com.github.quick4j.core.repository;

import java.util.Iterator;
import java.util.List;

/**
 * @author zhaojh
 */
public interface CrudRepository<Entity> {
    Entity findOne(String id);

    List<Entity> findAll(Object parameters);

    void save(Entity entity);

    void save(Iterator<Entity> entities);

    void update(Entity entity);

    void update(Iterator<Entity> entities);

    void delete(String id);

    void delete(String[] ids);
}

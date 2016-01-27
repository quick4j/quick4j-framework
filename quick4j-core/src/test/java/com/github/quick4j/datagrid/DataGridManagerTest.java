package com.github.quick4j.datagrid;

import com.github.quick4j.core.util.JsonUtils;
import com.github.quick4j.plugin.datagrid.DataGridManager;
import com.github.quick4j.plugin.datagrid.DataGrid;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * @author zhaojh
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
                          "/spring-config.xml",
                          "/spring-config-mybatis.xml"
                      })
public class DataGridManagerTest {

  @Resource
  private DataGridManager dataGridManager;

  @Test
  public void testLoadDataGrid() {
    DataGrid dynDataGrid = dataGridManager.buildCopy("dynamic");
    System.out.println(JsonUtils.toJson(dynDataGrid));

    DataGrid clone1 = dataGridManager.buildCopy("dictionary");
    System.out.println(JsonUtils.toJson(clone1));
  }

}

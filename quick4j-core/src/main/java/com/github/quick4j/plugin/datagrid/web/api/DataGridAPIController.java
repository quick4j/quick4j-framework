package com.github.quick4j.plugin.datagrid.web.api;

import com.github.quick4j.core.entity.Entity;
import com.github.quick4j.core.exception.NotFoundException;
import com.github.quick4j.core.mybatis.paging.model.DataPaging;
import com.github.quick4j.core.mybatis.paging.model.PageRequest;
import com.github.quick4j.core.repository.mybatis.support.Direction;
import com.github.quick4j.core.repository.mybatis.support.Order;
import com.github.quick4j.core.repository.mybatis.support.Sort;
import com.github.quick4j.core.service.Criteria;
import com.github.quick4j.core.service.CrudService;
import com.github.quick4j.core.web.http.JsonMessage;
import com.github.quick4j.plugin.datagrid.DataGrid;
import com.github.quick4j.plugin.datagrid.DataGridManager;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author zhaojh
 */
@Controller
@RequestMapping("/datagrid")
public class DataGridAPIController {
    private static final Logger logger = LoggerFactory.getLogger(DataGridAPIController.class);

    @Resource
    private DataGridManager dataGridManager;
    @Resource
    private CrudService<Entity> simpleCrudService;

    /**
     * 获取dataGrid的配置信息
     * @param name
     * @return
     */
    @RequestMapping(
            value = "/{name}/options",
            method = RequestMethod.GET,
            produces = "application/json;charset=utf-8"
    )
    @ResponseBody
    public JsonMessage getOptions(@PathVariable("name") String name){
        DataGrid dataGrid = dataGridManager.buildCopy(name);
        if(null == dataGrid){
            throw new NotFoundException("exception.datagrid.options.notfound", new Object[]{name});
        }

        return new JsonMessage().success(dataGrid);
    }

    /**
     * 加载数据
     * @param name
     * @return
     */
    @RequestMapping(
            value = "/{name}",
//            method = RequestMethod.POST,
            produces = "application/json;charset=utf-8"
    )
    @ResponseBody
    public JsonMessage loadDataFor(@PathVariable("name") String name,
                                     @RequestParam(value = "page", required = false) String page,
                                     @RequestParam(value = "rows", required = false) String limit,
                                     @RequestParam(value = "sort", required = false) String sortName,
                                     @RequestParam(value = "order", required = false) String sortOrder,
                                     HttpServletRequest request){

        DataGrid dataGrid = dataGridManager.buildCopy(name);

        int _page = 1, _size = Integer.MAX_VALUE;
        if(StringUtils.isNotBlank(page) && StringUtils.isNotBlank(limit)){
            _page = Integer.parseInt(page);
            _size = Integer.parseInt(limit);
        }

        logger.info("dataGrid: {}, page: {}, size: {}", name, _page, _size);


        Class entityClass = dataGrid.getEntity();
        Criteria  criteria = simpleCrudService.createCriteria(entityClass);
        PageRequest<Map<String, Object>> pageRequest = new PageRequest(_page, _size, wrapRequestMap(request));

        if(StringUtils.isNotBlank(sortName) && !sortName.contains(",")){
            pageRequest.setSort(new Sort(new Order(Direction.valueOf(sortOrder.toUpperCase()), sortName)));
        }

        DataPaging dataPaging = criteria.findAll(pageRequest);

        if(dataGrid.isSupportPostProcess()){
            dataPaging = dataGrid.getPostProcessor().process(dataPaging, pageRequest);
        }

        return new JsonMessage().success(dataPaging);
    }

    private Map<String, Object> wrapRequestMap(HttpServletRequest request){
        Map<String, Object> result = new HashMap<String, Object>();
        for(Iterator<Map.Entry<String, String[]>> iterator = request.getParameterMap().entrySet().iterator(); iterator.hasNext();){
            Map.Entry<String, String[]> entry = iterator.next();
            result.put(entry.getKey(), StringUtils.join(entry.getValue(), ""));
        }
        return result;
    }
}

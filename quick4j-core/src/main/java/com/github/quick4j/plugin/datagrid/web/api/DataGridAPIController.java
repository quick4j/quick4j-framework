package com.github.quick4j.plugin.datagrid.web.api;

import com.github.quick4j.core.entity.Entity;
import com.github.quick4j.core.exception.NotFoundException;
import com.github.quick4j.core.mybatis.interceptor.model.DataPaging;
import com.github.quick4j.core.mybatis.interceptor.model.PageRequest;
import com.github.quick4j.core.service.CrudService;
import com.github.quick4j.core.service.PagingCriteria;
import com.github.quick4j.core.web.http.AjaxResponse;
import com.github.quick4j.plugin.datagrid.DataGrid;
import com.github.quick4j.plugin.datagrid.DataGridFactoryBean;
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
@RequestMapping("/rest/datagrid")
public class DataGridAPIController {
    private static final Logger logger = LoggerFactory.getLogger(DataGridAPIController.class);

    @Resource
    private DataGridFactoryBean dataGridFactory;
    @Resource
    private CrudService<Entity, Map> simpleCrudService;

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
    public AjaxResponse getOptions(@PathVariable("name") String name){
        DataGrid dataGrid = dataGridFactory.buildCopy(name);
        if(null == dataGrid){
            throw new NotFoundException("datagrid.options.notfound", new Object[]{name});
        }

        return new AjaxResponse(AjaxResponse.Status.OK, dataGrid);
    }

    /**
     * 加载数据
     * @param name
     * @return
     */
    @RequestMapping(
            value = "/{name}",
            method = RequestMethod.POST,
            produces = "application/json;charset=utf-8"
    )
    @ResponseBody
    public AjaxResponse loadDataFor(@PathVariable("name") String name,
                                     @RequestParam(value = "page", required = false) String page,
                                     @RequestParam(value = "rows", required = false) String limit,
                                     HttpServletRequest request){

        DataGrid dataGrid = dataGridFactory.buildCopy(name);

        int _page = 1, _size = Integer.MAX_VALUE;
        if(StringUtils.isNotBlank(page) && StringUtils.isNotBlank(limit)){
            _page = Integer.parseInt(page);
            _size = Integer.parseInt(limit);
        }

        logger.info("dataGrid: {}, page: {}, size: {}", name, _page, _size);

        if(null == dataGrid){
            throw new NotFoundException("datagrid.notfound", new Object[]{name});
        }

        String dataModelFullName = dataGrid.getDataModel();
        try {
            Class clazz = Class.forName(dataModelFullName);
            PagingCriteria criteria = simpleCrudService.createPagingCriteria(clazz);
            PageRequest pageRequest = new PageRequest(_page, _size, wrapRequestMap(request));
            DataPaging dataPaging = criteria.list(pageRequest);
            return new AjaxResponse(AjaxResponse.Status.OK, dataPaging);

        } catch (ClassNotFoundException e) {
            logger.error("dataModel '{}' no exist.", dataGrid.getDataModel(), e);
            throw new NotFoundException("datagrid.datamodel.notfound", new Object[]{dataModelFullName, name});
        }
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

package com.github.quick4j.plugin.dictionary.web.controller;

import com.github.quick4j.core.service.Criteria;
import com.github.quick4j.core.service.CrudService;
import com.github.quick4j.core.web.http.AjaxResponse;
import com.github.quick4j.plugin.dictionary.entity.DicItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.swing.text.MaskFormatter;
import java.util.Map;

/**
 * @author zhaojh
 */
@Controller
public class DictionaryController {
    private static final Logger logger = LoggerFactory.getLogger(DictionaryController.class);
    private final String LOCATION = "dictionary/";

    @Resource
    private CrudService<DicItem> simpleCrudService;

    /**
     * listing
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public String listing(){
        return LOCATION + "index";
    }

    /**
     * show new form
     * @return
     */
    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String showAddPage(){
        return LOCATION + "new";
    }

    /**
     * create
     * @param dicItem
     * @return
     */
    @RequestMapping(
            value = "/new",
            method = RequestMethod.POST,
            produces = "application/json;charset=utf-8"
    )
    @ResponseBody
    public AjaxResponse doCreate(DicItem dicItem){
        logger.info("create dictionary.");
        simpleCrudService.save(dicItem);
        return new AjaxResponse(AjaxResponse.Status.OK);
    }

    /**
     * show edit form
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
    public String showEditPage(@PathVariable("id") String id){
        return LOCATION + "edit";
    }

    /**
     * edit
     * @param id
     * @param dicItem
     * @return
     */
    @RequestMapping(
            value = "/{id}/edit",
            method = RequestMethod.POST,
            produces = "application/json;charset=utf-8"
    )
    @ResponseBody
    public AjaxResponse doUpdate(@PathVariable("id") String id, DicItem dicItem){
        logger.info("update dictionary.");
        simpleCrudService.save(dicItem);
        return new AjaxResponse(AjaxResponse.Status.OK);
    }


    /**
     * delete
     * @param id
     * @return
     */
    @RequestMapping(
            value = "/{id}/delete",
            method = RequestMethod.GET,
            produces = "application/json;charset=utf-8"
    )
    @ResponseBody
    public AjaxResponse doDelete(@PathVariable("id") String id){
        Criteria<DicItem> criteria = simpleCrudService.createCriteria(DicItem.class);
        criteria.delete(id);
        return new AjaxResponse(AjaxResponse.Status.OK);
    }
}

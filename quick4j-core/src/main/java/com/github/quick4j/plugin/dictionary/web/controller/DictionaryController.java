package com.github.quick4j.plugin.dictionary.web.controller;

import com.github.quick4j.core.service.Criteria;
import com.github.quick4j.core.service.SimpleCrudService;
import com.github.quick4j.core.web.http.JsonMessage;
import com.github.quick4j.plugin.dictionary.entity.DicItem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @author zhaojh
 */
@Controller
@RequestMapping("/dictionary")
public class DictionaryController {

  private static final Logger logger = LoggerFactory.getLogger(DictionaryController.class);
  private final String LOCATION = "dictionary/";

  @Resource
  private SimpleCrudService<DicItem> simpleCrudService;

  /**
   * listing
   */
  @RequestMapping(method = RequestMethod.GET)
  public String listing() {
    return LOCATION + "index";
  }

  /**
   * show new form
   */
  @RequestMapping(value = "/new", method = RequestMethod.GET)
  public String showAddPage() {
    return LOCATION + "new";
  }

  /**
   * create
   */
  @RequestMapping(
      value = "/new",
      method = RequestMethod.POST,
      produces = "application/json;charset=utf-8"
  )
  @ResponseBody
  public JsonMessage doCreate(DicItem dicItem) {
    logger.info("create dictionary.");
    simpleCrudService.save(dicItem);
    return new JsonMessage().success();
  }

  /**
   * show edit form
   */
  @RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
  public String showEditPage(@PathVariable("id") String id) {
    return LOCATION + "edit";
  }

  /**
   * edit
   */
  @RequestMapping(
      value = "/{id}/edit",
      method = RequestMethod.POST,
      produces = "application/json;charset=utf-8"
  )
  @ResponseBody
  public JsonMessage doUpdate(@PathVariable("id") String id, DicItem dicItem) {
    logger.info("update dictionary.");
    dicItem.setId(id);
    simpleCrudService.save(dicItem);
    return new JsonMessage().success();
  }


  /**
   * delete
   */
  @RequestMapping(
      value = "/{id}/delete",
      method = RequestMethod.GET,
      produces = "application/json;charset=utf-8"
  )
  @ResponseBody
  public JsonMessage doDelete(@PathVariable("id") String id) {
    Criteria<DicItem> criteria = simpleCrudService.newCriteria(DicItem.class);
    criteria.delete(id);
    return new JsonMessage().success();
  }
}

package com.github.quick4j.plugin.logging.expression;

import org.springframework.expression.ParserContext;

/**
 * @author zhaojh
 */
public class CustomTemplateParserContext implements ParserContext {

  private final String expressionPrefix;

  private final String expressionSuffix;

  public CustomTemplateParserContext() {
    this("${", "}");
  }

  private CustomTemplateParserContext(String expressionPrefix, String expressionSuffix) {
    this.expressionPrefix = expressionPrefix;
    this.expressionSuffix = expressionSuffix;
  }

  @Override
  public boolean isTemplate() {
    return true;
  }

  @Override
  public String getExpressionPrefix() {
    return this.expressionPrefix;
  }

  @Override
  public String getExpressionSuffix() {
    return this.expressionSuffix;
  }
}

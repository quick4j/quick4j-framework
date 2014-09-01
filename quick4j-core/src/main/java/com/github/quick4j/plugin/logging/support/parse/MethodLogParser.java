package com.github.quick4j.plugin.logging.support.parse;

import com.github.quick4j.plugin.logging.StorageMedium;
import com.github.quick4j.plugin.logging.annontation.WriteLog;
import com.github.quick4j.plugin.logging.expression.ConvertAssistant;
import com.github.quick4j.plugin.logging.expression.CustomTemplateParserContext;
import org.apache.commons.lang3.StringUtils;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

/**
 * @author zhaojh
 */
public class MethodLogParser {
    private ExpressionParser spelParser = new SpelExpressionParser();
    private CustomTemplateParserContext templateParserContext = new CustomTemplateParserContext();

    public LogConfig parse(WriteLog writeLog, Object[] args){
        String logContentTemplate = writeLog.value();
        StorageMedium storageMedium = writeLog.to();
        String argsTemplate = writeLog.data().replaceAll("^\\,|\\,$","");

        ConvertAssistant assistant = new ConvertAssistant(args);
        EvaluationContext context = new StandardEvaluationContext(assistant);
        String logContent = (String)convertTemplate(context, logContentTemplate);
        Object[] argsArray = convertArgsTemplate(context, argsTemplate);

        return new LogConfig(true, storageMedium, logContent, argsArray);
    }

    private Object convertTemplate(EvaluationContext context, String templateString){
        return  spelParser
                .parseExpression(templateString, templateParserContext)
                .getValue(context);
    }

    private Object[] convertArgsTemplate(EvaluationContext context, String templateString){
        Object[] argsArray = null;
        if(StringUtils.isNotBlank(templateString)){
            String[] expressions = templateString.split(",");
            argsArray = new Object[expressions.length];
            for(int i=0; i<expressions.length; i++){
                Object arg = convertTemplate(context, expressions[i].trim());
                argsArray[i] = arg;
            }
        }
        return argsArray == null ? new String[]{}: argsArray;
    }
}

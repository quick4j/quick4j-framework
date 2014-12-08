package com.github.quick4j.plugin.logging;

import com.github.quick4j.plugin.logging.annontation.WriteLog;
import com.github.quick4j.plugin.logging.expression.ConvertAssistant;
import com.github.quick4j.plugin.logging.expression.CustomTemplateParserContext;
import org.apache.commons.lang3.StringUtils;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

/**
 * @author zhaojh.
 */
public class MethodLogParser implements LogParser{
    private WriteLog writeLog;
    private Object[] methodArgs;
    private ExpressionParser spelParser;
    private CustomTemplateParserContext parserContext;
    private EvaluationContext context;
    ConvertAssistant assistant;

    public MethodLogParser(WriteLog writeLog, Object[] methodArgs) {
        this.writeLog = writeLog;
        this.methodArgs = methodArgs;

        this.spelParser = new SpelExpressionParser();
        this.parserContext = new CustomTemplateParserContext();
        this.assistant = new ConvertAssistant(methodArgs);
        this.context = new StandardEvaluationContext(assistant);
    }

    @Override
    public LogConfig parse() {
        String logContent = getLogContent();
        Object[] extraData = getExtraData();
        return new LogConfig(true, logContent, extraData, LogConfig.LogType.OTHER);
    }

    private String getLogContent(){
        String contentExpression = writeLog.value();
        return (String) parseExpression(contentExpression, context);
    }

    private Object[] getExtraData(){
        String writeLogData = writeLog.data();
        Object[] extraData = new Object[]{""};
        if(null != writeLogData){
            String dataExpression = writeLogData.replaceAll("^\\,|\\,$", "");
            extraData = parseExtraData(dataExpression, context);
        }

        return extraData;
    }

    private Object parseExpression(String expressionString, EvaluationContext context){
        return spelParser
                .parseExpression(expressionString, parserContext)
                .getValue(context);
    }

    private Object[] parseExtraData(String expressionString, EvaluationContext context){
        Object[] argsArray = null;
        if(StringUtils.isNotBlank(expressionString)){
            String[] expressions = expressionString.split(",");
            argsArray = new Object[expressions.length];
            for(int i=0; i<expressions.length; i++){
                Object arg = parseExpression(expressions[i].trim(), context);
                argsArray[i] = arg;
            }
        }
        return argsArray == null ? new String[]{}: argsArray;
    }

}

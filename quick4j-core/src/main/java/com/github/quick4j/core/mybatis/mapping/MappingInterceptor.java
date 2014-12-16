package com.github.quick4j.core.mybatis.mapping;

import com.github.quick4j.core.mybatis.mapping.builder.MappedStatementAssistant;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.builder.annotation.ProviderSqlSource;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

/**
 * @author zhaojh.
 */
@Intercepts({
    @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
    @Signature(type = Executor.class, method = "update", args = {MappedStatement.class,Object.class})
})
public class MappingInterceptor implements Interceptor{
    private static Logger logger = LoggerFactory.getLogger(MappingInterceptor.class);

    private static final int MAPPED_STATEMENT_INDEX = 0;
    private static final int PARAMETER_INDEX = 1;
    private static final int ROWBOUNDS_INDEX = 2;
    private static final int RESULT_HANDLER_INDEX = 3;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        final Object[] queryArgs = invocation.getArgs();
        final MappedStatement ms = (MappedStatement) queryArgs[MAPPED_STATEMENT_INDEX];
        final Object parameter = queryArgs[PARAMETER_INDEX];
        final String sql = ms.getBoundSql(parameter).getSql();

        logger.debug("===> statement id: {}", ms.getId());
        logger.info("===> parameter: {}", parameter);
        logger.info("===> sql: {}", sql);

        if(ms.getSqlSource() instanceof ProviderSqlSource){
            switch (ms.getSqlCommandType()){
                case SELECT:
                    MappedStatement newStatement = MappedStatementAssistant.newSelectMappedStatement(ms, (Class) ((MapperMethod.ParamMap) parameter).get("type"));
                    queryArgs[MAPPED_STATEMENT_INDEX] = newStatement;
                    break;
            }
        }

        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        if(target instanceof Executor){
            return Plugin.wrap(target, this);
        }
        return target;
    }

    @Override
    public void setProperties(Properties properties) {}
}

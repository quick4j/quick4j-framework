package com.github.quick4j.core.mybatis.paging.dialect.support;

import com.github.quick4j.core.mybatis.paging.dialect.Dialect;

/**
 * @author zhaojh
 */
public class OracleDialect extends Dialect {
    @Override
    public boolean supportsLimit() {
        return true;
    }

    @Override
    public String getLimitString(String sql, int offset, int limit) {
        return getLimitString(sql, offset, Integer.toString(offset), Integer.toString(limit));
    }

    private String getLimitString(final String sql, final int offset,
                                  final String offsetPlaceholder, final String limitPlaceholder){
        String _sql = getLineSql(sql);
        StringBuilder pagingSelect = new StringBuilder(_sql.length() + 100);
        if (offset >= 0) {
            pagingSelect.append("select * from ( select row_.*, rownum rownum_ from ( ");
        } else {
            pagingSelect.append("select * from ( ");
        }
        pagingSelect.append(_sql);
        if (offset >= 0) {
            String endString = offsetPlaceholder + "+" + limitPlaceholder;
            pagingSelect.append(" ) row_ ) where rownum_ <= ")
                    .append(endString).append(" and rownum_ > ").append(offsetPlaceholder);
        } else {
            pagingSelect.append(" ) where rownum <= ").append(limitPlaceholder);
        }

        return pagingSelect.toString();
    }
}

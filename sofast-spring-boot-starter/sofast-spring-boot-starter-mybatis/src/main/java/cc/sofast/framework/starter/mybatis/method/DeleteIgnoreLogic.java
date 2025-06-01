package cc.sofast.framework.starter.mybatis.method;

import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.toolkit.sql.SqlScriptUtils;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

/**
 * @author Administrator
 */
public class DeleteIgnoreLogic extends AbstractMethod {
    /**
     * @since 3.5.0
     */
    public DeleteIgnoreLogic() {
        super("deleteIgnoreLogic");
    }

    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        SqlMethod sqlMethod = SqlMethod.DELETE;
        String sql = String.format(sqlMethod.getSql(), tableInfo.getTableName(),
                sqlWhereEntityWrapper(true, tableInfo),
                sqlComment());
        SqlSource sqlSource = super.createSqlSource(configuration, sql, modelClass);
        return this.addDeleteMappedStatement(mapperClass, methodName, sqlSource);
    }

    @Override
    protected String sqlWhereEntityWrapper(boolean newLine, TableInfo table) {
        /*
         * Wrapper SQL
         */
        String _sgEs_ = "<bind name=\"_sgEs_\" value=\"ew.sqlSegment != null and ew.sqlSegment != ''\"/>";
        String andSqlSegment = SqlScriptUtils.convertIf(String.format(" AND ${%s}", WRAPPER_SQLSEGMENT), String.format("_sgEs_ and %s", WRAPPER_NONEMPTYOFNORMAL), true);
        String lastSqlSegment = SqlScriptUtils.convertIf(String.format(" ${%s}", WRAPPER_SQLSEGMENT), String.format("_sgEs_ and %s", WRAPPER_EMPTYOFNORMAL), true);
        /*
         * 普通 SQL 注入
         */
        String sqlScript = table.getAllSqlWhere(false, false, true, WRAPPER_ENTITY_DOT);
        sqlScript = SqlScriptUtils.convertIf(sqlScript, String.format("%s != null", WRAPPER_ENTITY), true);
        sqlScript = SqlScriptUtils.convertWhere(sqlScript + NEWLINE + andSqlSegment) + NEWLINE + lastSqlSegment;
        sqlScript = SqlScriptUtils.convertIf(_sgEs_ + NEWLINE + sqlScript, String.format("%s != null", WRAPPER), true);
        return newLine ? NEWLINE + sqlScript : sqlScript;
    }
}

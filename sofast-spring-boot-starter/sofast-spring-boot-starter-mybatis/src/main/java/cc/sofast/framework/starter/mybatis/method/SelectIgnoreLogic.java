package cc.sofast.framework.starter.mybatis.method;

import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.toolkit.sql.SqlScriptUtils;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

/**
 * 查询满足条件一条数据，无论是否已逻辑删除
 *
 * @author Administrator
 * @see SqlMethod#SELECT_BY_ID
 * @see com.baomidou.mybatisplus.core.injector.methods.SelectById
 */
public class SelectIgnoreLogic extends AbstractMethod {

    public SelectIgnoreLogic() {
        super("selectIgnoreLogic");
    }

    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        SqlMethod sqlMethod = SqlMethod.SELECT_LIST;
        String sql = String.format(sqlMethod.getSql(), sqlFirst(), sqlSelectColumns(tableInfo, true), tableInfo.getTableName(),
                sqlWhereEntityWrapper(true, tableInfo), sqlOrderBy(tableInfo), sqlComment());
        SqlSource sqlSource = super.createSqlSource(configuration, sql, modelClass);
        return this.addSelectMappedStatementForTable(mapperClass, methodName, sqlSource, tableInfo);
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
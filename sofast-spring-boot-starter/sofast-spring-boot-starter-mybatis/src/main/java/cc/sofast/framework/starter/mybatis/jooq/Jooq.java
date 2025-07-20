package cc.sofast.framework.starter.mybatis.jooq;

import cc.sofast.framework.starter.common.utils.FiledNameUtils;
import cc.sofast.framework.starter.common.utils.SFunction;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Table;
import org.jooq.impl.DSL;

import java.util.List;
import java.util.Map;

/**
 * @author wxl
 */
public class Jooq {

    public static <T> Field<Object> field(SFunction<T> function) {
        String fieldName = FiledNameUtils.getFieldName(function);
        String underlineName = StrUtil.toUnderlineCase(fieldName);
        return DSL.field(underlineName);
    }

    public static <T> Table<Record> table(Class<T> entity) {
        TableInfo tableInfo = TableInfoHelper.getTableInfo(entity);
        return DSL.table(tableInfo.getTableName());
    }
}

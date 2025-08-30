package cc.sofast.framework.starter.jooq;

import cc.sofast.framework.starter.common.utils.FiledNameUtils;
import cc.sofast.framework.starter.common.utils.SFunction;
import cc.sofast.framework.starter.mybatis.dataobject.BaseDO;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Table;
import org.jooq.impl.DSL;

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

    public static <T extends Model<?>> Map<String, Object> insert(BaseDO<T, Long> entityInstance) {

        //todo 枚举
        return BeanUtil.beanToMap(entityInstance, true, true);
    }
}

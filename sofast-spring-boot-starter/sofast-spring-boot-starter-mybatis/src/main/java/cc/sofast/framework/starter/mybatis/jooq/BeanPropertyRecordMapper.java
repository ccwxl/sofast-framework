package cc.sofast.framework.starter.mybatis.jooq;

import lombok.extern.slf4j.Slf4j;
import org.jooq.Field;
import org.jooq.RecordMapper;
import org.springframework.beans.BeanUtils;

/**
 * @author wxl
 */
@Slf4j
public class BeanPropertyRecordMapper<Record extends org.jooq.Record, T> implements RecordMapper<Record, T> {
    private final Class<T> clazz;

    public BeanPropertyRecordMapper(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public T map(Record r) {
        Field<?>[] fields = r.fields();
        for (Field<?> field : fields) {
            log.info("field:{} val:{}", field.getName(), r.getValue(field.getName()));
        }
        return BeanUtils.instantiateClass(this.clazz);
    }
}

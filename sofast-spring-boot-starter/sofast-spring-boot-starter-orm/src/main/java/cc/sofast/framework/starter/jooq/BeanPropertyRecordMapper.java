package cc.sofast.framework.starter.jooq;

import lombok.extern.slf4j.Slf4j;
import org.jooq.Field;
import org.jooq.RecordMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.util.StringUtils;

import java.beans.PropertyDescriptor;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author wxl
 */
@Slf4j
public class BeanPropertyRecordMapper<Record extends org.jooq.Record, T> extends BeanPropertyRowMapper<T> implements RecordMapper<Record, T> {
    private final Class<T> mappedClass;
    private final Map<String, PropertyDescriptor> mappedProperties = new HashMap<>();

    @SuppressWarnings("unchecked")
    public BeanPropertyRecordMapper(Class<T> mappedClass) {
        super(mappedClass);
        this.mappedClass = mappedClass;
        for (PropertyDescriptor pd : BeanUtils.getPropertyDescriptors(mappedClass)) {
            if (pd.getWriteMethod() != null) {
                Set<String> mappedNames = mappedNames(pd);
                for (String mappedName : mappedNames) {
                    this.mappedProperties.put(mappedName, pd);
                }
            }
        }
    }

    @Override
    public T map(Record r) {
        BeanWrapperImpl bw = new BeanWrapperImpl();
        initBeanWrapper(bw);
        T mappedObject = BeanUtils.instantiateClass(this.mappedClass);
        bw.setBeanInstance(mappedObject);
        Field<?>[] fields = r.fields();
        for (Field<?> field : fields) {
            String property = lowerCaseName(StringUtils.delete(field.getName(), " "));
            PropertyDescriptor pd = (this.mappedProperties != null ? this.mappedProperties.get(property) : null);
            if (pd != null) {
                Class<?> propertyType = pd.getPropertyType();
                Object value = r.getValue(field.getName(), propertyType);
                bw.setPropertyValue(pd.getName(), value);
            }
        }
        return mappedObject;
    }
}

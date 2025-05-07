package cc.sofast.framework.starter.mybatis.trans;

import cc.sofast.framework.starter.common.trans.Translator;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.io.Serializable;

/**
 * 使用Mapper根据refVal查询条件查询出to(默认字段值)的值进行填充
 *
 * @author wxl
 */
public class DBTranslator<T extends Serializable> implements Translator<T, TransDB> {

    @Override
    public String transform(T refVal, TransDB annotation) {
        String filedName = annotation.ref();
        String filedValue = refVal.toString();
        Class<? extends BaseMapper<?>> mapper = annotation.mapper();
        String queryFileName = annotation.to();
        return Translator.super.transform(refVal, annotation);
    }
}

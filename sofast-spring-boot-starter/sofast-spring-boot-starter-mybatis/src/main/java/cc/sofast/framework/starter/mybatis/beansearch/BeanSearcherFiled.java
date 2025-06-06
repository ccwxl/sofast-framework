package cc.sofast.framework.starter.mybatis.beansearch;

import cn.zhxu.bs.FieldOp;

/**
 * @author wxl
 */
public record BeanSearcherFiled(Condition condition, String filedName, Object filedVal) {

    public Object getVal() {
        Class<? extends FieldOp> clazz = condition.getClazz();
        //TODO 根据类型转换

        return filedVal;
    }
}

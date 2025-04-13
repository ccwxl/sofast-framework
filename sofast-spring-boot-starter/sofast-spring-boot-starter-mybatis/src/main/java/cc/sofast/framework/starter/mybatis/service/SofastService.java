package cc.sofast.framework.starter.mybatis.service;

import cc.sofast.framework.starter.mybatis.dataobject.BaseDO;
import com.github.yulichang.base.MPJBaseService;

/**
 * @author wxl
 */
public interface SofastService<E extends BaseDO<?, ?>>
        extends MPJBaseService<E> {
}

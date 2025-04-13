package cc.sofast.framework.starter.mybatis.service;

import cc.sofast.framework.starter.mybatis.dataobject.BaseDO;
import com.github.yulichang.base.MPJBaseMapper;
import com.github.yulichang.base.MPJBaseServiceImpl;

/**
 * @author wxl
 */
public class SofastServiceImpl<M extends MPJBaseMapper<E>, E extends BaseDO<?, ?>>
        extends MPJBaseServiceImpl<M, E> implements SofastService<E> {
}

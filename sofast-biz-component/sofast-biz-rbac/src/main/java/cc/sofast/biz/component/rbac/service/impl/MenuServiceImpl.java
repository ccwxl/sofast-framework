package cc.sofast.biz.component.rbac.service.impl;

import cc.sofast.biz.component.rbac.MenuExtension;
import cc.sofast.biz.component.rbac.dataobject.SysMenu;
import cc.sofast.biz.component.rbac.mapper.SysMenuMapper;
import cc.sofast.biz.component.rbac.service.MenuService;
import cc.sofast.framework.starter.mybatis.service.SofastServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author wxl
 */
@Service
@RequiredArgsConstructor
public class MenuServiceImpl extends SofastServiceImpl<SysMenuMapper, SysMenu>
        implements MenuService {

    private final List<MenuExtension> extension;

    private final SysMenuMapper sysMenuMapper;

    /**
     * 添加菜单
     *
     * @param sysMenu 菜单
     * @return 是否添加成功
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addMenu(SysMenu sysMenu) {
        extension.forEach(s -> s.addBefore(sysMenu));
        sysMenuMapper.insert(sysMenu);
        extension.forEach(s -> s.addAfter(sysMenu));
        return false;
    }
}

package cc.sofast.biz.component.rbac.service.impl;

import cc.sofast.biz.component.common.ExtensionHandler;
import cc.sofast.biz.component.rbac.extension.MenuExtension;
import cc.sofast.biz.component.rbac.controller.request.SysMenuAddRequest;
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

    private final SysMenuMapper sysMenuMapper;

    /**
     * 添加菜单
     *
     * @param sysMenu 菜单
     * @return 是否添加成功
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addMenu(SysMenuAddRequest sysMenu) {
        ExtensionHandler.exec(MenuExtension.class, s -> s.addBefore(sysMenu));
        sysMenuMapper.insert(sysMenu);
        ExtensionHandler.exec(MenuExtension.class, s -> s.addAfter(sysMenu));
        return false;
    }
}

package cc.sofast.biz.component.rbac.service;

import cc.sofast.biz.component.rbac.dataobject.SysMenu;
import cc.sofast.framework.starter.mybatis.service.SofastService;

/**
 * @author wxl
 */
public interface MenuService extends SofastService<SysMenu> {

    /**
     * 添加菜单
     *
     * @param sysMenu 菜单
     * @return 是否添加成功
     */
    boolean addMenu(SysMenu sysMenu);
}

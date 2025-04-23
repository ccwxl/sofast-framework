package cc.sofast.biz.component.rbac.service.impl;

import cc.sofast.biz.component.rbac.dataobject.SysMenu;
import cc.sofast.biz.component.rbac.mapper.SysMenuMapper;
import cc.sofast.biz.component.rbac.service.MenuService;
import cc.sofast.framework.starter.mybatis.service.SofastServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author wxl
 */
@Service
@RequiredArgsConstructor
public class MenuServiceImpl extends SofastServiceImpl<SysMenuMapper, SysMenu>
        implements MenuService {

}

package cc.sofast.biz.component.rbac;

import cc.sofast.biz.component.rbac.dataobject.SysMenu;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wxl
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuContext {
    private SysMenu sysMenu;
    private Map<String, Object> data = new HashMap<>();

    public MenuContext(SysMenu sysMenu) {
        this.sysMenu = sysMenu;
    }
}

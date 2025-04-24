package cc.sofast.biz.component.rbac.controller.request;

import cc.sofast.biz.component.rbac.dataobject.SysMenu;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

import java.util.Map;

/**
 * @author wxl
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@FieldNameConstants
public class SysMenuAddRequest extends SysMenu {

    private Map<String, Object> ext;
}

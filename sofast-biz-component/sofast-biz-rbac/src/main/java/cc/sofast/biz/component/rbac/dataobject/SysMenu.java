package cc.sofast.biz.component.rbac.dataobject;

import cc.sofast.framework.starter.mybatis.dataobject.TenantBaseBO;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

/**
 * @author wxl
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@FieldNameConstants
@TableName(value = "${tb_prefix}sys_menu", autoResultMap = true)
public class SysMenu extends TenantBaseBO<Long, SysMenu> {

    private String title;
}


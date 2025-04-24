package cc.sofast.biz.component.rbac.dataobject;

import cc.sofast.framework.starter.mybatis.dataobject.TenantBaseDO;
import cc.sofast.framework.starter.web.validate.AddGroup;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
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
@Schema($schema = "菜单详情")
public class SysMenu extends TenantBaseDO<SysMenu, Long> {

    @Schema(description = "菜单名称")
    @NotBlank(groups = AddGroup.class, message = "菜单不能为空")
    private String title;
}


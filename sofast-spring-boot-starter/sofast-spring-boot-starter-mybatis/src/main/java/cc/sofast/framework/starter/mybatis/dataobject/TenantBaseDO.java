package cc.sofast.framework.starter.mybatis.dataobject;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

import java.io.Serializable;

/**
 * @author wxl
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@FieldNameConstants
public class TenantBaseDO<E extends Model<?>, P extends Serializable> extends BaseDO<E, P> {
    /**
     * 租户ID
     */
    @Schema(description = "租户ID")
    @TableField(value = "tenant_id")
    private String tenantId;
}

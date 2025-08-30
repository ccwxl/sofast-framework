package cc.sofast.framework.starter.mybatis.dataobject;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;
import org.apache.ibatis.type.JdbcType;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author wxl
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@FieldNameConstants
public class BaseDO<E extends Model<?>, P extends Serializable> extends Model<E> implements Serializable {
    public static final String NORM_DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    private static final String MYSQL_TS_FUN = "NOW(6)";
    /**
     * 主键ID
     */
    @Schema(description = "主键ID")
    @TableId(value = "id")
    private P id;

    /**
     * 创建时间
     */
    @Schema(description = "创建时刻")
    @DateTimeFormat(pattern = NORM_DATETIME_PATTERN)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = NORM_DATETIME_PATTERN)
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 最后更新时间
     */
    @Schema(description = "最后更新时间")
    @DateTimeFormat(pattern = NORM_DATETIME_PATTERN)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = NORM_DATETIME_PATTERN)
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /**
     * 创建者，目前使用 user 的 id 编号
     * <p>
     * 使用 String 类型的原因是，未来可能会存在非数值的情况，留好拓展性。
     */
    @Schema(description = "创建者")
    @TableField(value = "create_by", fill = FieldFill.INSERT, jdbcType = JdbcType.VARCHAR)
    private String createBy;

    /**
     * 更新者，目前使用 user 的 id 编号
     * <p>
     * 使用 String 类型的原因是，未来可能会存在非数值的情况，留好拓展性。
     */
    @Schema(description = "更新者")
    @TableField(value = "update_by", fill = FieldFill.INSERT_UPDATE, jdbcType = JdbcType.VARCHAR)
    private String updateBy;

    /**
     * 是否删除
     */
    @Schema(description = "逻辑删除标识", title = "逻辑删除标识 0:未删除 当前时间戳:已删除")
    @TableLogic(value = "0", delval = MYSQL_TS_FUN)
    @TableField(value = "deleted", jdbcType = JdbcType.VARCHAR)
    private String deleted;
}

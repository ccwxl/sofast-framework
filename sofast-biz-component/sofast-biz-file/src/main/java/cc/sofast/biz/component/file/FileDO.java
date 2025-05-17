package cc.sofast.biz.component.file;

import cc.sofast.framework.starter.mybatis.dataobject.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

import java.time.LocalDateTime;

/**
 * @author wxl
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@FieldNameConstants
@TableName(value = "${tb_prefix}file_detail", autoResultMap = true)
@Schema($schema = "文件详情")
public class FileDO extends TenantBaseDO<FileDO, String> {

    /**
     * 文件id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 文件访问地址
     */
    @TableField(value = "url")
    private String url;

    /**
     * 文件大小，单位字节
     */
    @TableField(value = "`size`")
    private Long size;

    /**
     * 文件名称
     */
    @TableField(value = "filename")
    private String filename;

    /**
     * 原始文件名
     */
    @TableField(value = "original_filename")
    private String originalFilename;

    /**
     * 基础存储路径
     */
    @TableField(value = "base_path")
    private String basePath;

    /**
     * 存储路径
     */
    @TableField(value = "`path`")
    private String path;

    /**
     * 文件扩展名
     */
    @TableField(value = "ext")
    private String ext;

    /**
     * MIME类型
     */
    @TableField(value = "content_type")
    private String contentType;

    /**
     * 存储平台
     */
    @TableField(value = "platform")
    private String platform;

    /**
     * 缩略图访问路径
     */
    @TableField(value = "th_url")
    private String thUrl;

    /**
     * 缩略图名称
     */
    @TableField(value = "th_filename")
    private String thFilename;

    /**
     * 缩略图大小，单位字节
     */
    @TableField(value = "th_size")
    private Long thSize;

    /**
     * 缩略图MIME类型
     */
    @TableField(value = "th_content_type")
    private String thContentType;

    /**
     * 文件所属对象id
     */
    @TableField(value = "object_id")
    private String objectId;

    /**
     * 文件所属对象类型，例如用户头像，评价图片
     */
    @TableField(value = "object_type")
    private String objectType;

    /**
     * 文件元数据
     */
    @TableField(value = "metadata")
    private String metadata;

    /**
     * 文件用户元数据
     */
    @TableField(value = "user_metadata")
    private String userMetadata;

    /**
     * 缩略图元数据
     */
    @TableField(value = "th_metadata")
    private String thMetadata;

    /**
     * 缩略图用户元数据
     */
    @TableField(value = "th_user_metadata")
    private String thUserMetadata;

    /**
     * 附加属性
     */
    @TableField(value = "attr")
    private String attr;

    /**
     * 哈希信息
     */
    @TableField(value = "hash_info")
    private String hashInfo;

    /**
     * 上传ID，仅在手动分片上传时使用
     */
    @TableField(value = "upload_id")
    private String uploadId;

    /**
     * 上传状态，仅在手动分片上传时使用，1：初始化完成，2：上传完成
     */
    @TableField(value = "upload_status")
    private Integer uploadStatus;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private LocalDateTime createTime;

    /**
     * 业务类型
     */
    @TableField(value = "biz_type")
    private String bizType;

    /**
     * 业务key
     */
    @TableField(value = "biz_key")
    private String bizKey;
}

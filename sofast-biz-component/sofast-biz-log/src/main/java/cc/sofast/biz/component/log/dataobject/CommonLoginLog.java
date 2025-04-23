package cc.sofast.biz.component.log.dataobject;

import cc.sofast.framework.starter.mybatis.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 登录日志
 *
 * @author wxl siaron.wang@gmail.com
 * @since 1.0.0 2024-05-28
 */

@Data
@TableName("common_login_log")
public class CommonLoginLog extends BaseDO<CommonLoginLog, Long> {
    /**
     * 用户账号
     */
    private String userName;

    /**
     * 登录IP地址
     */
    private String ip;

    /**
     * 登录地点
     */
    private String loginLocation;

    /**
     * 浏览器类型
     */
    private String browser;

    /**
     * 操作系统
     */
    private String os;

    /**
     * 登录状态（0成功 1失败）
     */
    private String status;

    /**
     * 提示消息
     */
    private String msg;

    /**
     * 访问时间
     */
    private LocalDateTime loginTime;

    /**
     * 租户id
     */
    private String tenantId;

    /**
     * 租户
     */
    private String tenantName;

    /**
     * 登录的产品
     */
    private String appName;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 认证方式
     */
    private String authType;

}
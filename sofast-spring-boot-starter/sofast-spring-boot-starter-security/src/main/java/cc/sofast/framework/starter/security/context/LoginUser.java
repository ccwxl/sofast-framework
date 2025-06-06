package cc.sofast.framework.starter.security.context;

import lombok.Data;
import lombok.experimental.FieldNameConstants;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wxl
 */
@Data
@FieldNameConstants
public class LoginUser {

    public static final String INFO_KEY_NICKNAME = "nickname";

    /**
     * 用户编号
     */
    private Long id;

    /**
     * 用户权限
     */
    private List<String> permissions;

    /**
     * 用户角色
     */
    private List<String> roles;

    /**
     * 用户组织
     */
    private List<Long> orgIds;

    /**
     * 访问令牌
     */
    private String accessToken;

    /**
     * 访问令牌的过期时间
     */
    private LocalDateTime tokenExpireTime;

    /**
     * 额外的用户信息
     */
    private Map<String, Object> info = new HashMap<>();

    public String nickName() {
        return (String) info.get(INFO_KEY_NICKNAME);
    }
}

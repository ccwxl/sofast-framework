package cc.sofast.framework.starter.security.context;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wxl
 */
@Data
public class LoginUser {

    public static final String INFO_KEY_NICKNAME = "nickname";

    /**
     * 用户编号
     */
    private String id;

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

    /**
     * 设置额外的用户信息
     *
     * @param key   键
     * @param value 值
     */
    public void putInfo(String key, String value) {
        info.put(key, value);
    }

    /**
     * 获取额外的用户信息
     *
     * @param key 键
     * @return 值
     */
    public Object getInfo(String key) {
        return info.get(key);
    }
}

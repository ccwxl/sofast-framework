package cc.sofast.framework.starter.security.utils;

import cc.sofast.framework.starter.common.utils.SpringUtils;
import cc.sofast.framework.starter.common.utils.json.JsonUtils;
import cc.sofast.framework.starter.redis.redisson.utils.RedissonUtils;
import cc.sofast.framework.starter.security.context.LoginUser;
import cc.sofast.framework.starter.security.token.UserInfoDetailService;
import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import org.redisson.api.RKeys;

import java.util.List;

/**
 * @author wxl
 */
public class RedisUserUtils {
    public static final String BASE_KEY = "login:user:";
    public static final String ORG_KEY = BASE_KEY + LoginUser.Fields.orgIds + ":%s";
    public static final String PERMISSIONS_KEY = BASE_KEY + LoginUser.Fields.permissions + "%s";
    public static final String ROLES_KEY = BASE_KEY + LoginUser.Fields.roles + ":%s";
    public static final UserInfoDetailService detailService = SpringUtils.getBean(UserInfoDetailService.class);

    public static LoginUser getLoginUser(Long userId) {
        LoginUser loginUser = new LoginUser();
        String orgString = RedissonUtils.getByKey(String.format(ORG_KEY, userId));
        List<Long> orgs;
        if (StrUtil.isBlank(orgString)) {
            orgs = detailService.getOrgs(userId);
            RedissonUtils.setKv(String.format(ORG_KEY, userId), JsonUtils.toJson(orgs));
        } else {
            orgs = JsonUtils.toObj(orgString, new TypeReference<>() {
            });
        }
        loginUser.setOrgIds(orgs);

        String permissionsString = RedissonUtils.getByKey(String.format(PERMISSIONS_KEY, userId));
        List<String> permissions;
        if (StrUtil.isBlank(permissionsString)) {
            permissions = detailService.getPermissions(userId);
            RedissonUtils.setKv(String.format(PERMISSIONS_KEY, userId), JsonUtils.toJson(permissions));
        } else {
            permissions = JsonUtils.toObj(permissionsString, new TypeReference<>() {
            });
        }
        loginUser.setPermissions(permissions);

        String rolesString = RedissonUtils.getByKey(String.format(ROLES_KEY, userId));
        List<String> roles;
        if (StrUtil.isEmpty(rolesString)) {
            roles = detailService.getRoles(userId);
            RedissonUtils.setKv(String.format(ROLES_KEY, userId), JsonUtils.toJson(roles));
        } else {
            roles = JsonUtils.toObj(rolesString, new TypeReference<>() {
            });
        }
        loginUser.setRoles(roles);

        return loginUser;
    }

    public static void cleanCache(Long userId) {
        RKeys keys = RedissonUtils.getRedissonClient().getKeys();
        keys.delete(String.format(ORG_KEY, userId),
                String.format(PERMISSIONS_KEY, userId),
                String.format(ROLES_KEY, userId));
    }
}

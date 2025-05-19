package cc.sofast.framework.starter.security.utils;

import cc.sofast.framework.starter.common.utils.SpringUtils;
import cc.sofast.framework.starter.redis.redisson.utils.RedissonUtils;
import cc.sofast.framework.starter.security.context.LoginUser;
import cc.sofast.framework.starter.security.token.UserInfoDetailService;
import cn.hutool.core.collection.CollUtil;
import org.redisson.api.RBucket;
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
        RBucket<List<Long>> bucket = RedissonUtils.getRedissonClient().getBucket(String.format(ORG_KEY, userId));
        List<Long> orgs = bucket.get();
        if (CollUtil.isEmpty(orgs)) {
            orgs = detailService.getOrgs(userId);
            RedissonUtils.setKv(String.format(ORG_KEY, userId), orgs);
        }
        loginUser.setOrgIds(orgs);

        List<String> permissions = RedissonUtils.getByKey(String.format(PERMISSIONS_KEY, userId));
        if (CollUtil.isEmpty(permissions)) {
            permissions = detailService.getPermissions(userId);
            RedissonUtils.setKv(String.format(PERMISSIONS_KEY, userId), permissions);
        }
        loginUser.setPermissions(permissions);

        List<String> roles = RedissonUtils.getByKey(String.format(ROLES_KEY, userId));
        if (CollUtil.isEmpty(roles)) {
            roles = detailService.getRoles(userId);
            RedissonUtils.setKv(String.format(ROLES_KEY, userId), roles);
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

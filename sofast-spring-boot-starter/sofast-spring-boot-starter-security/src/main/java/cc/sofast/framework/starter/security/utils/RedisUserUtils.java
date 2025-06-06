package cc.sofast.framework.starter.security.utils;

import cc.sofast.framework.starter.common.utils.SpringUtils;
import cc.sofast.framework.starter.redis.redisson.utils.RedisUtils;
import cc.sofast.framework.starter.security.config.SecurityConstant;
import cc.sofast.framework.starter.security.token.SecurityUserInfo;
import cc.sofast.framework.starter.security.token.SecurityUserInfoDetailService;
import org.redisson.api.RKeys;

/**
 * @author wxl
 */
public class RedisUserUtils {

    private static SecurityUserInfoDetailService detailService = SpringUtils.getBean(SecurityUserInfoDetailService.class);

    public static SecurityUserInfo getLoginUser(Long userId) {
        String key = key(userId);
        SecurityUserInfo securityUserInfo = RedisUtils.getByKey(key, SecurityUserInfo.class);
        if (securityUserInfo == null) {
            securityUserInfo = detailService.getUserInfo(userId);
            RedisUtils.setKv(key, securityUserInfo);
        }
        return securityUserInfo;
    }

    private static String key(Long userId) {

        return String.format(SecurityConstant.REDIS_USER_KEY, userId);
    }

    public static void cleanCache(Long userId) {
        RKeys keys = RedisUtils.getRedissonClient().getKeys();
        keys.delete(key(userId));
    }

    public void setDetailService(SecurityUserInfoDetailService detailService) {
        RedisUserUtils.detailService = detailService;
    }
}

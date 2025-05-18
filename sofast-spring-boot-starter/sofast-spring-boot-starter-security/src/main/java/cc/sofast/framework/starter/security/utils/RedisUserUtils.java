package cc.sofast.framework.starter.security.utils;

import cc.sofast.framework.starter.security.context.LoginUser;

/**
 * @author wxl
 */
public class RedisUserUtils {

    public static LoginUser getLoginUser(Long userId) {
        //cache get
        //if cache get null call UserInfoDetailService
        //put cache
        return null;
    }

    public static void cleanCache(Long userId) {
        //cache clean
    }
}

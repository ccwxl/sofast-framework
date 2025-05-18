package cc.sofast.framework.starter.security.token.impl;

import cc.sofast.framework.starter.security.context.LoginUser;
import cc.sofast.framework.starter.security.token.TokenService;
import cc.sofast.framework.starter.security.utils.RedisUserUtils;

/**
 * @author wxl
 */
public class RedisTokenService implements TokenService {


    @Override
    public LoginUser getLoginUser(String token) {
        Long userid = 0L;
        LoginUser loginUser = RedisUserUtils.getLoginUser(userid);
        loginUser.setAccessToken(token);
        return null;
    }
}

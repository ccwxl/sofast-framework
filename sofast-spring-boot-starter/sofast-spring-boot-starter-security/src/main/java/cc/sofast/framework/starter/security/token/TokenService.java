package cc.sofast.framework.starter.security.token;

import cc.sofast.framework.starter.security.context.LoginUser;

/**
 * @author wxl
 */
public interface TokenService {

    LoginUser getLoginUser(String token);
}

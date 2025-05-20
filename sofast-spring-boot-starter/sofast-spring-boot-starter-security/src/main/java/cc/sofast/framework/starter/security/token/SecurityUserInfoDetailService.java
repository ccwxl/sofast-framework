package cc.sofast.framework.starter.security.token;

import cc.sofast.framework.starter.security.context.LoginUser;

/**
 * @author wxl
 */
public interface SecurityUserInfoDetailService {

    SecurityUserInfo getUserInfo(Long userid);

}

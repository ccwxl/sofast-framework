package cc.sofast.framework.starter.security.token;

import java.util.Map;

/**
 * @author wxl
 */
public interface TokenService {

    TokenInfo createToken(Long uid, Map<String, Object> ext);

    TokenInfo loadByToken(String token);

}

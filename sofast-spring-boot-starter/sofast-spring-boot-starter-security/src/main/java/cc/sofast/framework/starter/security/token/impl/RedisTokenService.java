package cc.sofast.framework.starter.security.token.impl;

import cc.sofast.framework.starter.redis.redisson.utils.RedissonUtils;
import cc.sofast.framework.starter.security.SofastSecurityProperties;
import cc.sofast.framework.starter.security.config.SecurityConstant;
import cc.sofast.framework.starter.security.token.TokenInfo;
import cc.sofast.framework.starter.security.token.TokenService;
import cn.hutool.core.util.IdUtil;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * @author wxl
 */
public class RedisTokenService implements TokenService {

    private final SofastSecurityProperties properties;

    public RedisTokenService(SofastSecurityProperties securityProperties) {
        this.properties = securityProperties;
    }

    @Override
    public TokenInfo createToken(Long uid, Map<String, Object> ext) {
        TokenInfo tokenInfo = new TokenInfo();
        tokenInfo.setToken(generateToken());
        tokenInfo.setUid(uid);
        tokenInfo.setExt(ext);
        tokenInfo.setExpireTime(generateExpireTime());
        RedissonUtils.setKv(key(tokenInfo.getToken()), tokenInfo, properties.getTokenExpireTime());
        return tokenInfo;
    }

    private LocalDateTime generateExpireTime() {

        return LocalDateTime.now().plus(properties.getTokenExpireTime());
    }

    private String generateToken() {

        return IdUtil.fastSimpleUUID();
    }

    @Override
    public TokenInfo loadByToken(String token) {

        return RedissonUtils.getByKey(key(token), TokenInfo.class);
    }

    public String key(String token) {

        return String.format(SecurityConstant.REDIS_TOKEN_KEY, token);
    }
}

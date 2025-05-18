package cc.sofast.framework.starter.security.token;

import java.util.List;
import java.util.Map;

/**
 * @author wxl
 */
public interface UserInfoDetailService {

    List<String> getPermissions(Long userid);

    List<String> getRoles(Long userid);

    List<Long> getOrgs(Long userid);

    Map<String, Object> getUserInfo(Long userid);
}

package cc.sofast.framework.starter.security.token;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author wxl
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SecurityUserInfo {

    /**
     * 用户权限
     */
    private List<String> permissions;

    /**
     * 用户角色
     */
    private List<String> roles;

    /**
     * 用户组织
     */
    private List<Long> orgIds;

}

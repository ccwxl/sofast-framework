package cc.sofast.framework.starter.security.token;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * @author wxl
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenInfo {

    private String token;

    private Long uid;

    private Map<String, Object> ext;

    private LocalDateTime expireTime;
}

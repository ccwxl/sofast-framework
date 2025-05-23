package cc.sofast.framework.starter.websocket.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wxl
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SocketMessage {

    private String payload;
}

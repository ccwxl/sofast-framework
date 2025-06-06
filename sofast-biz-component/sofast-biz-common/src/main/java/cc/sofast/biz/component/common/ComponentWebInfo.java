package cc.sofast.biz.component.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wxl
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComponentWebInfo {

    private boolean enabled;

    private String basePath;
}

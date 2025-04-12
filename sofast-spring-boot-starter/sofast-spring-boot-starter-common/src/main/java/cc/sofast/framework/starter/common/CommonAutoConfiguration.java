package cc.sofast.framework.starter.common;

import cc.sofast.framework.starter.common.utils.SpringUtils;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Import;

/**
 * @author wxl
 */
@AutoConfiguration
@Import(SpringUtils.class)
public class CommonAutoConfiguration {
}

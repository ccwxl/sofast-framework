package cc.sofast.framework.starter.common;

import cc.sofast.framework.starter.common.utils.SpringUtil;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Import;

/**
 * @author wxl
 */
@AutoConfiguration
@Import(SpringUtil.class)
public class CommonAutoConfiguration {
}

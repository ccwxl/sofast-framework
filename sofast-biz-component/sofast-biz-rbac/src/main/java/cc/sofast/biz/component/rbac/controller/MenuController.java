package cc.sofast.biz.component.rbac.controller;

import cc.sofast.biz.component.rbac.RbacProperties;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 菜单接口
 *
 * @author wxl
 */
@Validated
@RestController
@AllArgsConstructor
@RequestMapping("${sofast.rbac.web.base-path}/menu/")
@Tag(name = "(core)菜单接口", description = "(core)菜单接口")
@ConditionalOnProperty(prefix = RbacProperties.WEB_PREFIX, name = "enabled", havingValue = "true")
public class MenuController {

}

package cc.sofast.biz.component.rbac.controller;

import cc.sofast.biz.component.common.HasPermission;
import cc.sofast.biz.component.rbac.RbacProperties;
import cc.sofast.biz.component.rbac.controller.request.SysMenuAddRequest;
import cc.sofast.biz.component.rbac.dataobject.SysMenu;
import cc.sofast.biz.component.rbac.service.MenuService;
import cc.sofast.framework.starter.common.dto.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
 * 菜单接口
 *
 * @author wxl
 */
@Slf4j
@Validated
@RestController
@AllArgsConstructor
@RequestMapping("${sofast.sofast.rbac.web.base-path:}/v1")
@Tag(name = "(biz)菜单接口", description = "(biz)菜单接口")
@ConditionalOnProperty(prefix = RbacProperties.WEB_PREFIX, name = "enabled", havingValue = "true", matchIfMissing = true)
public class MenuController {

    private final MenuService menuService;

    /**
     *
     */
    @PostMapping
    @Operation(summary = "添加菜单")
    @HasPermission
    public Result<Boolean> addMenu(@RequestBody SysMenuAddRequest sysMenu) {
        boolean ok = menuService.addMenu(sysMenu);
        log.info("添加菜单:{}", ok);
        return Result.ok(ok);
    }

    /**
     * 获取菜单
     *
     * @param id 菜单id
     * @return 菜单信息
     */
    @GetMapping("{id}")
    @Operation(summary = "根据id获取菜单")
    public Result<SysMenu> getMenu(@NotNull(message = "菜单id不能为空") @PathVariable String id) {
        SysMenu menu = menuService.getById(id);
        log.info("菜单信息:{}", menu);
        return Result.ok(menu);
    }


}

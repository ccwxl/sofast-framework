package cc.sofast.practice.controller;

import cc.sofast.biz.component.log.mapper.LogMapper;
import cc.sofast.biz.component.rbac.mapper.SysMenuMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wxl
 */
@RestController
@RequestMapping("/ex")
@RequiredArgsConstructor
public class ExampleController {

    private final SysMenuMapper sysMenuMapper;

    private final LogMapper logMapper;

    @GetMapping("/hello")
    @SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
    @Operation(summary = "描述1")
    public String hello() {
        return "hello";
    }
}

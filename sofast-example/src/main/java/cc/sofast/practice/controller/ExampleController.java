package cc.sofast.practice.controller;

import cc.sofast.biz.component.log.mapper.LogMapper;
import cc.sofast.biz.component.rbac.mapper.SysMenuMapper;
import lombok.RequiredArgsConstructor;
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

    @RequestMapping("/hello")
    public String hello() {
        return "hello";
    }
}

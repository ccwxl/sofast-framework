package cc.sofast.practice.module.apidoc.web;

import cc.sofast.biz.component.log.mapper.LogMapper;
import cc.sofast.biz.component.rbac.mapper.SysMenuMapper;
import cc.sofast.framework.starter.common.dto.Result;
import cc.sofast.practice.module.apidoc.request.CourseInfo;
import cc.sofast.practice.module.apidoc.request.CourseType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author wxl
 */
@RestController
@RequestMapping("/course")
@RequiredArgsConstructor
@Tag(name = "枚举接口", description = "枚举接口")
public class EnumApidocController {

    private final SysMenuMapper sysMenuMapper;

    private final LogMapper logMapper;

    @GetMapping("/enum/param")
    @SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
    @Operation(summary = "枚举参数")
    public String hello(@Schema(description = "性别") @RequestParam CourseType course) {
        return "hello";
    }

    @GetMapping("/enum/obj/param")
    @SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
    @Operation(summary = "对象枚举参数")
    public String hello2(@ParameterObject CourseInfo course) {
        return "hello";
    }

    @PostMapping("/enum/json")
    @SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
    @Operation(summary = "枚举json")
    public String hello3(@RequestBody CourseInfo course) {

        return "hello";
    }

    @PostMapping("/res/json")
    @SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
    @Operation(summary = "resJson")
    public Result<CourseInfo> res() {
        CourseInfo courseInfo = new CourseInfo();
        courseInfo.setCourse(CourseType.MATH);
        courseInfo.setScore(100);
        courseInfo.setStudent("wxl");
        courseInfo.setTs(System.currentTimeMillis());
        courseInfo.setData2(List.of("1", "2", "3"));
        courseInfo.setMaps(Map.of("1", "2", "3", "4"));
//        courseInfo.setCourses(List.of(CourseType.MATH, CourseType.ENGLISH));
        courseInfo.setDates(new java.util.Date());
        courseInfo.setLocalDates(java.time.LocalDate.now());
        courseInfo.setLocalDateTime(java.time.LocalDateTime.now());
        return Result.ok(courseInfo);
    }

    @PostMapping("/req/json")
    @SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
    @Operation(summary = "reqJson")
    public Result<CourseInfo> req(@RequestBody CourseInfo course) {

        return Result.ok(course);
    }
}

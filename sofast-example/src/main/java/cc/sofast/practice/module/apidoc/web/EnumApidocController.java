package cc.sofast.practice.module.apidoc.web;

import cc.sofast.biz.component.log.mapper.LogMapper;
import cc.sofast.biz.component.rbac.mapper.SysMenuMapper;
import cc.sofast.framework.starter.common.dto.PageParam;
import cc.sofast.framework.starter.common.dto.Result;
import cc.sofast.framework.starter.common.dto.TimeRangeParam;
import cc.sofast.practice.module.apidoc.request.CourseInfo;
import cc.sofast.practice.module.apidoc.request.CourseType;
import cc.sofast.practice.module.apidoc.request.QueryObj;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wxl
 */
@RestController
@RequestMapping("/apidoc")
@RequiredArgsConstructor
@Tag(name = "参数解析实例", description = "参数解析实例")
public class EnumApidocController {

    private final SysMenuMapper sysMenuMapper;

    private final LogMapper logMapper;

    @GetMapping("/singe/enum/parser")
    @SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
    @Operation(summary = "单个枚举参数解析")
    public Result<CourseType> hello(@Schema(description = "课程") @RequestParam CourseType course) {
        return Result.ok(course);
    }

    @GetMapping("/enum/obj/parser")
    @SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
    @Operation(summary = "对象枚举参数解析")
    public Result<CourseInfo> hello2(@ParameterObject CourseInfo course) {
        return Result.ok(course);
    }

    @PostMapping("/post/enum/json")
    @SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
    @Operation(summary = "post请求枚举解析")
    public Result<CourseInfo> hello3(@RequestBody CourseInfo course) {

        return Result.ok(course);
    }

    @PostMapping("/return/json")
    @SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
    @Operation(summary = "返回json")
    public Result<CourseInfo> res() {
        CourseInfo courseInfo = new CourseInfo();
        courseInfo.setCourse(CourseType.MATH);
        courseInfo.setScore(100);
        courseInfo.setStudent("wxl");
        courseInfo.setTs(System.currentTimeMillis());
        courseInfo.setData2(List.of("1", "2", "3"));
        courseInfo.setMaps(Map.of("1", "2", "3", "4"));
        courseInfo.setCourses(List.of(CourseType.MATH, CourseType.ENGLISH));
        courseInfo.setDates(new java.util.Date());
        courseInfo.setLocalDates(java.time.LocalDate.now());
        courseInfo.setLocalDateTime(java.time.LocalDateTime.now());

//        courseInfo.setCourseInfos(List.of(courseInfo));
        courseInfo.setCourseMaps(Map.of("1", CourseType.MATH, "2", CourseType.ENGLISH));
        courseInfo.setCourseInfoMaps(Map.of(CourseType.MATH, "1"));
        return Result.ok(courseInfo);
    }

    @PostMapping("/request/json")
    @SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
    @Operation(summary = "提交json")
    public Result<CourseInfo> req(@RequestBody CourseInfo course) {

        return Result.ok(course);
    }

    @PostMapping("/get/time/parser")
    @SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
    @Operation(summary = "get请求时间参数")
    public Result<CourseInfo> time(@ParameterObject CourseInfo course) {

        return Result.ok(course);
    }

    @PostMapping("/time/range/parser")
    @SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
    @Operation(summary = "时间范围请求")
    public Result<TimeRangeParam> getTimeRange(@Schema(type = "string") @RequestParam TimeRangeParam timeRange) {

        return Result.ok(timeRange);
    }

    @GetMapping("/page/parser")
    @SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
    @Operation(summary = "分页请求")
    public Result<Map<String, Object>> getPage(@Schema(type = "string") @RequestParam TimeRangeParam timeRange,
                                               @ParameterObject PageParam pageParam) {
        Map<String, Object> param = new HashMap<>();
        param.put("pageParam", pageParam);
        param.put("query", timeRange);
        return Result.ok(param);
    }

    @GetMapping("/page/obj/parser")
    @SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
    @Operation(summary = "分页对象请求")
    public Result<Map<String, Object>> getPageObj(@ParameterObject PageParam pageParam,
                                                  @Schema @ParameterObject QueryObj query) {
        Map<String, Object> param = new HashMap<>();
        param.put("pageParam", pageParam);
        param.put("query", query);
        return Result.ok(param);
    }
}

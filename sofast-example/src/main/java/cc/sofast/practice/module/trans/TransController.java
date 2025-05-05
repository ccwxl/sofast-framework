package cc.sofast.practice.module.trans;

import cc.sofast.framework.starter.common.dto.PageResult;
import cc.sofast.framework.starter.common.dto.Result;
import cc.sofast.framework.starter.mybatis.utils.PageUtil;
import cc.sofast.practice.module.trans.res.TransResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author wxl
 */
@RestController
@RequestMapping("/trans")
@RequiredArgsConstructor
@Tag(name = "翻译", description = "翻译")
public class TransController {

    @GetMapping("/sample")
    @Operation(summary = "简单对象")
    public Result<TransResponse> sample() {

        return Result.ok();
    }

    @GetMapping("/list")
    @Operation(summary = "集合")
    public Result<List<TransResponse>> list() {

        return Result.ok();
    }

    @GetMapping("/map")
    @Operation(summary = "map")
    public Result<Map<String, Object>> map() {

        return Result.ok();
    }

    @GetMapping("/page")
    @Operation(summary = "分页示例")
    public PageResult<TransResponse> page() {
        List<TransResponse> responses = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            responses.add(new TransResponse("id" + i, "name" + i, null, "courseStr" + i, null));
        }
        return PageResult.list(responses);
    }

    @GetMapping("/page2")
    @Operation(summary = "分页示例2")
    public PageResult<TransResponse> page2() {

        return PageResult.empty();
    }
}

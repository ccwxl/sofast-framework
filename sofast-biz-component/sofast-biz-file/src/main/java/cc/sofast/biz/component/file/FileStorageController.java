package cc.sofast.biz.component.file;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 文件相关接口
 *
 * @author wxl
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@Tag(name = "(biz-file)文件接口", description = "(biz-file)文件接口")
@RequestMapping(value = "${sofast.file.api.base-path}/file")
@ConditionalOnProperty(prefix = "sofast.file.api", name = "enabled", havingValue = "true", matchIfMissing = true)
public class FileStorageController {


}

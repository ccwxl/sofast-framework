package cc.sofast.biz.component.file;

import cn.hutool.core.io.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import java.io.File;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author wxl
 */
@Slf4j
@Disabled
class FileUploadUtilsTest {
    Path path = Path.of("C:\\Users\\Administrator\\Pictures\\Camera Roll\\20240610104932.jpg");

    @Test
    void getMimeType() {
        File imageFile = new File(path.toString());
        byte[] bytes = FileUtil.readBytes(imageFile);
        String mimeType1 = FileUtil.getMimeType(path);
        MockMultipartFile file = new MockMultipartFile("my.jpg", bytes);
        String mimeType = FileUploadUtils.getMimeType(file);
        log.info("mimeType1: {}", mimeType1);
        log.info("mineType: {}", mimeType);
        assertNotNull(mimeType);
    }

    @Test
    void isImage() {
        File imageFile = new File(path.toString());
        byte[] bytes = FileUtil.readBytes(imageFile);
        MockMultipartFile file = new MockMultipartFile("my.jpg", bytes);
        boolean image = FileUploadUtils.isImage(file);
        assertTrue(image);
    }
}
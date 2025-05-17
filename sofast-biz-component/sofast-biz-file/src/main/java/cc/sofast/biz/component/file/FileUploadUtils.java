package cc.sofast.biz.component.file;

import com.alibaba.ttl.TransmittableThreadLocal;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.tika.Tika;
import org.dromara.x.file.storage.core.FileInfo;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 文件工具类
 *
 * @author wxl
 */
public class FileUploadUtils {
    private static final Pattern IMAGE_PATTERN = Pattern.compile("image/.*");
    private static final ThreadLocal<Tika> TIKA = TransmittableThreadLocal.withInitial(Tika::new);

    /**
     * 获取类型
     *
     * @param file MultipartFile
     * @return String
     */
    public static String getMimeType(MultipartFile file) {
        try (InputStream stream = file.getInputStream()) {
            return TIKA.get().detect(stream);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    /**
     * 判断是否是图片
     *
     * @param file MultipartFile
     * @return boolean
     */
    public static boolean isImage(MultipartFile file) {
        String type = getMimeType(file);
        Matcher m = IMAGE_PATTERN.matcher(type);
        return m.matches();
    }

    /**
     * 上传文件
     *
     * @return FileInfo
     */
    public static FileInfo uploadFile(FileUploadParams params, MultipartFile file) {

        return null;
    }

    /**
     * 下载文件
     *
     * @param id           文件ID
     * @param outputStream 输出流
     */
    public static void downloadFile(String id, OutputStream outputStream) {

    }

    /**
     * 下载文件
     *
     * @param id       文件ID
     * @param name     文件名
     * @param response 响应流
     */
    public static void downloadFile(String id, String name, HttpServletResponse response) {

    }
}

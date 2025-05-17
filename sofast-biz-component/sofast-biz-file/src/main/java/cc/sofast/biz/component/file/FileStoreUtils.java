package cc.sofast.biz.component.file;

import cc.sofast.framework.starter.common.utils.SpringUtils;
import com.alibaba.ttl.TransmittableThreadLocal;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.tika.Tika;
import org.dromara.x.file.storage.core.FileInfo;
import org.dromara.x.file.storage.core.FileStorageService;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * 文件工具类
 *
 * @author wxl
 */
public class FileStoreUtils {
    private static final String IMAGE_START = "image";
    private static final ThreadLocal<Tika> TIKA = TransmittableThreadLocal.withInitial(Tika::new);
    private static volatile FileStorageService fileStorageService;

    private static FileStorageService getFs() {
        if (fileStorageService == null) {
            synchronized (FileStoreUtils.class) {
                if (fileStorageService == null) {
                    fileStorageService = SpringUtils.getBean(FileStorageService.class);
                }
            }
        }
        return fileStorageService;
    }

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
            return null;
        }
    }

    /**
     * 判断是否是图片
     *
     * @param file MultipartFile
     * @return boolean
     */
    public static boolean isImage(MultipartFile file) {
        try (InputStream stream = file.getInputStream()) {
            return TIKA.get().detect(stream).startsWith(IMAGE_START);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 判断是否是图片
     *
     * @param file MultipartFile
     * @return boolean
     */
    public static boolean isImage(File file) {
        try {
            return TIKA.get().detect(file).startsWith(IMAGE_START);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 上传文件
     *
     * @return FileInfo
     */
    public static FileInfo uploadFile(FileUploadParams params, MultipartFile file) {

        return uploadFileInner(params, file.getName(), FileStoreUtils.isImage(file), file);
    }

    /**
     * 上传文件
     *
     * @return FileInfo
     */
    private static FileInfo uploadFileInner(FileUploadParams params, String name, boolean thumbnail, Object file) {
        //验证文件类型
        FileProperties fileProperties = SpringUtils.getBean(FileProperties.class);
        if (fileProperties.denied(name)) {
            throw new FileException(FileErrorCode.NOT_ALLOWED_FILE_TYPE);
        }
        FileStorageService fileStorageService = SpringUtils.getBean(FileStorageService.class);
        String platform = params.getPlatform();
        if (StringUtils.isBlank(params.getPlatform())) {
            platform = fileStorageService.getProperties().getDefaultPlatform();
        }
        boolean supportAcl = fileStorageService.isSupportAcl(params.getPlatform());
        //上传文件
        return fileStorageService
                .of(file)
                .setPlatform(platform)
                .thumbnail(thumbnail)
                .setAcl(supportAcl, params.getAcl())
                .upload();
    }

    /**
     * 上传文件
     *
     * @return FileInfo
     */
    public static FileInfo uploadFile(FileUploadParams params, File file) {

        return uploadFileInner(params, file.getName(), isImage(file), file);
    }

    /**
     * 下载文件
     *
     * @param id           文件ID
     * @param outputStream 输出流
     */
    public static void downloadFile(String id, OutputStream outputStream) {
        FileInfo fileInfo = getFileInfo(id);
        getFs().download(fileInfo).outputStream(outputStream);
    }

    /**
     * 下载文件
     *
     * @param id       文件ID
     * @param filename 文件名
     * @param response 响应流
     */
    public static void downloadFile(String id, String filename, HttpServletResponse response) throws Exception {
        FileInfo fileInfo = getFileInfo(id);
        if (StringUtils.isBlank(filename)) {
            filename = fileInfo.getOriginalFilename();
        }
        response.setHeader("Content-Disposition",
                "attachment;filename=" + URLEncoder.encode(filename, StandardCharsets.UTF_8.name()));
        response.setContentType(fileInfo.getContentType());
        getFs().download(fileInfo).outputStream(response.getOutputStream());
    }

    /**
     * 获取文件信息
     *
     * @param id 文件ID
     * @return FileInfo
     */
    public static FileInfo getFileInfo(String id) {
        FileService fileService = SpringUtils.getBean(FileService.class);
        SofastFileRecorder fileRecorder = SpringUtils.getBean(SofastFileRecorder.class);
        FileDO fileDO = fileService.getById(id);
        if (fileDO == null) {
            throw new FileException(FileErrorCode.FILE_NOT_EXIST);
        }
        return fileRecorder.toFileInfo(fileDO);
    }
}

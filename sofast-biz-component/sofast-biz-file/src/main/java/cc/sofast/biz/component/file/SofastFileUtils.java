package cc.sofast.biz.component.file;

import org.apache.tika.metadata.HttpHeaders;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.metadata.TikaCoreProperties;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.dromara.x.file.storage.core.FileInfo;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.helpers.DefaultHandler;

import java.io.InputStream;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 文件工具类
 *
 * @author wxl
 */
public class SofastFileUtils {

    private static final Pattern IMAGE_PATTERN = Pattern.compile("image/.*");

    /**
     * 获取类型
     *
     * @param file MultipartFile
     * @return String
     */
    public static String getMimeType(MultipartFile file) {
        AutoDetectParser parser = new AutoDetectParser();
        parser.setParsers(new HashMap<>());
        Metadata metadata = new Metadata();
        metadata.add(TikaCoreProperties.RESOURCE_NAME_KEY, file.getName());
        try (InputStream stream = file.getInputStream()) {
            parser.parse(stream, new DefaultHandler(), metadata, new ParseContext());
        } catch (Exception e) {
            throw new RuntimeException();
        }
        return metadata.get(HttpHeaders.CONTENT_TYPE);
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
}

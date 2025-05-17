package cc.sofast.biz.component.file;

import cc.sofast.framework.starter.common.exception.ErrorCode;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wxl
 */
public enum FileErrorCode implements ErrorCode {
    NOT_ALLOWED_FILE_TYPE("F0001", "不允许此文件类型上传"),

    FILE_NOT_EXIST("F0002", "文件不存在"),

    FILE_PRIVATE_NOT_READER("F0003", "私有文件不允许通过此接口下载");


    private final String code;
    private final String message;

    private static final Map<String, FileErrorCode> CODE_MAP = new HashMap<>();

    static {
        for (FileErrorCode e : values()) {
            CODE_MAP.put(e.code, e);
        }
    }

    FileErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}

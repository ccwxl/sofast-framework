package cc.sofast.framework.starter.common.dto;

import cc.sofast.framework.starter.common.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

import java.io.Serializable;

/**
 * @author wxl
 */
@Data
@FieldNameConstants
@AllArgsConstructor
@NoArgsConstructor
public class ResultWrapper<T> implements Serializable {
    public static final String SUCCESS = "Success";
    public static final String FAIL = "Fail";
    public static final String SUCCESS_CODE = "00000";
    public static final String FAIL_CODE = "A0500";
    private String code;
    private boolean success;
    private String message;
    private T data;
    private long timestamp = System.currentTimeMillis();

    public ResultWrapper(String code, boolean success, String message, T data) {
        this.code = code;
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public static <T> ResultWrapper<T> ok() {
        return new ResultWrapper<>(SUCCESS_CODE, true, SUCCESS, null);
    }

    public static <T> ResultWrapper<T> ok(T data) {
        return new ResultWrapper<>(SUCCESS_CODE, true, SUCCESS, data);
    }

    public static <T> ResultWrapper<T> ok(String msg, T data) {
        return new ResultWrapper<>(SUCCESS_CODE, true, msg, data);
    }

    public static <T> ResultWrapper<T> ok(String code, String msg, T data) {
        return new ResultWrapper<>(code, true, msg, data);
    }

    public static <T> ResultWrapper<T> fail(String msg) {
        return new ResultWrapper<>(FAIL_CODE, false, FAIL, null);
    }

    public static <T> ResultWrapper<T> fail(String msg, T data) {
        return new ResultWrapper<>(FAIL_CODE, false, FAIL, data);
    }

    public static <T> ResultWrapper<T> fail(String code, String msg, T data) {
        return new ResultWrapper<>(code, false, msg, data);
    }

    public static <T> ResultWrapper<T> fail(ErrorCode errorCode) {
        return new ResultWrapper<>(errorCode.getCode(), false, errorCode.getMessage(), null);
    }

    public static <T> ResultWrapper<T> fail(ErrorCode errorCode, T data) {
        return new ResultWrapper<>(errorCode.getCode(), false, errorCode.getMessage(), data);
    }
}

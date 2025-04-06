package cc.sofast.farmework.starter.common.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author wxl
 */
@Data
public class Result<T> implements Serializable {
    private int code;
    private String message;
    private T data;
}

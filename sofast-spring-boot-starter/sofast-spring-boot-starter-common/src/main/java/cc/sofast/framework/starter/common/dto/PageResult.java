package cc.sofast.framework.starter.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

import java.util.List;

/**
 * 分页结果类
 *
 * @author wxl
 */
@Data
@FieldNameConstants
@AllArgsConstructor
public class PageResult<T> extends Result<List<T>> {

    private Long totalCount = 0L;

    private Long pageSize = 1L;

    private Long pageIndex = 1L;

    private boolean pageResult = true;

    public PageResult() {
        super(SUCCESS_CODE, true, SUCCESS, null);
    }

    public PageResult(List<T> data) {
        super(SUCCESS_CODE, true, SUCCESS, data);
        long size = 0L;
        if (data != null) {
            size = data.size();
        }
        this.totalCount = size;
        this.pageSize = size;
    }
}

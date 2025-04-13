package cc.sofast.framework.starter.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

import java.util.List;

/**
 * @author wxl
 */
@Data
@FieldNameConstants
@AllArgsConstructor
@NoArgsConstructor
public class PageResult<T> {

    private Long totalCount = 0L;

    private Long pageSize = 1L;

    private Long pageIndex = 1L;

    private List<T> records;

}

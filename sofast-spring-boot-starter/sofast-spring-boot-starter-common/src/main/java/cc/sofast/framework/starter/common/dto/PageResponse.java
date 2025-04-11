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
public class PageResponse<T> {

    private int totalCount = 0;

    private int pageSize = 1;

    private int pageIndex = 1;

    private List<T> records;

}

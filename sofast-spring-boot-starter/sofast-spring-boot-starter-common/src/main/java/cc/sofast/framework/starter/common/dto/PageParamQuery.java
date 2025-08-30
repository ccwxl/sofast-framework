package cc.sofast.framework.starter.common.dto;

import java.util.List;

/**
 * @author wxl
 */
public interface PageParamQuery {

    List<SortableField> getSortingFields();

    int getPageSize();

    int getPageIndex();

    boolean isNeedTotalCount();

}

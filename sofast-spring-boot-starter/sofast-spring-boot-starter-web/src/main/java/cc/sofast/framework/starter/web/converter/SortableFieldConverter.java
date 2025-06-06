package cc.sofast.framework.starter.web.converter;

import cc.sofast.framework.starter.common.dto.SortableField;
import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wxl
 */
public class SortableFieldConverter implements Converter<String, List<SortableField>> {

    @Override
    public List<SortableField> convert(String source) {
        List<SortableField> list = new ArrayList<>();
        if (!StringUtils.hasText(source)) {
            return list;
        }
        String[] parts = source.split(",");
        for (String part : parts) {
            String[] fieldAndOrder = part.split(":");
            if (fieldAndOrder.length == 2) {
                SortableField sf = new SortableField();
                sf.setField(fieldAndOrder[0].trim());
                sf.setOrder(fieldAndOrder[1].trim().toLowerCase());
                list.add(sf);
            }
        }
        return list;
    }
}

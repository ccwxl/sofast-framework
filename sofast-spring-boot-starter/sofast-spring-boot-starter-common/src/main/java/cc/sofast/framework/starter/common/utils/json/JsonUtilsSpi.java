package cc.sofast.framework.starter.common.utils.json;

import com.fasterxml.jackson.databind.json.JsonMapper;

/**
 * @author wxl
 */
public interface JsonUtilsSpi {

    void customer(JsonMapper.Builder builder);
}

package cc.sofast.biz.component.rbac;

import org.apache.ibatis.parsing.PropertyParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Properties;

/**
 * @author wxl
 */
public class TableNameTest {

    @Test
    public void testTableName() {
        String tableName = "${tb_prefix}sys_menu";
        Properties properties = new Properties();
        properties.setProperty("tb_prefix", "tb_");
        String parse = PropertyParser.parse(tableName, properties);
        Assertions.assertEquals("tb_sys_menu", parse);

        properties.setProperty("tb_prefix", "");
        parse = PropertyParser.parse(tableName, properties);
        Assertions.assertEquals("sys_menu", parse);

        Properties properties2 = new Properties();
        parse = PropertyParser.parse(tableName, properties2);
        Assertions.assertEquals("${tb_prefix}sys_menu", parse);
    }
}

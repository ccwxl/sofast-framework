package cc.sofast.framework.starter.common.utils;

import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import static cc.sofast.framework.starter.common.utils.LambdaExceptionUtil.wrapFunction;

/**
 * @author wxl
 */
class LambdaExceptionUtilTest {

    @Test
    void test() throws MalformedURLException {
        List<String> source = Arrays.asList("https://example1.com", "https://example2.com", "https://example3.com");

        // 只需要在原来的lambda表达式外用wrapFunction()方法包裹一下即可，注意异常已经被抛到了上层
        List<URL> urlList = source.stream()
                .map(wrapFunction(URL::new))
                .toList();

        // 还可以使用方法引入，代码更加简洁！
        List<URL> urlList1 = source.stream()
                .map(wrapFunction(URL::new))
                .toList();
    }

}
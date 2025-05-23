package cc.sofast.framework.starter.redis.cache;

import org.springframework.cache.annotation.CachingConfigurationSelector;

import org.springframework.cache.annotation.ProxyCachingConfiguration;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.AdviceModeImportSelector;
import org.springframework.context.annotation.AutoProxyRegistrar;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wxl
 */
public class SofastCachingConfigurationSelector extends AdviceModeImportSelector<SofastEnableCaching> {

    private static final String PROXY_JCACHE_CONFIGURATION_CLASS =
            "org.springframework.cache.jcache.config.ProxyJCacheConfiguration";

    private static final String CACHE_ASPECT_CONFIGURATION_CLASS_NAME =
            "org.springframework.cache.aspectj.AspectJCachingConfiguration";

    private static final String JCACHE_ASPECT_CONFIGURATION_CLASS_NAME =
            "org.springframework.cache.aspectj.AspectJJCacheConfiguration";


    private static final boolean jsr107Present;

    private static final boolean jcacheImplPresent;

    static {
        ClassLoader classLoader = CachingConfigurationSelector.class.getClassLoader();
        jsr107Present = ClassUtils.isPresent("javax.cache.Cache", classLoader);
        jcacheImplPresent = ClassUtils.isPresent(PROXY_JCACHE_CONFIGURATION_CLASS, classLoader);
    }


    /**
     * Returns {@link ProxyCachingConfiguration} or {@code AspectJCachingConfiguration}
     * for {@code PROXY} and {@code ASPECTJ} values of {@link SofastEnableCaching#mode()},
     * respectively. Potentially includes corresponding JCache configuration as well.
     */
    @Override
    public String[] selectImports(AdviceMode adviceMode) {
        return switch (adviceMode) {
            case PROXY -> getProxyImports();
            case ASPECTJ -> getAspectJImports();
        };
    }

    /**
     * Return the imports to use if the {@link AdviceMode} is set to {@link AdviceMode#PROXY}.
     * <p>Take care of adding the necessary JSR-107 import if it is available.
     */
    private String[] getProxyImports() {
        List<String> result = new ArrayList<>(3);
        result.add(AutoProxyRegistrar.class.getName());
        result.add(SofastProxyCachingConfiguration.class.getName());
        if (jsr107Present && jcacheImplPresent) {
            result.add(PROXY_JCACHE_CONFIGURATION_CLASS);
        }
        return StringUtils.toStringArray(result);
    }

    /**
     * Return the imports to use if the {@link AdviceMode} is set to {@link AdviceMode#ASPECTJ}.
     * <p>Take care of adding the necessary JSR-107 import if it is available.
     */
    private String[] getAspectJImports() {
        List<String> result = new ArrayList<>(2);
        result.add(CACHE_ASPECT_CONFIGURATION_CLASS_NAME);
        if (jsr107Present && jcacheImplPresent) {
            result.add(JCACHE_ASPECT_CONFIGURATION_CLASS_NAME);
        }
        return StringUtils.toStringArray(result);
    }

}

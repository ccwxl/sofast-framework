package cc.sofast.framework.starter.knife4j;

import io.swagger.v3.oas.models.OpenAPI;

/**
 * @author wxl
 */
@FunctionalInterface
public interface OpenAPICustomizer {

    /**
     * Customize the Swagger Docket.
     *
     * @param docket the Docket to customize
     */
    void customize(OpenAPI docket);
}

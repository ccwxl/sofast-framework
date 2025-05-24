package cc.sofast.framework.starter.web.exception;

import cc.sofast.framework.starter.common.dto.Result;
import cc.sofast.framework.starter.common.exception.ServiceException;
import cc.sofast.framework.starter.common.exception.code.ErrorCodeEnum;
import cc.sofast.framework.starter.common.utils.ServletUtils;
import cc.sofast.framework.starter.common.utils.web.WebUtils;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.List;

/**
 * @author wxl
 */
@RestControllerAdvice
@AllArgsConstructor
@Slf4j
public class GlobalCommonException {

    private static final ErrorCodeEnum BAD_REQUEST = ErrorCodeEnum.USER_REQUEST_PARAMETER_ERROR;
    private static final ErrorCodeEnum NOT_FOUND = ErrorCodeEnum.REQUEST_NOT_FOUND;
    private static final ErrorCodeEnum INTERNAL_SERVER_ERROR = ErrorCodeEnum.USER_REQUEST_SERVICE_EXCEPTION;
    private static final ErrorCodeEnum METHOD_NOT_ALLOWED = ErrorCodeEnum.REQUEST_METHOD_NOT_FOUND;
    private static final ErrorCodeEnum FORBIDDEN = ErrorCodeEnum.ACCESS_PERMISSION_EXCEPTION;


    /**
     * 处理所有异常，主要是提供给 Filter 使用
     * 因为 Filter 不走 SpringMVC 的流程，但是我们又需要兜底处理异常，所以这里提供一个全量的异常处理过程，保持逻辑统一。
     *
     * @param request 请求
     * @param ex      异常
     * @return 通用返回
     */
    public Result<?> allExceptionHandler(HttpServletRequest request, Throwable ex) {
        if (ex instanceof MissingServletRequestParameterException) {
            return missingServletRequestParameterExceptionHandler((MissingServletRequestParameterException) ex);
        }
        if (ex instanceof MethodArgumentTypeMismatchException) {
            return methodArgumentTypeMismatchExceptionHandler((MethodArgumentTypeMismatchException) ex);
        }
        if (ex instanceof MethodArgumentNotValidException) {
            return methodArgumentNotValidExceptionExceptionHandler((MethodArgumentNotValidException) ex);
        }
        if (ex instanceof BindException) {
            return bindExceptionHandler((BindException) ex);
        }
        if (ex instanceof ConstraintViolationException) {
            return constraintViolationExceptionHandler((ConstraintViolationException) ex);
        }

        if (ex instanceof NoHandlerFoundException) {
            return noHandlerFoundExceptionHandler((NoHandlerFoundException) ex);
        }
//        if (ex instanceof NoResourceFoundException) {
//            return noResourceFoundExceptionHandler(request, (NoResourceFoundException) ex);
//        }
        if (ex instanceof HttpRequestMethodNotSupportedException) {
            return httpRequestMethodNotSupportedExceptionHandler((HttpRequestMethodNotSupportedException) ex);
        }
        if (ex instanceof ServiceException) {
            return serviceExceptionHandler((ServiceException) ex);
        }
        if (ex instanceof AccessDeniedException) {
            return accessDeniedExceptionHandler(request, (AccessDeniedException) ex);
        }
        return defaultExceptionHandler(request, ex);
    }

    /**
     * 处理 SpringMVC 请求参数缺失
     * <p>
     * 例如说，接口上设置了 @RequestParam("xx") 参数，结果并未传递 xx 参数
     */
    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result<?> missingServletRequestParameterExceptionHandler(MissingServletRequestParameterException ex) {
        log.error("[missingServletRequestParameterExceptionHandler]", ex);
        return Result.error(ErrorCodeEnum.USER_REQUEST_PARAMETER_ERROR, String.format("请求参数缺失: %s", ex.getParameterName()));
    }

    /**
     * 处理 SpringMVC 请求参数类型错误
     * <p>
     * 例如说，接口上设置了 @RequestParam("xx") 参数为 Integer，结果传递 xx 参数类型为 String
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result<?> methodArgumentTypeMismatchExceptionHandler(MethodArgumentTypeMismatchException ex) {
        log.warn("[methodArgumentTypeMismatchExceptionHandler]", ex);
        return Result.error(ErrorCodeEnum.USER_REQUEST_PARAMETER_ERROR, String.format("请求参数类型错误: %s", ex.getMessage()));
    }


    /**
     * 处理 SpringMVC 参数校验不正确
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result<?> methodArgumentNotValidExceptionExceptionHandler(MethodArgumentNotValidException ex) {
        log.warn("[methodArgumentNotValidExceptionExceptionHandler]", ex);
        // 获取 errorMessage
        String errorMessage = null;
        FieldError fieldError = ex.getBindingResult().getFieldError();
        if (fieldError == null) {
            List<ObjectError> allErrors = ex.getBindingResult().getAllErrors();
            if (CollUtil.isNotEmpty(allErrors)) {
                errorMessage = allErrors.getFirst().getDefaultMessage();
            }
        } else {
            errorMessage = fieldError.getDefaultMessage();
        }
        // 转换 CommonResult
        if (StrUtil.isEmpty(errorMessage)) {
            return Result.error(BAD_REQUEST);
        }
        return Result.error(BAD_REQUEST, String.format("请求参数不正确:%s", errorMessage));
    }

    /**
     * 处理 SpringMVC 参数绑定不正确，本质上也是通过 Validator 校验
     */
    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result<?> bindExceptionHandler(BindException ex) {
        log.warn("[handleBindException]", ex);
        FieldError fieldError = ex.getFieldError();
        // 断言，避免告警
        assert fieldError != null;
        return Result.error(BAD_REQUEST, String.format("请求参数不正确:%s", fieldError.getDefaultMessage()));
    }


    /**
     * 处理 SpringMVC 请求参数类型错误
     * <p>
     * 例如说，接口上设置了 @RequestBody实体中 xx 属性类型为 Integer，结果传递 xx 参数类型为 String
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Result<?> methodArgumentTypeInvalidFormatExceptionHandler(HttpMessageNotReadableException ex) {
        log.warn("[methodArgumentTypeInvalidFormatExceptionHandler]", ex);
        if (ex.getCause() instanceof InvalidFormatException) {
            InvalidFormatException invalidFormatException = (InvalidFormatException) ex.getCause();
            return Result.error(BAD_REQUEST, String.format("请求参数类型错误:%s", invalidFormatException.getValue()));
        } else {
            return defaultExceptionHandler(ServletUtils.getRequest(), ex);
        }
    }

    /**
     * 处理 Validator 校验不通过产生的异常
     */
    @ExceptionHandler(value = ConstraintViolationException.class)
    public Result<?> constraintViolationExceptionHandler(ConstraintViolationException ex) {
        log.warn("[constraintViolationExceptionHandler]", ex);
        ConstraintViolation<?> constraintViolation = ex.getConstraintViolations().iterator().next();
        return Result.error(BAD_REQUEST, String.format("请求参数不正确:%s", constraintViolation.getMessage()));
    }


    /**
     * 处理 SpringMVC 请求地址不存在
     * <p>
     * 注意，它需要设置如下两个配置项：
     * 1. spring.mvc.throw-exception-if-no-handler-found 为 true
     * 2. spring.mvc.static-path-pattern 为 /statics/**
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public Result<?> noHandlerFoundExceptionHandler(NoHandlerFoundException ex) {
        log.warn("[noHandlerFoundExceptionHandler]", ex);
        return Result.error(NOT_FOUND, String.format("请求地址不存在:%s", ex.getRequestURL()));
    }

    /**
     * 处理 SpringMVC 请求方法不正确
     * <p>
     * 例如说，A 接口的方法为 GET 方式，结果请求方法为 POST 方式，导致不匹配
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Result<?> httpRequestMethodNotSupportedExceptionHandler(HttpRequestMethodNotSupportedException ex) {
        log.warn("[httpRequestMethodNotSupportedExceptionHandler]", ex);
        return Result.error(METHOD_NOT_ALLOWED, String.format("请求方法不正确:%s", ex.getMessage()));
    }

    /**
     * 处理 Spring Security 权限不足的异常
     * <p>
     * 来源是，使用 @PreAuthorize 注解，AOP 进行权限拦截
     */
    @ExceptionHandler(value = AccessDeniedException.class)
    public Result<?> accessDeniedExceptionHandler(HttpServletRequest req, AccessDeniedException ex) {
        log.warn("[accessDeniedExceptionHandler][userId({}) 无法访问 url({})]", WebUtils.getLoginUserId(req),
                req.getRequestURL(), ex);
        return Result.error(FORBIDDEN);
    }


    /**
     * 处理业务异常 ServiceException
     * <p>
     * 例如说，商品库存不足，用户手机号已存在。
     */
    @ExceptionHandler(value = ServiceException.class)
    public Result<?> serviceExceptionHandler(ServiceException ex) {
        log.warn("[serviceExceptionHandler]", ex);
        return Result.fail(ex.getMessage());
    }


    /**
     * 处理系统异常，兜底处理所有的一切
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result<?> defaultExceptionHandler(HttpServletRequest req, Throwable ex) {
        log.error("[defaultExceptionHandler]", ex);
        // 返回 ERROR CommonResult
        return Result.error(INTERNAL_SERVER_ERROR);
    }
}

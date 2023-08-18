package com.supcon.orchid.SESECD.config;

import com.supcon.greendill.common.exception.LicenseException;
import com.supcon.orchid.foundation.config.ServerConfig;
import com.supcon.orchid.foundation.exception.log.entity.ErrorLogEntity;
import com.supcon.orchid.foundation.exception.log.publisher.LogPublisher;
import com.supcon.orchid.foundation.exception.log.support.LogBuilder;
import com.supcon.orchid.foundation.exceptions.ObjectNotFoundException;
import com.supcon.orchid.foundation.utils.StringUtil;
import com.supcon.orchid.foundation.utils.UrlUtil;
import com.supcon.orchid.foundation.utils.WebUtil;
import com.supcon.orchid.i18n.InternationalResource;
import com.supcon.orchid.services.BAPException;
import com.supcon.orchid.services.FileUploadException;
import com.supcon.orchid.support.Result;
import com.supcon.orchid.support.ResultCode;
import com.supcon.orchid.utils.StringUtils;
import com.supcon.supfusion.framework.cloud.common.context.RpcContext;
import org.hibernate.StaleObjectStateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.PriorityOrdered;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.orm.hibernate5.HibernateOptimisticLockingFailureException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.persistence.OptimisticLockException;
import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

/**
 * 此处定义全局异常是为了支持国际化，可在参数校验中使用注解校验，无需单独代码校验
 */
@Configuration
@RestControllerAdvice
public class InternationalBAPRestExceptionHandler implements PriorityOrdered {
    @Autowired
    private ServerConfig serverConfig;

    @Override
    public int getOrder() {
        return 99;
    }

    private final Logger log = LoggerFactory.getLogger(InternationalBAPRestExceptionHandler.class);

    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result handleError(MethodArgumentNotValidException e) {
        this.log.warn("参数验证失败", e.getMessage());
        return this.handleError(e.getBindingResult());
    }

    private Result handleError(BindingResult result) {
        FieldError error = result.getFieldError();
        String defaultMessage = error.getDefaultMessage();
        //拿到国际化值
        String format = InternationalResource.get(defaultMessage, getCurrentLanguage());
        String msg = error.getField() + ": " + format;
        return Result.fail(ResultCode.PARAM_BIND_ERROR, msg);
    }

    private String getCurrentLanguage() {
        Locale locale = RpcContext.getContext().getLanguage();
        return null == locale ? "zh_CN" : locale.toString();
    }


    @ExceptionHandler({MissingServletRequestParameterException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result handleError(MissingServletRequestParameterException e) {
        this.log.warn("缺少请求参数", e.getMessage());
        String message = String.format("缺少必要的请求参数: %s", e.getParameterName());
        return Result.fail(ResultCode.PARAM_MISS, message);
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result handleError(MethodArgumentTypeMismatchException e) {
        this.log.warn("请求参数格式错误", e.getMessage());
        String message = String.format("请求参数格式错误: %s", e.getName());
        return Result.fail(ResultCode.PARAM_TYPE_ERROR, message);
    }


    @ExceptionHandler({BindException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result handleError(BindException e) {
        this.log.warn("参数绑定失败", e.getMessage());
        return this.handleError(e.getBindingResult());
    }


    @ExceptionHandler({NoHandlerFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Result handleError(NoHandlerFoundException e) {
        String httpMethod = e.getHttpMethod();
        String requestURL = e.getRequestURL();
        this.log.error("404没找到请求: httpMethod: " + httpMethod + "requestURL:" + requestURL, e);
        return Result.fail(ResultCode.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler({HttpMessageNotReadableException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result handleError(HttpMessageNotReadableException e) {
        this.log.error("消息不能读取:{}", e.getMessage());
        return Result.fail(ResultCode.MSG_NOT_READABLE, e.getMessage());
    }

    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public Result handleError(HttpRequestMethodNotSupportedException e) {
        this.log.error("不支持当前请求方法:{}", e.getMessage());
        return Result.fail(ResultCode.METHOD_NOT_SUPPORTED, e.getMessage());
    }

    @ExceptionHandler({HttpMediaTypeNotSupportedException.class})
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    public Result handleError(HttpMediaTypeNotSupportedException e) {
        this.log.error("不支持当前媒体类型:{}", e.getMessage());
        return Result.fail(ResultCode.MEDIA_TYPE_NOT_SUPPORTED, e.getMessage());
    }

    @ExceptionHandler({HibernateOptimisticLockingFailureException.class, OptimisticLockException.class, StaleObjectStateException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result handleError(Exception e) {
        this.log.error("本数据已经被其他人修改或删除，请刷新页面后重试。", e.getMessage());
        return Result.fail(ResultCode.UPDATE_BY_OTHER, InternationalResource.get("foundation.exceptions.row.update.by.other", this.getCurrentLanguage()));
    }

    @ExceptionHandler({BAPException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result handleError(BAPException e) {
        this.log.error("业务异常", e);
        ResultCode resultCode = ResultCode.INTERNAL_SERVER_ERROR;
        String message = e.getMessage();
        BAPException.Code code = e.getCode();
        if (code != null) {
            message = InternationalResource.get(code.toMessageKey(), this.getCurrentLanguage(), e.getArgs().toArray());
            if (StringUtils.isEmpty(message)) {
                message = code.toMessageKey();
            }

            if (BAPException.Code.VISITS_EXCEEDED.equals(code)) {
                resultCode = ResultCode.VISITS_EXCEEDED;
            } else if (BAPException.Code.UN_AUTHORIZED.equals(code)) {
                resultCode = ResultCode.UN_AUTHORIZED;
            }
        } else if (!StringUtils.isEmpty(e.getMessageKey())) {
            message = InternationalResource.get(e.getMessageKey(), this.getCurrentLanguage(), e.getArgs().toArray());
        }

        return Result.fail(resultCode, message);
    }

    @ExceptionHandler({FileUploadException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result handleError(FileUploadException e) {
        this.log.error("附件上传异常", e);
        String message = e.getMessage();
        if (!StringUtils.isEmpty(e.getMessageKey())) {
            message = InternationalResource.get(e.getMessageKey(), this.getCurrentLanguage(), e.getArgs().toArray());
        }

        return Result.fail(ResultCode.INTERNAL_SERVER_ERROR, message);
    }

    @ExceptionHandler({ObjectNotFoundException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result handleError(ObjectNotFoundException e) {
        this.log.error("接口异常", e);
        String message = e.getMessage();
        return Result.fail(ResultCode.INTERNAL_SERVER_ERROR, message);
    }

    @ExceptionHandler({Throwable.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result handleError(Throwable e) {
        this.log.error("服务器异常", e);
        ErrorLogEntity errorLogEntity = new ErrorLogEntity();
        LogBuilder.buildErrorLog(e, UrlUtil.getPath(WebUtil.getRequest().getRequestURI()), this.serverConfig, errorLogEntity);
        LogPublisher.publishErrorLogEvent(errorLogEntity);
        int status = this.getStatus(WebUtil.getRequest());
        return 503 == status ? Result.fail(ResultCode.SC_SERVICE_UNAVAILABLE, StringUtil.isEmpty(e.getMessage()) ? ResultCode.SC_SERVICE_UNAVAILABLE.getMessage() : e.getMessage()) : Result.fail(ResultCode.INTERNAL_SERVER_ERROR, StringUtil.isEmpty(e.getMessage()) ? ResultCode.INTERNAL_SERVER_ERROR.getMessage() : e.getMessage());
    }

    @ExceptionHandler({LicenseException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Result handleError(LicenseException e) {
        this.log.error("请求未授权", e);
        String message = e.getMessage();
        return Result.fail(ResultCode.UN_AUTHORIZED, message);
    }

    @ExceptionHandler({AccessDeniedException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public Result handleError(AccessDeniedException e) {
        this.log.error("请求无权限", e);
        String message = e.getMessage();
        return Result.fail(ResultCode.SC_FORBIDDEN, message);
    }

    private int getStatus(HttpServletRequest request) {
        Integer status = (Integer) request.getAttribute("javax.servlet.error.status_code");
        return status != null ? status : 500;
    }
}

package com.wang.perpro.config;

import com.wang.perpro.base.data.ApiResponse;
import com.wang.perpro.base.enums.BaseErrorEnum;
import com.wang.perpro.base.exceptions.ApiException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author wangxiaoliang
 * @date 2018-09-14 下午6:26
 * @description:
 * 系统全局异常处理
 */
@ControllerAdvice
@ResponseBody
public class ExcetionHandler {

  /**
   * 自定义异常处理
   * @param e
   * @return
   */
  @ExceptionHandler({ApiException.class})
  public ApiResponse handleApiException(ApiException e) {


    return ApiResponse.error(e);
  }

  /**
   * 默认系统异常处理
   * @param e
   * @return
   */
  @ExceptionHandler(Exception.class)
  public ApiResponse handleException(Exception e) {

    return ApiResponse.error(BaseErrorEnum.BASE_SYSTEM_ERROR.getErrorCode(),
        BaseErrorEnum.BASE_SYSTEM_ERROR.getErrorMsg());
  }
}

package com.wang.perpro.base.exceptions;

/**
 * @author wangxiaoliang
 * @date 2018-09-17 下午3:59
 * @description:
 */
public class ApiException extends RuntimeException{
  /**
   * 错误码.
   */
  private final ErrorCode errorCode;


  /**
   *
   * @param errorCode
   */
  public ApiException(ErrorCode errorCode) {
    super(errorCode.getErrorMsg());
    this.errorCode = errorCode;
  }

  /**
   *
   * @param errorCode
   */
  public ApiException(ErrorCode errorCode, Object... params) {
    super(errorCode.getErrorMsg(params));
    this.errorCode = errorCode;
  }

  /**
   *
   * @param errorCode
   */
  public ApiException(ErrorCode errorCode, Throwable t) {
    super(t);
    this.errorCode = errorCode;
  }

  public ApiException(ErrorCode errorCode, Throwable t, Object... params) {
    super(errorCode.getErrorMsg(params), t);
    this.errorCode = errorCode;
  }

  public String getErrorCode() {
    return errorCode.getErrorCode();
  }
}

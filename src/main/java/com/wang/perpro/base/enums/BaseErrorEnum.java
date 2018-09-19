package com.wang.perpro.base.enums;

import com.google.common.base.CaseFormat;
import com.wang.perpro.base.exceptions.ErrorCode;

/**
 * @author wangxiaoliang
 * @date 2018-09-17 下午4:00
 * @description:
 */
public enum  BaseErrorEnum implements ErrorCode {
  BASE_SYSTEM_ERROR,
  BASE_ERROR,
  BASE_EXCEPTION,

  ;

  public final String errCode;

  BaseErrorEnum() {
    this.errCode = CaseFormat.UPPER_UNDERSCORE.converterTo(CaseFormat.LOWER_HYPHEN).convert(name());
  }

  @Override
  public String getErrorCode() {
    return errCode;
  }
}

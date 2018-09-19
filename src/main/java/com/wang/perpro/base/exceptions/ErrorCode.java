package com.wang.perpro.base.exceptions;

import com.wang.perpro.config.LocalMessageConfig;

/**
 * @author wangxiaoliang
 * @date 2018-09-17 下午3:54
 * @description:
 */
public interface ErrorCode {
  String getErrorCode();

  default String getErrorMsg() {
    return LocalMessageConfig.getMessage(getErrorCode().replace("-", "."));
  }

  default String getErrorMsg(Object... args) {
    return LocalMessageConfig.getMessage(getErrorCode().replace("-", "."), args);
  }
}

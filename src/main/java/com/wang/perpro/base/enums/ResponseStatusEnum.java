package com.wang.perpro.base.enums;

import lombok.Getter;

/**
 * @author wangxiaoliang
 * @date 2018-09-17 下午4:19
 * @description:
 */
@Getter
public enum ResponseStatusEnum {

  OK("ok"), ERROR("error");

  private String value;

  ResponseStatusEnum(String v) {
    this.value = v;
  }

  public String getValue() {
    return value;
  }

}


package com.wang.perpro.base.data;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.reflect.TypeParameter;
import com.google.common.reflect.TypeToken;
import com.wang.perpro.base.enums.ResponseStatusEnum;
import com.wang.perpro.base.exceptions.ApiException;
import com.wang.perpro.base.exceptions.ErrorCode;
import java.lang.reflect.Type;
import java.util.List;
import lombok.Data;

/**
 * @author wangxiaoliang
 * @date 2018-09-17 下午4:09
 * @description:
 */
@Data
public class ApiResponse<T> {

  private String status;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private String code;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private String message;

  private T data;

  public static <K> ApiResponse<K> ok(K data) {
    ApiResponse<K> response = new ApiResponse<>();
    response.status = ResponseStatusEnum.OK.getValue();
    response.code = ResponseStatusEnum.OK.getValue();
    response.message = ResponseStatusEnum.OK.getValue();
    response.data = (data);
    return response;
  }

  public static <K> ApiResponse<K> error(ErrorCode errorCode) {
    return error(errorCode.getErrorCode(), errorCode.getErrorMsg());
  }

  public static <K> ApiResponse<K> error(ErrorCode errorCode, Object... params) {
    return error(errorCode.getErrorCode(), errorCode.getErrorMsg(params));
  }

  public static <K> ApiResponse<K> error(ApiException e) {
    return error(e.getErrorCode(), e.getMessage());
  }

  public static <K> ApiResponse<K> error(ApiException e, K data) {
    ApiResponse<K> response = new ApiResponse<>();
    response.status = ResponseStatusEnum.ERROR.getValue();
    response.code = e.getErrorCode();
    response.message = e.getMessage();
    response.data = data;
    return response;
  }

  public static <K> ApiResponse<K> error(String errCode, String errMsg) {
    ApiResponse<K> response = new ApiResponse<>();
    response.status = ResponseStatusEnum.ERROR.getValue();
    response.code = errCode;
    response.message = errMsg;
    return response;
  }

  public static <T> TypeReference<ApiResponse<List<T>>> list(Class<T> dataType) {
    return new TypeReference<ApiResponse<List<T>>>() {
      @Override
      public Type getType() {
        TypeToken<?> typeToken = TypeToken.of(super.getType());
        return typeToken.where(new TypeParameter<T>() {
        }, dataType).getType();
      }
    };
  }

  public static <T> TypeReference<ApiResponse<T>> type(Class<T> dataType) {
    return new TypeReference<ApiResponse<T>>() {
      @Override
      public Type getType() {
        TypeToken<?> typeToken = TypeToken.of(super.getType());
        return typeToken.where(new TypeParameter<T>() {
        }, dataType).getType();
      }
    };
  }

  //@Override
  //public String toString() {
    //return JsonUtil.writeValue(this);
  //}
}

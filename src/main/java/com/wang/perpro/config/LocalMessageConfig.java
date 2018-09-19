package com.wang.perpro.config;

import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

/**
 * @author wangxiaoliang
 * @date 2018-09-17 下午3:37
 * @description:
 */
public class LocalMessageConfig {
  private static ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
  static {
    messageSource.setFallbackToSystemLocale(false);
    messageSource.setBasenames(
        "classpath:/i18n/message_perpro"
    );
    messageSource.setDefaultEncoding("UTF-8");
  }

  private static final String NOT_TRANSLATED = " (NT)";

  public static String getMessage(String code) {
    try {
      return messageSource.getMessage(code, null, LocaleContextHolder.getLocale());
    } catch (NoSuchMessageException e) {
      return code + NOT_TRANSLATED;
    }
  }

  public static String getMessage(String code, Object... args) {
    try {
      return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
    } catch (NoSuchMessageException e) {
      return code + NOT_TRANSLATED;
    }
  }
}

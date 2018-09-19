package com.wang.perpro.base.filter;

import com.wang.perpro.base.util.IpUtil;
import com.wang.perpro.service.cache.CacheValueManager;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author wangxiaoliang
 * @date 2018-09-17 下午4:44
 * @description:
 */
@Component
public class UserContextFilter implements Filter {

  @Autowired
  private CacheValueManager cacheValueManager;

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {

  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    HttpServletRequest req = (HttpServletRequest) request;
    String ip = IpUtil.getIpAddressForHttpTrueClientIp(req);
    cacheValueManager.set("IP",ip);
    chain.doFilter(request,response);
  }

  @Override
  public void destroy() {

  }
}

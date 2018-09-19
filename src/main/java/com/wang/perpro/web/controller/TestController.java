package com.wang.perpro.web.controller;

import com.wang.perpro.base.data.ApiResponse;
import com.wang.perpro.service.data.DmData;
import com.wang.perpro.service.service.DmDataService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wangxiaoliang
 * @date 2018-09-10 上午10:30
 * @description:
 */
@RestController
@RequestMapping("/v1/test")
public class TestController {
  @Autowired
  private DmDataService service;
  @GetMapping("/test")
  public List<DmData> test(){
    return service.getAll();
  }
  @GetMapping("/test-by-id")
  public ApiResponse<DmData> getById(Integer id){
    DmData dmData=service.getById(id);
    return  ApiResponse.ok(dmData);
  }
}

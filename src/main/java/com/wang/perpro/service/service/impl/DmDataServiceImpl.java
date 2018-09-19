package com.wang.perpro.service.service.impl;


import com.wang.perpro.base.enums.BaseErrorEnum;
import com.wang.perpro.base.exceptions.ApiException;
import com.wang.perpro.service.data.DmData;
import com.wang.perpro.service.mapper.DmDataMapper;
import com.wang.perpro.service.service.DmDataService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wangxiaoliang
 * @date 2018-09-09 上午10:55
 * @description:
 */
@Slf4j
@Service
public class DmDataServiceImpl implements DmDataService {
  @Autowired
  private DmDataMapper dataMapper;
  @Override
  public List<DmData> getAll() {
    return dataMapper.selectAll();
  }

  @Override
  public DmData getById(Integer id) {
    if(id==null){
      throw new ApiException(BaseErrorEnum.BASE_ERROR);
    }
    return dataMapper.selectByPrimaryKey(id);
  }
}

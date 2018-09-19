package com.wang.perpro.service.service;

import com.wang.perpro.service.data.DmData;
import java.util.List;

/**
 * @author wangxiaoliang
 * @date 2018-09-09 上午10:54
 * @description:
 */
public interface DmDataService {
  List<DmData> getAll();
  DmData getById(Integer id);
}

package com.wang.perpro.service.mapper;


import com.wang.perpro.service.data.DmData;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DmDataMapper {

  int insert(DmData record);

  int insertSelective(DmData record);

  DmData selectByPrimaryKey(Integer did);

  List<DmData> selectAll();

  int updateByPrimaryKeySelective(DmData record);

  int updateByPrimaryKey(DmData record);
}
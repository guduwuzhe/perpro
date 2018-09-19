package com.wang.perpro.service.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import lombok.Data;
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel
@Data
public class DmData {
    private Integer did;

    private String groupid;

    private String drugid;

    private String classifyone;

    private String classifytwo;

    private String drugcname;

    private String drugename;

    private Date creatdate;

    private Date modifydate;

    private String describes;

    private String stock;

    private String stockunit;

    private String comments;
    @ApiModelProperty(value = "超管账号", required = true)
    private String catNo;

    private String location;
}
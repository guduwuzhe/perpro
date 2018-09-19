package com.wang.perpro.service.cache;

/**
 * <p> </p>
 *
 * @Author jinxue 2017年05月19 16时01分
 * @ProjectName:gallery
 * @Version V1.0
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: jinxue
 * @modify by reason:{方法名}:{原因}
 */
public interface IRedisValueCallBack<T> {

    T doInRedis(Object... key);

}

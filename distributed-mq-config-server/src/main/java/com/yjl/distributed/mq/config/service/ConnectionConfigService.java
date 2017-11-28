package com.yjl.distributed.mq.config.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.yjl.distributed.mq.config.bean.entity.ConnectionConfigEntity;

/**
 * <p>
 * 连接配置表 服务类
 * </p>
 *
 * @author zhaoyc@1109
 * @since 2017-11-16
 */
public interface ConnectionConfigService extends IService<ConnectionConfigEntity> {

	/**
	 * 获取所有配置
	 * 
	 * @return
	 */
	List<ConnectionConfigEntity> list();



}

package com.yjl.distributed.mq.config.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yjl.distributed.mq.config.bean.entity.ConnectionConfigEntity;
import com.yjl.distributed.mq.config.bean.entity.enums.StatusEnum;
import com.yjl.distributed.mq.config.dal.mapper.ConnectionConfigMapper;
import com.yjl.distributed.mq.config.service.ConnectionConfigService;


/**
 * <p>
 * 连接配置表 服务实现类
 * </p>
 *
 * @author zhaoyc@1109
 * @since 2017-11-16
 */
@Service
public class ConnectionConfigServiceImpl extends ServiceImpl<ConnectionConfigMapper, ConnectionConfigEntity>
		implements ConnectionConfigService {


	@Override
	public List<ConnectionConfigEntity> list() {

		Wrapper<ConnectionConfigEntity> wrapper = new EntityWrapper<>();
		wrapper.where(true, "status = {0}", StatusEnum.ENABLE.getCode());

		return super.selectList(wrapper);
	}


}

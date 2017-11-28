
-- ----------------------------
-- Table structure for connection_config
-- ----------------------------
DROP TABLE IF EXISTS `connection_config`;
CREATE TABLE `connection_config` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `broker_key` varchar(16) NOT NULL DEFAULT '' COMMENT 'broker集群唯一标志',
  `broker_url` varchar(256) NOT NULL DEFAULT '' COMMENT '集群地址',
  `username` varchar(32) NOT NULL DEFAULT '' COMMENT '登录用户名',
  `password` varchar(128) NOT NULL DEFAULT '' COMMENT '登录密码',
  `max_connections` int(8) unsigned NOT NULL DEFAULT '5' COMMENT '最大连接数',
  `maximum_active_session` int(8) unsigned NOT NULL DEFAULT '100' COMMENT '每个连接中使用的最大活动会话数',
  `max_thread_pool_size` int(8) unsigned NOT NULL DEFAULT '5' COMMENT '连接池中线程池大小',
  `status` varchar(10) NOT NULL DEFAULT 'ENABLE' COMMENT '状态:ENABLE-可用，DISABLE-不可用，DELETE-删除',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_broker` (`broker_key`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='连接配置表';

-- ----------------------------
-- Records of connection_config
-- ----------------------------
INSERT INTO `connection_config` VALUES ('1', '192.168.117.13', 'failover://(tcp://192.168.117.13:61616,tcp://192.168.117.13:61616)?initialReconnectDelay=1000&timeout=3000&startupMaxReconnectAttempts=2&randomize=false', '', '', '5', '100', '5', 'ENABLE', '2017-11-16 09:43:52', '2017-11-17 01:43:20');
INSERT INTO `connection_config` VALUES ('2', '192.168.117.14', 'failover://(tcp://192.168.117.14:61616,tcp://192.168.117.14:61616)?initialReconnectDelay=1000&timeout=3000&startupMaxReconnectAttempts=2&randomize=false', '', '', '5', '100', '5', 'ENABLE', '2017-11-16 09:44:18', '2017-11-17 01:43:16');

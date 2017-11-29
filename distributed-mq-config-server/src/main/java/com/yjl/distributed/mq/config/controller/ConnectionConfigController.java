package com.yjl.distributed.mq.config.controller;

import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.yjl.distributed.mq.config.bean.entity.ConnectionConfigEntity;
import com.yjl.distributed.mq.config.common.ResponseEntity;
import com.yjl.distributed.mq.config.service.ConnectionConfigService;


/**
 * <p>
 * 连接配置表 前端控制器
 * </p>
 *
 * @author zhaoyc@1109
 * @since 2017-11-16
 */
@RestController
@RequestMapping("connection-configs")
public class ConnectionConfigController {
    @Autowired
    private ConnectionConfigService connectionConfigService;

    /*
     * 
     * id Long
     * 
     * 集群地址 brokerUrl String
     * 
     * 登录用户名 username String
     * 
     * 登录密码 password String
     * 
     * 最大连接数 maxConnections Integer
     * 
     * 每个连接中使用的最大活动会话数 maximumActiveSession Integer
     * 
     * 连接池中线程池大小 maxThreadPoolSize Integer
     * 
     * 创建时间 createTime Date
     * 
     * 修改时间 updateTime Date
     * 
     */


    @GetMapping("get/{id}")
    public ResponseEntity<ConnectionConfigEntity> get(@PathVariable Long id) {
        return new ResponseEntity<ConnectionConfigEntity>().ok()
                .setResponseContent(connectionConfigService.selectById(id));
    }

    @GetMapping("/list")
    public ResponseEntity<List<ConnectionConfigEntity>> list() {
        return new ResponseEntity<List<ConnectionConfigEntity>>().ok()
                .setResponseContent(connectionConfigService.list());
    }

    @PostMapping("save")
    public ResponseEntity<Object> save(ConnectionConfigEntity connectionConfig) {
        try {
            if (!connectionConfigService.insert(connectionConfig)) {
                return new ResponseEntity<Object>().badRequest("保存失败");
            }
            return new ResponseEntity<Object>().ok("保存成功");
        } catch (Exception e) {
            LogManager.getLogger().error(e);
            return new ResponseEntity<Object>().badRequest("保存失败");
        }
    }

    @PostMapping("update/{id}")
    public ResponseEntity<Object> update(@PathVariable Long id,
            ConnectionConfigEntity connectionConfig) {
        try {
            if (!connectionConfigService.updateById(connectionConfig.setId(id))) {
                return new ResponseEntity<Object>().badRequest("更新失败");
            }
            return new ResponseEntity<Object>().ok("更新成功");
        } catch (Exception e) {
            LogManager.getLogger().error(e);
            return new ResponseEntity<Object>().badRequest("更新失败");
        }
    }

    @PostMapping("del/{id}")
    public ResponseEntity<Object> del(@PathVariable Long id) {
        try {
            if (!connectionConfigService.deleteById(id)) {
                return new ResponseEntity<Object>().badRequest("删除失败");
            }
            return new ResponseEntity<Object>().ok("删除成功");
        } catch (Exception e) {
            LogManager.getLogger().error(e);
            return new ResponseEntity<Object>().badRequest("删除失败");
        }
    }



}

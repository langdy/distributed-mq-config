package com.yjl.distributed.mq.config.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.baomidou.mybatisplus.plugins.PerformanceInterceptor;

/**
 * @author : zhaoyc
 * @date : 2017/5/10
 */
@Configuration
@MapperScan({"com.yjl.distributed.mq.config.dal.mapper"})
public class MybatisPlusConfig {

    /**
     * mybatis-plus 性能分析拦截器<br>
     * 文档：http://mp.baomidou.com<br>
     */
    @Bean
    @ConditionalOnExpression("${mybatisPlus.performanceInterceptorEnabled:false}")
    public PerformanceInterceptor performanceInterceptor() {
        return new PerformanceInterceptor();
    }

}

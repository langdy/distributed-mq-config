package com.yjl.distributed.mq.config.config;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.boot.web.servlet.ErrorPageRegistrar;
import org.springframework.boot.web.servlet.ErrorPageRegistry;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import com.yjl.distributed.mq.config.common.converter.StringToDateConverter;
import com.yjl.distributed.mq.config.common.filter.CorsHeaderFilter;
import com.yjl.distributed.mq.config.common.filter.RequestLoggingFilter;
import com.yjl.distributed.mq.config.common.interceptor.InjectionAttackInterceptor;
import com.yjl.distributed.mq.config.common.util.JsonUtils;

/**
 * @author : zhaoyc
 * @date : 2017/5/10
 */
@Configuration
public class SpringConfig extends WebMvcConfigurerAdapter implements ErrorPageRegistrar {


    @Autowired(required = false)
    private InjectionAttackInterceptor injectionAttackInterceptor;


    @Bean
    public RequestLoggingFilter requestLoggingFilter() {
        return new RequestLoggingFilter();
    }

    @Bean
    public CorsHeaderFilter corsHeaderFilter() {
        return new CorsHeaderFilter();
    }

    /**
     * <pre>
     *      distributed:
     *        filter:
     *          injection-attack-interceptor:
     *            enabled: true
     *
     * </pre>
     */
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    @ConditionalOnProperty(prefix = "distributed.filter.injection-attack-interceptor",
            name = "enabled", havingValue = "true")
    public InjectionAttackInterceptor injectionAttackInterceptor() {
        return new InjectionAttackInterceptor();
    }


    /**
     * 添加过滤器
     *
     * @return
     */
    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        // 过滤器注册
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
        encodingFilter.setEncoding(StandardCharsets.UTF_8.displayName());
        encodingFilter.setForceEncoding(true);
        // 字符过滤器
        registrationBean.setFilter(encodingFilter);
        // 日志处理过滤器
        registrationBean.setFilter(requestLoggingFilter());
        // 跨域处理，及解决跨域情况下设置header失败的问题
        registrationBean.setFilter(corsHeaderFilter());
        return registrationBean;
    }

    /**
     * 添加转换器
     *
     * @param registry
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        // 从前台过来的数据转换成对应类型的转换器
        registry.addConverter(new StringToDateConverter());
    }

    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
        return new MappingJackson2HttpMessageConverter(JsonUtils.getCustomizationMapper());
    }

    /**
     * 消息转换
     *
     * @param converters
     */
    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        // 默认转换器注册后, 插入自定义的请求响应转换器
        converters.add(new StringHttpMessageConverter(StandardCharsets.UTF_8));
        converters.add(this.mappingJackson2HttpMessageConverter());

    }

    @Override
    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> returnValueHandlers) {}

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        if (Objects.nonNull(injectionAttackInterceptor)) {
            registry.addInterceptor(injectionAttackInterceptor).addPathPatterns("/**");
        }
    }


    @Override
    public void registerErrorPages(ErrorPageRegistry registry) {
        registry.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/404.html"));
        registry.addErrorPages(new ErrorPage(HttpStatus.UNAUTHORIZED, "/401.html"));
        registry.addErrorPages(new ErrorPage(Throwable.class, "/500.html"));
    }
}

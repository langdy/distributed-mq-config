package com.yjl.distributed.mq.config.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.core.annotation.AliasFor;

/**
 * 不需要防注入攻击拦截注解
 * <p>
 * 控制器中加入该注解,这个Mapping则不会进行注入拦截处理
 * <p>
 * 
 * <pre>
 *     // 表示该请求,不会进行注入攻击拦截处理
 *     <code>@</code>RequestMapping( "demo" )
 *     <code>@</code>PassInjectionAttackIntercept
 *     public ResponseEntity< String > demo () {
 *          return ResponseEntity.ok();
 *     }
 *
 *     // 表示该请求,进行注入攻击拦截处理时,如果请求参数中包含了 "update" 或者 "exec",那么对此进行忽略,排除这些关键字符
 *     <code>@</code>RequestMapping( "demo" )
 *     <code>@</code>PassInjectionAttackIntercept( { "update" , "exec" } )
 *     public ResponseEntity< String > demo () {
 *          return ResponseEntity.ok();
 *     }
 *     
 *     // 注解在控制器方法上同理
 *
 * </pre>
 *
 * @author : zhaoyc
 * @date : 2017/8/28
 * @see com.yjl.distributed.mq.config.common.interceptor.InjectionAttackInterceptor
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface PassInjectionAttackIntercept {

    /**
     * 忽略的字符
     */
    @AliasFor("ignoreStrings")
    String[] value() default {};

    @AliasFor("value")
    String[] ignoreStrings() default {};


}

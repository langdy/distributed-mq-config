/**
 * Copyright (c) 2011 Andrew C Slocum
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 **/
package com.yjl.distributed.mq.config.common.interceptor;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.LogManager;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import com.yjl.distributed.mq.config.common.annotation.PassInjectionAttackIntercept;
import com.yjl.distributed.mq.config.common.interceptor.handler.DefaultInjectionAttackHandler;
import com.yjl.distributed.mq.config.common.interceptor.handler.InjectionAttackHandler;
import com.yjl.distributed.mq.config.common.util.RequestUtils;
import lombok.Getter;
import lombok.Setter;

/**
 * @author : zhaoyc
 * @date : 2017/8/29
 * @see PassInjectionAttackIntercept
 */
@Getter
@Setter
public class InjectionAttackInterceptor extends HandlerInterceptorAdapter {


    private InjectionAttackHandler injectionAttackHandler =
            DefaultInjectionAttackHandler.getInstance();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
            Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return false;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;

        final PassInjectionAttackIntercept passInjectionAttackIntercept =
                this.getHandlerAnnotation(handlerMethod, PassInjectionAttackIntercept.class);


        String[] ignoreStrings = null;
        if (Objects.nonNull(passInjectionAttackIntercept)) {
            if (ArrayUtils.isEmpty(passInjectionAttackIntercept.value())) {
                LogManager.getLogger().debug("pass,不需要注入攻击拦截");
                return true;
            }
            ignoreStrings = passInjectionAttackIntercept.value();
        }


        final String parameters = RequestUtils.getRequestParameters(request);
        LogManager.getLogger().debug("请求参数 : {} ", parameters);
        LogManager.getLogger().debug("ignoreStrings : {} ", Arrays.toString(ignoreStrings));


        // 参数注入攻击处理
        if (this.injectionAttackHandler.isInjectionAttack(parameters, ignoreStrings)) {
            LogManager.getLogger().debug("参数 {} 被判断为注入攻击", parameters);
            this.injectionAttackHandler.attackHandle(request, response, parameters);
            return false;
        }

        @SuppressWarnings("unchecked")
        final Map<String, String> decodedUriVariables = (Map<String, String>) request
                .getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);

        if (MapUtils.isEmpty(decodedUriVariables)) {
            return true;
        }

        // URI PATH 注入攻击处理
        for (String decodedUriVariable : decodedUriVariables.values()) {
            if (this.injectionAttackHandler.isInjectionAttack(decodedUriVariable, ignoreStrings)) {
                LogManager.getLogger().debug("URI {} 被判断为注入攻击", parameters);
                this.injectionAttackHandler.attackHandle(request, response, decodedUriVariable);
                return false;
            }
        }
        return true;
    }

    private <T extends Annotation> T getHandlerAnnotation(HandlerMethod handlerMethod,
            Class<T> clazz) {
        T annotation = handlerMethod.getMethodAnnotation(clazz);
        if (Objects.nonNull(annotation)) {
            return annotation;
        }
        return handlerMethod.getBean().getClass().getAnnotation(clazz);
    }
}

package com.yjl.distributed.mq.config.common.util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.BeanUtils;

/**
 * 继承并扩展spring BeanUtils
 * 
 * @author zhaoyc@1109
 * @version 创建时间：2017年8月8日 下午4:57:01
 */
public class BeanPlusUtils extends BeanUtils {

    /**
     * bean对象转成map对象
     * 
     * @param obj bean对象
     * @param prefix key属性的前缀
     * @return
     */
    public static Map<String, Object> bean2map(Object obj, String prefix) {
        if (obj == null) {
            return null;
        }

        try {
            Map<String, Object> map = new HashMap<String, Object>();

            Field[] declaredFields = obj.getClass().getDeclaredFields();
            for (Field field : declaredFields) {
                field.setAccessible(true);
                Object value = field.get(obj);
                map.put(prefix + field.getName(), value == null ? "" : value);
            }
            return map;
        } catch (IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;
    }
}

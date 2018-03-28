package com.yjl.distributed.mq.config.common.util;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Map操作工具
 * 
 * @author WuJianYing
 * @since 2018年1月26日
 *
 */
public class MapUtils {

    /**
     * 按key排序
     * 
     * @author WuJianYing
     * @since 2018年1月26日
     * @param map
     * @return
     */
    public static Map<String, Object> sortByKey(Map<String, Object> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }

        Map<String, Object> sorted = new TreeMap<String, Object>(new MapKeyComparator());

        sorted.putAll(map);

        return sorted;
    }

    /**
     * 按key排序后，按 key:value,key:value ...格式转成字符串
     * 
     * @author WuJianYing
     * @since 2018年1月26日
     * @param map
     * @return
     */
    public static String toStringAndSortedByKey(Map<String, Object> map) {
        Map<String, Object> sorted = sortByKey(map);

        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, Object> entry : sorted.entrySet()) {
            sb.append(entry.getKey()).append(":").append(entry.getValue()).append(",");
        }

        return sb.deleteCharAt(sb.length() - 1).toString();
    }

    /**
     * 将有规则的字符串转成Map
     * 
     * @author WuJianYing
     * @since 2018年1月26日
     * @param string
     * @param decollator1 第一维度分割符
     * @param decollator2 第二维度分割符
     * @return
     */
    public static Map<String, String> toMap(String string, String decollator1, String decollator2) {
        String[] array = string.split(decollator1);

        Map<String, String> map = new HashMap<String, String>();

        for (String s : array) {
            String[] keyValue = s.split(decollator2);
            map.put(keyValue[0], keyValue[1]);
        }

        return map;
    }

}


class MapKeyComparator implements Comparator<String> {
    @Override
    public int compare(String o1, String o2) {
        return o1.compareTo(o2);
    }
}



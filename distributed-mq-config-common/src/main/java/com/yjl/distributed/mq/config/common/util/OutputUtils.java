package com.yjl.distributed.mq.config.common.util;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 
 * @author zhaoyc@1109
 * @version 创建时间：2017年10月18日 上午11:47:06
 */
public class OutputUtils {
    private static final Logger logger = LoggerFactory.getLogger(OutputUtils.class);

    /**
     * 直接向浏览器输出成功信息
     * 
     * @param response
     * @param msg
     */
    public static void success(HttpServletResponse response, String msg) {
        PrintWriter pw = null;
        try {
            response.setContentType("text/json;charset=UTF-8");
            response.setStatus(200);
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);

            pw = response.getWriter();
            pw.print(msg);// 向页面输出
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e.toString(), e);
        } finally {
            if (pw != null) {
                pw.close();
            }
        }
    }

    /**
     * 向浏览器输出成功json信息,key是msg
     * 
     * @param response
     * @param msg
     */
    public static void successJson(HttpServletResponse response, String msg) {
        PrintWriter pw = null;
        try {
            JSONObject res = new JSONObject();
            res.put("statusCode", 200);
            res.put("statusMessage", msg);

            response.setContentType("text/json;charset=UTF-8");
            response.setStatus(200);
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);

            pw = response.getWriter();
            pw.print(res.toString());// 向页面输出
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e.toString(), e);
        } finally {
            if (pw != null) {
                pw.close();
            }
        }
    }

    /**
     * 向浏览器输出成功json信息，将对象自动转换成json
     * 
     * @param response
     * @param msg
     */
    public static void successJsonObject(HttpServletResponse response, Object obj) {
        PrintWriter pw = null;
        try {
            String res = JSONObject.toJSONString(obj);

            response.setContentType("text/json;charset=UTF-8");
            response.setStatus(200);
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);

            pw = response.getWriter();
            pw.print(res);// 向页面输出
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e.toString(), e);
        } finally {
            if (pw != null) {
                pw.close();
            }
        }
    }

    /**
     * 向浏览器输出成功json信息，将列表自动转换成json
     * 
     * @param response
     * @param msg
     */
    public static void successJsonArray(HttpServletResponse response, Object obj) {
        PrintWriter pw = null;
        try {
            String res = JSONArray.toJSONString(obj);

            response.setContentType("text/json;charset=UTF-8");
            response.setStatus(200);
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);

            pw = response.getWriter();
            pw.print(res);// 向页面输出
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e.toString(), e);
        } finally {
            if (pw != null) {
                pw.close();
            }
        }
    }

    /**
     * 向浏览器输出未授权提示json信息,key是msg
     * 
     * @param response
     * @param msg
     */
    public static void unauthorizedJson(HttpServletResponse response, String msg) {
        PrintWriter pw = null;
        try {
            JSONObject res = new JSONObject();
            res.put("statusCode", 401);
            res.put("statusMessage", msg);

            response.setContentType("text/json;charset=UTF-8");
            response.setStatus(401);
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);

            pw = response.getWriter();
            pw.print(res.toString());// 向页面输出
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e.toString(), e);
        } finally {
            if (pw != null) {
                pw.close();
            }
        }
    }


    /**
     * 直接向浏览器输出失败信息
     * 
     * @param response
     * @param msg
     */
    public static void error(HttpServletResponse response, String msg) {
        PrintWriter pw = null;
        try {
            response.setContentType("text/json;charset=UTF-8");
            response.setStatus(500);
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);

            pw = response.getWriter();
            pw.print(msg);// 向页面输出
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e.toString(), e);
        } finally {
            if (pw != null) {
                pw.close();
            }
        }
    }

    /**
     * 向浏览器输出失败json信息,key是msg
     * 
     * @param response
     * @param msg
     */
    public static void errorJson(HttpServletResponse response, String msg) {
        PrintWriter pw = null;
        try {
            JSONObject res = new JSONObject();
            res.put("statusCode", 500);
            res.put("statusMessage", msg);

            response.setContentType("text/json;charset=UTF-8");
            response.setStatus(500);
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);

            pw = response.getWriter();
            pw.print(res.toString());// 向页面输出
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e.toString(), e);
        } finally {
            if (pw != null) {
                pw.close();
            }
        }
    }


}

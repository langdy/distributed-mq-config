package com.yjl.distributed.mq.config.common.util;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.springframework.http.HttpStatus;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yjl.distributed.mq.config.common.ResponseEntity.StatusCode;

/**
 * 
 * @author zhaoyc@1109
 * @version 创建时间：2017年10月18日 上午11:47:06
 */
public class OutputUtils {

    /**
     * 直接向浏览器输出成功信息
     * 
     * @param response
     * @param message
     */
    public static void success(HttpServletResponse response, Object message) {
        PrintWriter pw = null;
        try {
            response.setContentType("text/json;charset=UTF-8");
            response.setStatus(HttpStatus.OK.value());
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);

            pw = response.getWriter();
            // 向页面输出
            pw.print(JSON.toJSON(message));
        } catch (IOException e) {
            e.printStackTrace();
            LogManager.getLogger().error(e.toString(), e);
        } finally {
            if (pw != null) {
                pw.close();
            }
        }
    }

    /**
     * 向浏览器输出成功json信息
     * 
     * @param response
     * @param statusMessage
     * @param responseContent
     */
    public static void successJson(HttpServletResponse response, Object statusMessage,
            Object responseContent) {
        PrintWriter pw = null;
        try {
            JSONObject res = new JSONObject();
            res.put("statusCode", StatusCode.OK.getStatusCode());
            res.put("statusMessage", JSON.toJSON(statusMessage));
            res.put("responseContent", JSON.toJSONString(responseContent));

            response.setContentType("text/json;charset=UTF-8");
            response.setStatus(HttpStatus.OK.value());
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);

            pw = response.getWriter();
            // 向页面输出
            pw.print(res.toString());
        } catch (IOException e) {
            e.printStackTrace();
            LogManager.getLogger().error(e.toString(), e);
        } finally {
            if (pw != null) {
                pw.close();
            }
        }
    }

    /**
     * 向浏览器输出未授权提示json信息
     * 
     * @param response
     * @param statusMessage
     * @param responseContent
     */
    public static void unauthorizedJson(HttpServletResponse response, Object statusMessage,
            Object responseContent) {
        PrintWriter pw = null;
        try {
            JSONObject res = new JSONObject();
            res.put("statusCode", StatusCode.UNAUTHORIZED.getStatusCode());
            res.put("statusMessage", JSON.toJSON(statusMessage));
            res.put("responseContent", JSON.toJSONString(responseContent));

            response.setContentType("text/json;charset=UTF-8");
            response.setStatus(HttpStatus.OK.value());
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);

            pw = response.getWriter();
            // 向页面输出
            pw.print(res.toString());
        } catch (IOException e) {
            e.printStackTrace();
            LogManager.getLogger().error(e.toString(), e);
        } finally {
            if (pw != null) {
                pw.close();
            }
        }
    }

    /**
     * 向浏览器输出api授权提示json信息
     * 
     * @param response
     * @param statusCode
     * @param statusMessage
     * @param responseContent
     */
    public static void apiAuthorizedJson(HttpServletResponse response, String statusCode,
            Object statusMessage, Object responseContent) {
        PrintWriter pw = null;
        try {
            JSONObject res = new JSONObject();
            res.put("statusCode", statusCode);
            res.put("statusMessage", JSON.toJSON(statusMessage));
            res.put("responseContent", JSON.toJSONString(responseContent));

            response.setContentType("text/json;charset=UTF-8");
            response.setStatus(HttpStatus.OK.value());
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);

            pw = response.getWriter();
            // 向页面输出
            pw.print(res.toString());
        } catch (IOException e) {
            e.printStackTrace();
            LogManager.getLogger().error(e.toString(), e);
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
     * @param message
     */
    public static void error(HttpServletResponse response, Object message) {
        PrintWriter pw = null;
        try {
            response.setContentType("text/json;charset=UTF-8");
            response.setStatus(HttpStatus.OK.value());
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);

            pw = response.getWriter();
            // 向页面输出
            pw.print(JSON.toJSONString(message));
        } catch (IOException e) {
            e.printStackTrace();
            LogManager.getLogger().error(e.toString(), e);
        } finally {
            if (pw != null) {
                pw.close();
            }
        }
    }

    /**
     * 向浏览器输出失败json信息
     * 
     * @param response
     * @param statusMessage
     * @param responseContent
     */
    public static void errorJson(HttpServletResponse response, Object statusMessage,
            Object responseContent) {
        PrintWriter pw = null;
        try {
            JSONObject res = new JSONObject();
            res.put("statusCode", StatusCode.SYSTEM_ERROR.getStatusCode());
            res.put("statusMessage", JSON.toJSONString(statusMessage));
            res.put("responseContent", JSON.toJSONString(responseContent));

            response.setContentType("text/json;charset=UTF-8");
            response.setStatus(HttpStatus.OK.value());
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);

            pw = response.getWriter();
            // 向页面输出
            pw.print(res.toString());
        } catch (IOException e) {
            e.printStackTrace();
            LogManager.getLogger().error(e.toString(), e);
        } finally {
            if (pw != null) {
                pw.close();
            }
        }
    }


}

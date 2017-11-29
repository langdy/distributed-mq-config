package com.yjl.distributed.mq.config;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import com.github.pagehelper.PageInfo;
import com.yjl.distributed.mq.config.common.ResponseEntity;
import com.yjl.distributed.mq.config.common.annotation.NeedExport;
import com.yjl.distributed.mq.config.common.util.Export;
import com.yjl.distributed.mq.config.common.util.JsonUtils;
import com.yjl.distributed.mq.config.common.util.ReflectionPlusUtils;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * api {@link ResponseEntity} 返回类型处理
 *
 */
@RestControllerAdvice
public class GlobalResponseControllerAdvice implements ResponseBodyAdvice<ResponseEntity> {


    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return ResponseEntity.class.isAssignableFrom(returnType.getParameterType());
    }

    @Override
    public ResponseEntity beforeBodyWrite(ResponseEntity body, MethodParameter returnType,
            MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request,
            ServerHttpResponse response) {
        HttpServletRequest servletRequest =
                ((ServletServerHttpRequest) request).getServletRequest();
        HttpServletResponse servletResponse =
                ((ServletServerHttpResponse) response).getServletResponse();

        // 转化为http协议返回码
        servletResponse.setStatus(Integer.parseInt(body.getStatusCode()));

        // 获取导出字段标题
        LinkedHashMap<String, String> exportTitleMap = body.getExportTitleMap();

        // | 1. 字段过滤处理 |
        // - 如果自行设置了,那么就以自行设置的为主
        if (body.isFieldsFilter()) {
            body = body.filterFieldsFlush();
        }

        // ---------------------------------------------------------------------------------------------------------------------------------------
        // | 2. 导出处理 | 这里写在一个类中,显得臃肿,后续调整

        if (this.isExport(servletRequest, returnType)) {
            return this.exportHandle(body, returnType, selectedContentType, selectedConverterType,
                    exportTitleMap, servletRequest, servletResponse);
        }
        return body;
    }


    /**
     * 导出处理
     *
     * @param body
     * @param returnType
     * @param selectedContentType
     * @param selectedConverterType
     * @param exportTitleMap
     * @param request
     * @param response
     * @return
     */
    private ResponseEntity exportHandle(ResponseEntity body, MethodParameter returnType,
            MediaType selectedContentType, Class selectedConverterType,
            LinkedHashMap<String, String> exportTitleMap, HttpServletRequest request,
            HttpServletResponse response) {
        // 服务器导出配置
        final NeedExport needExport = returnType.getMethod().getAnnotation(NeedExport.class);
        // 客户端自定义的导出配置
        ExportParamsMessage exportParamsMessage =
                this.extractExportParamsMessage(request, needExport);
        // 得到导出数据
        List dataList = this.getExportList(body);
        final Export.Type exportType = this.getExportType(needExport, exportParamsMessage);
        final String exportFileDefaultName =
                this.getExportFileDefaultName(needExport, exportParamsMessage);

        final LinkedHashMap<String, String> titleMap = exportTitleMap == null
                ? ReflectionPlusUtils.exportFiledComment(needExport.exportClass()) : exportTitleMap;


        LogManager.getLogger().debug("exportType : {}", exportType);
        LogManager.getLogger().debug("exportFileDefaultName : {}", exportFileDefaultName);
        LogManager.getLogger().debug("titleMap : {}", titleMap);
        try {
            Export.export(exportType, exportFileDefaultName, titleMap, dataList, response);
            return body;
        } catch (IOException e) {
            LogManager.getLogger().error(e);
            return new ResponseEntity<String>().badRequest("导出异常:", e.getMessage());
        }
    }

    /**
     * 提取客户端导出参数
     *
     * @param request
     * @param needExport
     * @return
     */
    private ExportParamsMessage extractExportParamsMessage(HttpServletRequest request,
            NeedExport needExport) {
        final String headerExportParamsMessage =
                this.extractExportMessageFromRequest(request, "export");
        LogManager.getLogger().debug("needExport : {}", needExport);
        ExportParamsMessage exportParamsMessage =
                JsonUtils.jsonToType(headerExportParamsMessage, ExportParamsMessage.class);
        // 解析失败,给个空,这样才不会影响正常流程
        if (Objects.isNull(exportParamsMessage)) {
            exportParamsMessage = ExportParamsMessage.EMPTY;
        }
        return exportParamsMessage;
    }

    /**
     * 得到导出数据
     *
     * @param body
     * @return
     */
    private List getExportList(ResponseEntity body) {
        final Object responseContent = body.getResponseContent();
        List dataList;
        if (responseContent instanceof List) {
            dataList = (List) responseContent;
        } else if (responseContent instanceof PageInfo) {
            dataList = ((PageInfo) responseContent).getList();
        } else if (responseContent instanceof Map) {
            dataList = (List) ((Map) responseContent).get("list");
        } else {
            dataList = Collections.singletonList(responseContent);
        }
        return dataList;
    }

    /**
     * 得到导出文件名
     *
     * @param needExport
     * @param exportParamsMessage
     * @return
     */
    private String getExportFileDefaultName(NeedExport needExport,
            ExportParamsMessage exportParamsMessage) {
        if (StringUtils.isNotEmpty(exportParamsMessage.getExportFileName())) {
            return exportParamsMessage.getExportFileName();
        }
        return needExport.exportFileDefaultName();
    }

    /**
     * 得到导出类型
     *
     * @param needExport
     * @param exportParamsMessage
     * @return
     */
    private Export.Type getExportType(NeedExport needExport,
            ExportParamsMessage exportParamsMessage) {
        if (Objects.nonNull(exportParamsMessage.getExportType())) {
            return exportParamsMessage.getExportType();
        }
        return needExport.exportDefaultType();
    }

    /**
     * 是否是导出
     *
     * @param request
     * @param returnType
     * @return
     */
    private boolean isExport(HttpServletRequest request, MethodParameter returnType) {
        final Method method = returnType.getMethod();
        final NeedExport needExport = method.getAnnotation(NeedExport.class);
        final String exportParamsMessage = this.extractExportMessageFromRequest(request, "export");
        final boolean isExportHeader = Objects.nonNull(exportParamsMessage);
        final boolean isNeedExport = Objects.nonNull(needExport);
        return isExportHeader && isNeedExport;
    }

    /**
     * 请求中获取导出信息
     *
     * @param request
     * @return
     */
    private String extractExportMessageFromRequest(HttpServletRequest request, String headerName) {
        String header = request.getHeader(headerName);
        if (StringUtils.isEmpty(header)) {
            header = request.getParameter(headerName);
        }
        LogManager.getLogger().debug("{} : {}", headerName, header);
        return header;
    }


    @Data
    @Accessors(chain = true)
    private static class ExportParamsMessage implements Serializable {
        /**
         * 
         */
        private static final long serialVersionUID = 6012182515631669157L;
        public static final ExportParamsMessage EMPTY = new ExportParamsMessage();

        private Export.Type exportType;
        private String exportFileName;
    }

}


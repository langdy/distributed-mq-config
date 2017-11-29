package com.yjl.distributed.mq.config.common.filter;


import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import com.yjl.distributed.mq.config.common.util.RequestUtils;

/**
 * 请求信息打印过滤器
 *
 * @author : zhaoyc
 * @date : 2017/1/17
 */
public class RequestLoggingFilter implements Filter {

    private static final String PASSWORD_FILTER_REGEX =
            "(password=\\[([\\S\\s])*\\])|(\"password\":\"([\\S\\s])*\")";


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        final BodyReaderWrapper wrapper = new BodyReaderWrapper((HttpServletRequest) request);
        String requestMessage = RequestUtils.getRequestMessage(wrapper);
        if (!LogManager.getLogger().isDebugEnabled()) {
            requestMessage = StringUtils.replaceAll(requestMessage, PASSWORD_FILTER_REGEX,
                    "enable password protection, if not debug so do not see");
        }
        LogManager.getLogger().info(requestMessage);
        chain.doFilter(wrapper, response);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void destroy() {}


}

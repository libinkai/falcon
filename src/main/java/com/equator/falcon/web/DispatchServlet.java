package com.equator.falcon.web;

import com.equator.falcon.configuration.ConfigurationFactory;
import com.equator.falcon.ioc.BeanContainer;
import com.equator.falcon.util.CodecUtil;
import com.equator.falcon.util.JsonUtil;
import com.equator.falcon.util.ReflectionHelper;
import com.equator.falcon.util.StreamUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * 请求转发器
 *
 * @Author: Equator
 * @Date: 2020/2/12 9:18
 **/

// @WebServlet(urlPatterns = "/*", loadOnStartup = 0)
public class DispatchServlet extends HttpServlet {
    Logger logger = LoggerFactory.getLogger(DispatchServlet.class);

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        ComponentLoader.init();
        // 获取Servlet上下文用于注册Servlet
        ServletContext servletContext = servletConfig.getServletContext();
        // TODO 注册JSP
        // 注册默认资源Servlet
        ServletRegistration resourcesServlet = servletContext.getServletRegistration("default");
        resourcesServlet.addMapping(ConfigurationFactory.getResourcesUrl());
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("DispatchServlet ...");
        String requestMethod = req.getMethod().toLowerCase();
        String requestPath = req.getPathInfo();
        // 获取处理器
        Handler handler = HandlerMapping.getHandler(requestMethod, requestPath);
        if (handler != null) {
            logger.debug("handler {}", handler);
            // 获取 controller的class与实例
            Class<?> controllerClass = handler.getControllerClass();
            Object controllerInstance = BeanContainer.getBean(controllerClass);
            Map<String, Object> paramMap = new HashMap<>();
            Enumeration<String> paramNames = req.getParameterNames();
            while (paramNames.hasMoreElements()) {
                String paramKey = paramNames.nextElement();
                String paramValue = req.getParameter(paramKey);
                paramMap.put(paramKey, paramValue);
            }
            String body = CodecUtil.decodeUrl(StreamUtil.getStringValue(req.getInputStream()));
            if (StringUtils.isNotEmpty(body)) {
                Map<String, String> paramsFromRequestBody = CodecUtil.getParamsFromRequestBody(body);
                paramMap.putAll(paramsFromRequestBody);
            }
            // 调用 Action方法
            RequestParam requestParam = new RequestParam(paramMap);
            Method actionMethod = handler.getActionMethod();
            Object result = ReflectionHelper.invokeMethod(controllerInstance, actionMethod);
            // 对于那些带有Action注解且返回值类型是JsonData的方法
            if (result instanceof JsonData) {
                JsonData jsonData = (JsonData) result;
                Object data = jsonData.getModel();
                if (data != null) {
                    resp.setContentType("application/json");
                    resp.setCharacterEncoding("UTF-8");
                    PrintWriter writer = resp.getWriter();
                    logger.debug("is null {}", writer == null);
                    logger.debug("result {}", data);
                    String s = JsonUtil.toJson(data);
                    logger.debug("result json {}", s);
                    writer.write(s);
                    writer.flush();
                    writer.close();
                }
            }
            // TODO 处理其它数据类型，如JSP
        }
    }
}

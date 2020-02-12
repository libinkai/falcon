package com.equator.falcon.web;

import com.equator.falcon.configuration.ConfigurationFactory;
import com.equator.falcon.util.*;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebServlet;
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

@WebServlet(urlPatterns = "/*", loadOnStartup = 0)
public class DispatchServlet extends HttpServlet {
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
        String requestMethod = req.getMethod().toLowerCase();
        String requestPath = req.getPathInfo();
        // 获取处理器
        Handler handler = HandlerMapping.getHandler(requestMethod, requestPath);
        if (handler != null) {
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
            Object result = ReflectionHelper.invokeMethod(controllerInstance, actionMethod, requestParam);
            if (result instanceof JsonData) {
                JsonData jsonData = (JsonData) result;
                Object data = jsonData.getModel();
                if (data != null) {
                    resp.setContentType("application/json");
                    resp.setCharacterEncoding("UTF-8");
                    PrintWriter writer = resp.getWriter();
                    writer.write(JsonUtil.toJson(data));
                    writer.flush();
                    writer.close();
                }
            }
            // 处理其它数据类型，如JSP

        }
    }
}

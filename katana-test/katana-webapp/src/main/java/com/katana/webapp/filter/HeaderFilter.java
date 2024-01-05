package com.katana.webapp.filter;

import jakarta.annotation.Resource;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jooq.impl.ImplicitColumnListener;
import org.jooq.tools.StringUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author : liyifei
 * @created : 2023/10/20, 星期五
 * Copyright (c) 2004-2029 All Rights Reserved.
 **/
@WebFilter("/*")
@Component
public class HeaderFilter extends HttpFilter {

    @Resource
    private ImplicitColumnListener completeListener;

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String tenantCode = request.getHeader("tenant_code");
        String env = request.getHeader("env");
        if (!StringUtils.isEmpty(tenantCode)) {
            completeListener.setImplicitValue("tenant_code", tenantCode);
            completeListener.setImplicitValue("env", env);
        }
        super.doFilter(request, response, chain);
    }
}

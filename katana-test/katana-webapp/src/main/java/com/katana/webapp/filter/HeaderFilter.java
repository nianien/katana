package com.katana.webapp.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jooq.impl.FieldCompleteListener;
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

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String tenantCode = request.getHeader("tenant_code");
        String env = request.getHeader("env");
        if (!StringUtils.isEmpty(tenantCode)) {
            FieldCompleteListener.setFieldValue("tenant_code", tenantCode);
            FieldCompleteListener.setFieldValue("env", env);
        }
        super.doFilter(request, response, chain);
    }
}

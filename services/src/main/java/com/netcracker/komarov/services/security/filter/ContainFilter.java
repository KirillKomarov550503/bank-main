package com.netcracker.komarov.services.security.filter;

import com.netcracker.komarov.services.security.utils.CustomUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ContainFilter implements Filter {
    private static final Logger LOGGER = LoggerFactory.getLogger(ContainFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        LOGGER.info("Init ContainFilter");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        CustomUser customUser = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (customUser != null) {
            String uri = httpServletRequest.getRequestURI();
            Pattern pattern;
            Matcher matcher;
            String adminsRegex = "(^/bank/v1/admins/[0-9]+$)|(^/bank/v1/admins/[0-9]+/.+$)";
            pattern = Pattern.compile(adminsRegex);
            matcher = pattern.matcher(uri);
            if (matcher.matches()) {
                check(servletRequest, servletResponse, filterChain, httpServletResponse, customUser, uri);
            } else {
                String clientRegex = "(^/bank/v1/clients/[0-9]+$)|(^/bank/v1/clients/[0-9]+/.+$)";
                pattern = Pattern.compile(clientRegex);
                matcher = pattern.matcher(uri);
                if (matcher.matches()) {
                    check(servletRequest, servletResponse, filterChain, httpServletResponse, customUser, uri);
                } else {
                    filterChain.doFilter(servletRequest, servletResponse);
                }
            }
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    private void check(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain,
                       HttpServletResponse httpServletResponse, CustomUser customUser, String uri) throws IOException,
            ServletException {
        String[] strings = uri.split("/");
        long id = Long.valueOf(strings[4]);
        if (id == customUser.getId()) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            httpServletResponse.sendError(HttpServletResponse.SC_FORBIDDEN,
                    "You do not have access to this account");
        }
    }

    @Override
    public void destroy() {
        LOGGER.info("Destroy ContainFilter");
    }
}

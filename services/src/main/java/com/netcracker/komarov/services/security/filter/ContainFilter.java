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
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

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
            String[] strings = uri.split("/");
            String role = strings[3];
            String[] roles = {"admins", "clients", "people"};
            Optional<String> search = Stream.of(roles)
                    .filter(string -> string.equals(role))
                    .findFirst();
            if (search.isPresent()) {
                String regex = "(^/bank/v1/" + role + "/[0-9]+$)|(^/bank/v1/" + role + "/[0-9]+/.+$)";
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(uri);
                if (matcher.matches()) {
                    long id = Long.valueOf(strings[4]);
                    if (id == customUser.getId()) {
                        filterChain.doFilter(servletRequest, servletResponse);
                    } else {
                        httpServletResponse.sendError(HttpServletResponse.SC_FORBIDDEN,
                                "You do not have access to this account");
                    }
                } else {
                    filterChain.doFilter(servletRequest, servletResponse);
                }
            } else {
                httpServletResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "Bad request");
            }
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {
        LOGGER.info("Destroy ContainFilter");
    }
}

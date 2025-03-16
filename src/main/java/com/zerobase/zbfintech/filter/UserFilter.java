package com.zerobase.zbfintech.filter;

import com.zerobase.zbfintech.auth.JwtAuthenticationProvider;
import com.zerobase.zbfintech.entity.UserVo;
import com.zerobase.zbfintech.service.UserService;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;

@WebFilter(urlPatterns = "/user/*")
public class UserFilter implements Filter {

    private final JwtAuthenticationProvider jwtAuthenticationProvider;
    private final UserService userService;

    public UserFilter(JwtAuthenticationProvider jwtAuthenticationProvider, UserService userService) {
        this.jwtAuthenticationProvider = jwtAuthenticationProvider;
        this.userService = userService;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        String token = req.getHeader("Authorization");
        if (token == null || token.isEmpty()) {
            throw new ServletException("Authorization token is missing");
        }
        UserVo vo = jwtAuthenticationProvider.getValidatedUserVo(token)
                        .orElseThrow(() -> new ServletException("Invalid Access"));
        userService.findByIdAndEmail(vo.getId(), vo.getEmail())
                .orElseThrow(() -> new ServletException("Invalid Access"));
        chain.doFilter(request, response);
    }
}

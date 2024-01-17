package com.example.notes.security.filter;

import com.example.notes.security.util.JwtUtil;
import com.example.notes.service.UserDetailsDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    UserDetailsDAO userDetailsDAO;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String autHeader = request.getHeader("Authorization");
        final String userEmail;
        final String jwtToken;

        if (ObjectUtils.isEmpty(autHeader) || !autHeader.startsWith("Bearer")) {
            filterChain.doFilter(request, response);
            return;
        }

        jwtToken = autHeader.substring(7);
        userEmail = jwtUtil.extractUsername(jwtToken);
        if (!ObjectUtils.isEmpty(userEmail) && ObjectUtils.isEmpty(SecurityContextHolder.getContext().getAuthentication())) {
            User userDetail = userDetailsDAO.loadUserByEmail(userEmail);
            final boolean isTokenValid = jwtUtil.validateToken(jwtToken, userDetail);

            if (isTokenValid) {
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetail.getUsername(), userDetail.getPassword(), userDetail.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}

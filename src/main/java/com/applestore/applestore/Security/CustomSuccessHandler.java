package com.applestore.applestore.Security;

import com.applestore.applestore.Entities.Role;
import jakarta.servlet.FilterChain;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

public class CustomSuccessHandler implements AuthenticationSuccessHandler {

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();


    @Override
    public void onAuthenticationSuccess(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, jakarta.servlet.ServletException {
        AuthenticationSuccessHandler.super.onAuthenticationSuccess(request, response, chain, authentication);
    }

    @Override
    public void onAuthenticationSuccess(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response, Authentication authentication) throws IOException, jakarta.servlet.ServletException {
        String targetUrl = determineTargetUrl(authentication);
        if (response.isCommitted()) {
            return;
        }
        redirectStrategy.sendRedirect(request, response, targetUrl);
    }

    protected String determineTargetUrl(Authentication authentication) {
        boolean isUser = false;
        boolean isAdmin = false;

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (GrantedAuthority grantedAuthority : authorities) {
            System.out.println("CSH: "+grantedAuthority.getAuthority());
            if (grantedAuthority.getAuthority().equals("ROLE_USER")) {
                isUser = true;
                break;
            } else if (grantedAuthority.getAuthority().equals("ROLE_ADMIN")) {
                isAdmin = true;
                break;
            }
        }


        if (isUser) {
            return "/user/";
        } else if (isAdmin) {
            return "/admin/viewAll";
        } else {
            throw new IllegalStateException();
        }
    }
}

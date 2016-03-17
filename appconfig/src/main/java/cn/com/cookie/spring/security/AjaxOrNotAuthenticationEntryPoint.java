package cn.com.cookie.spring.security;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 判断是否是ajax请求
 * 
 * @author Cookie
 *
 */
@SuppressWarnings("deprecation")
public class AjaxOrNotAuthenticationEntryPoint extends LoginUrlAuthenticationEntryPoint {

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        String redirectUrl = null;

        String url = request.getRequestURI();

        if (url.indexOf("ajax") == -1) {

            if (this.isUseForward()) {

                if (this.isForceHttps() && "http".equals(request.getScheme())) {
                    // First redirect the current request to HTTPS.
                    // When that request is received, the forward to the login page will be used.
                    redirectUrl = buildHttpsRedirectUrlForRequest(request);
                }

                if (redirectUrl == null) {
                    String loginForm = determineUrlToUseForThisRequest(request, response, authException);

                    RequestDispatcher dispatcher = request.getRequestDispatcher(loginForm);

                    dispatcher.forward(request, response);

                    return;
                }
            } else {
                // redirect to login page. Use https if forceHttps true

                redirectUrl = buildRedirectUrlToLoginPage(request, response, authException);

            }

            redirectStrategy.sendRedirect(request, response, redirectUrl);
        } else {

            ObjectMapper objectMapper = new ObjectMapper();
            response.setHeader("Content-Type", "application/json;charset=UTF-8");
            objectMapper.writeValue(response.getOutputStream(), "{}");
        }
    }

}

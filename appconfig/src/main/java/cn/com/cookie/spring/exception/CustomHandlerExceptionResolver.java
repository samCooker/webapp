package cn.com.cookie.spring.exception;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

/**
 * 自定义异常捕获 可在xml文件实例化
 * 
 * @author Cookie
 *
 */
public class CustomHandlerExceptionResolver implements HandlerExceptionResolver {

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        // 自定义的异常捕获
        if (!(request.getHeader("accept").indexOf("application/json") > -1 || (request.getHeader("X-Requested-With") != null && request.getHeader("X-Requested-With").indexOf("XMLHttpRequest") > -1))) {
            // 如果不是异步请求，可根据异常类型返回不同页面
            return null;
        } else {
            // JSON格式返回
            try {
                PrintWriter writer = response.getWriter();
                String errorMsg = ex.getMessage();
                writer.write(errorMsg != null ? errorMsg : "server error");
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
package cn.com.cookie.common.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import cn.com.cookie.common.bean.ErrorInfo;

/**
 * 捕获控制器抛出的异常
 * 
 * @author Cookie
 *
 */
// @ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(value = HttpStatus.METHOD_NOT_ALLOWED)
    @ResponseBody
    public ResponseEntity<String> requestHandlingError(HttpServletRequest req, HttpRequestMethodNotSupportedException ex) {
        String errorMessage = ex.getMessage();

        String errorURL = req.getRequestURL().toString();

        ErrorInfo errorInfo = new ErrorInfo(errorURL, errorMessage);
        return new ResponseEntity<String>(errorInfo.toJson(), HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<String> handleIOException(HttpServletRequest req, NullPointerException ex) {
        String errorMessage = ex.getMessage();

        String errorURL = req.getRequestURL().toString();

        ErrorInfo errorInfo = new ErrorInfo(errorURL, errorMessage);
        return new ResponseEntity<String>(errorInfo.toJson(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<String> requestHandlingNoHandlerFound(HttpServletRequest req, Exception ex) {
        String errorMessage = ex.getMessage();

        String errorURL = req.getRequestURL().toString();

        ErrorInfo errorInfo = new ErrorInfo(errorURL, errorMessage);
        return new ResponseEntity<String>(errorInfo.toJson(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

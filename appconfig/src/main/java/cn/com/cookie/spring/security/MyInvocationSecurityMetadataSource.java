package cn.com.cookie.spring.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

import cn.com.cookie.spring.tools.AntUrlPathMatcher;
import cn.com.cookie.spring.tools.UrlMatcher;

public class MyInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
    private UrlMatcher                                      urlMatcher  = new AntUrlPathMatcher();
    private static Map<String, Collection<ConfigAttribute>> resourceMap = null;

    // tomcat启动时实例化一次
    public MyInvocationSecurityMetadataSource() {
        loadResourceDefine();
    }

    // tomcat开启时加载一次，加载所有url和权限（或角色）的对应关系
    private void loadResourceDefine() {
        resourceMap = new HashMap<String, Collection<ConfigAttribute>>();
        Collection<ConfigAttribute> adminCas = new ArrayList<ConfigAttribute>();
        Collection<ConfigAttribute> otherCas = new ArrayList<ConfigAttribute>();
        ConfigAttribute admin = new SecurityConfig("ROLE_ADMIN");
        ConfigAttribute other = new SecurityConfig("ROLE_OTHER");
        adminCas.add(admin);

        otherCas.add(admin);
        otherCas.add(other);
        resourceMap.put("/home.json", otherCas);
        resourceMap.put("/plugin/*.json", otherCas);
        resourceMap.put("/other/*.json", otherCas);
        resourceMap.put("/**", adminCas);
        resourceMap.put("/user/*.json", adminCas);
    }

    // 参数是要访问的url，返回这个url对于的所有权限（或角色）
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        // 将参数转为url
        String url = ((FilterInvocation) object).getRequestUrl();
        Iterator<String> ite = resourceMap.keySet().iterator();
        while (ite.hasNext()) {
            String resURL = ite.next();
            if (urlMatcher.pathMatchesUrl(resURL, url)) {
                return resourceMap.get(resURL);
            }
        }
        throw new AccessDeniedException("forbidden");
    }

    public boolean supports(Class<?> clazz) {
        return true;
    }

    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }
}

package cn.com.cookie.spring.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class MyUserDetailService implements UserDetailsService {

    private String admin = "cookie";

    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException, DataAccessException {
        // 可从数据库获取用户信息及对应的角色
        // 这里直接模拟从数据库获取数据
        if (userName != null && userName.matches("[A-Za-z]+")) {
            Collection<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();
            List<String> rolesList = this.getRoles(userName);
            GrantedAuthority ga = null;
            for (String role : rolesList) {
                ga = new SimpleGrantedAuthority(role);
                auths.add(ga);
            }
            User user = new User(userName, "c4ca4238a0b923820dcc509a6f75849b", true, true, true, true, auths);
            return user;
        }
        throw new UsernameNotFoundException("user not found");
    }

    private List<String> getRoles(String userName) {
        List<String> roles = new ArrayList<>();
        if (admin.equals(userName)) {
            roles.add("ROLE_ADMIN");
        }
        roles.add("ROLE_OTHER");
        return roles;
    }
}
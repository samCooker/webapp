package cn.com.cookie.spring.bean;

public class LoginUser {

    /** 登陆用户名 */
    private String username;
    /** 登陆密码 */
    private String password;
    /** 登陆校验码 */
    private String validateCode;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getValidateCode() {
        return validateCode;
    }

    public void setValidateCode(String validateCode) {
        this.validateCode = validateCode;
    }

}

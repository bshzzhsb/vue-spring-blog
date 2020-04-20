package self.hsb.blog.web.admin;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;
import self.hsb.blog.po.User;
import self.hsb.blog.response.Response;
import self.hsb.blog.response.ResponseFactory;
import self.hsb.blog.service.UserService;

import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * @author SipooHe
 * @date 2020/4/12 16:24
 */
@Controller
@RequestMapping("/hsblog/admin")
public class UserController {

    @Autowired
    private UserService userService;

    @CrossOrigin
    @PostMapping("/register")
    @ResponseBody
    public Response register(@RequestBody User user) {
        String username = user.getUsername();
        String password = user.getPassword();
        username = HtmlUtils.htmlEscape(username);
        user.setUsername(username);
        boolean exist = userService.checkUser(username);
        if (exist) {
            String message = "用户名已存在";
            return ResponseFactory.buildFailResponse(message);
        }
        // 生成盐，默认长度16位
        String salt = new SecureRandomNumberGenerator().nextBytes().toString();
        // 设置hash算法迭代次数
        int times = 2;
        // 得到hash后的密码
        String encodedPassword = new SimpleHash("md5", password, salt, times).toString();
        user.setSalt(salt);
        user.setPassword(encodedPassword);
        user.setCreateTime(new Date());
        userService.addUser(user);

        return ResponseFactory.buildSuccessResponse(user);
    }

    @CrossOrigin
    @PostMapping("/login")
    @ResponseBody
    public Response login(@RequestBody User requestUser) {
        String username = requestUser.getUsername();
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, requestUser.getPassword());
        try {
            subject.login(usernamePasswordToken);
            return ResponseFactory.buildSuccessResponse(username);
        } catch (AuthenticationException e) {
            String message = "账号密码错误";
            return ResponseFactory.buildFailResponse(message);
        }
    }

    @CrossOrigin
    @GetMapping("/logout")
    @ResponseBody
    public Response logout(HttpSession session) {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return ResponseFactory.buildSuccessResponse(null);
    }
}

package person.xu.vulEnv;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppController {
    @GetMapping("/")
    public String info() {
        return "Hello admin";
    }

    @GetMapping("/info")
    public String infos() {
        return "Show user infomation";
    }

    @GetMapping("/info/{username}")
    public String info(@PathVariable String username) {
        return "Username: " + username + "\tAge: 18";
    }

    @GetMapping("/login")
    public String login(String user, String pass, boolean isRemember) {
        Subject subject = SecurityUtils.getSubject();
        try {
            UsernamePasswordToken token = new UsernamePasswordToken(user, pass);
            if (isRemember) {
                token.setRememberMe(true);
            }
            subject.login(token);
            return "login ok";
        } catch (Exception e) {
            return e.getMessage();
        }

    }
}

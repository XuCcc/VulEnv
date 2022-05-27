package person.xu.vulEnv;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.websocket.server.PathParam;

@Controller
public class WebController {
    @GetMapping("/")
    @ResponseBody
    public String index() {
        return "welcome";
    }

    @GetMapping("/admin/{name}")
    @ResponseBody
    public String admin(@PathVariable String name) {
        return "welcome " + name;
    }
}

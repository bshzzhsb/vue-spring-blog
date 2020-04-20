package self.hsb.blog.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author SipooHe
 * @date 2020/4/12 14:23
 */
@Controller
public class IndexShowController {

    @CrossOrigin
    @GetMapping("/{id}/{name}")
    @ResponseBody
    public String index(@PathVariable int id, @PathVariable String name) {
        System.out.println("---index---");
        return "index";
    }
}

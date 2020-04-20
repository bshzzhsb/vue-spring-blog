package self.hsb.blog.web.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import self.hsb.blog.po.Tag;
import self.hsb.blog.response.Response;
import self.hsb.blog.response.ResponseFactory;
import self.hsb.blog.service.TagService;

import java.util.List;

/**
 * @author SipooHe
 * @date 2020/4/18 14:44
 */
@Controller
@RequestMapping("/hsblog/admin")
public class TagController {

    @Autowired
    private TagService tagService;

    @CrossOrigin
    @GetMapping("/tag")
    @ResponseBody
    public Response getTags() {
        List<Tag> tags = tagService.listTag();
        return ResponseFactory.buildSuccessResponse(tags);
    }

}

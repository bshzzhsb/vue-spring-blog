package self.hsb.blog.web.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
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

    @CrossOrigin
    @PostMapping("/tag")
    @ResponseBody
    public Response saveTag(@RequestBody Tag tag) {
        Tag t;
        if (tag.getId() == null) {
            t = tagService.saveTag(tag);
        } else {
            t = tagService.updateTag(tag);
        }
        if (t == null) {
            return ResponseFactory.buildFailResponse("保存失败");
        } else {
            return ResponseFactory.buildSuccessResponse(null);
        }
    }

    @CrossOrigin
    @PostMapping("/tag/delete/{id}")
    @ResponseBody
    public Response deleteTag(@PathVariable Integer id) {
        tagService.deleteTag(id);
        return ResponseFactory.buildSuccessResponse(null);
    }

}

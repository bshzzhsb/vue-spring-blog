package self.hsb.blog.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import self.hsb.blog.po.Blog;
import self.hsb.blog.po.Tag;
import self.hsb.blog.response.Response;
import self.hsb.blog.response.ResponseFactory;
import self.hsb.blog.service.BlogService;
import self.hsb.blog.service.TagService;

import java.util.List;

/**
 * @author SipooHe
 * @date 2020/4/28 14:09
 */
@Controller
@RequestMapping("/hsblog")
public class TagShowController {

    @Autowired
    private TagService tagService;
    @Autowired
    private BlogService blogService;

    @CrossOrigin
    @GetMapping("/tag")
    @ResponseBody
    public Response getTags() {
        List<Tag> tags = tagService.listTagTop();
        return ResponseFactory.buildSuccessResponse(tags);
    }

    @CrossOrigin
    @GetMapping("/tag/{tagId}")
    @ResponseBody
    public Response getTagBlog(@PageableDefault(size = 10, sort = {"createTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                      @PathVariable Integer tagId) {
        Page<Blog> blogPage = blogService.listBlog(tagId, pageable);
        return ResponseFactory.buildSuccessResponse(blogPage);
    }

}

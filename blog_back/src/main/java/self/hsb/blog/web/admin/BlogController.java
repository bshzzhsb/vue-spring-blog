package self.hsb.blog.web.admin;

import org.apache.shiro.SecurityUtils;
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
import self.hsb.blog.service.TypeService;
import self.hsb.blog.service.UserService;
import self.hsb.blog.vo.BlogQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * @author SipooHe
 * @date 2020/4/17 12:38
 */
@Controller
@RequestMapping("/hsblog/admin")
public class BlogController {

    @Autowired
    private BlogService blogService;
    @Autowired
    private UserService userService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;

    @CrossOrigin
    @GetMapping(value = {"/blog", "/blog/search"})
    @ResponseBody
    public Response getBlogs(@PageableDefault(size = 3, sort = {"createTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                             BlogQuery blogQuery) {
        Page<Blog> blogPage = blogService.listBlog(blogQuery, pageable);
        return ResponseFactory.buildSuccessResponse(blogPage);
    }

    @CrossOrigin
    @PostMapping("/blog/search")
    @ResponseBody
    public Response queryBlogs(@PageableDefault(size = 3, sort = {"createTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                               @RequestBody BlogQuery blogQuery) {
        Page<Blog> blogPage = blogService.listBlog(blogQuery, pageable);
        return ResponseFactory.buildSuccessResponse(blogPage);
    }

    @CrossOrigin
    @PostMapping("/blog")
    @ResponseBody
    public Response saveBlog(@RequestBody Blog blog) {
        String username = SecurityUtils.getSubject().getPrincipal().toString();
        blog.setUser(userService.getUserByUsername(username));
        blog.setType(typeService.getTypeById(blog.getType().getId()));
        List<Tag> tags = new ArrayList<>();
        for (Tag tag : blog.getTags()) {
            tags.add(tagService.getTagById(tag.getId()));
        }
        blog.setTags(tags);
        Blog b;
        if (blog.getId() == null) {
            b = blogService.saveBlog(blog);
        } else {
            b = blogService.updateBlog(blog);
        }
        if (b == null) {
            return ResponseFactory.buildFailResponse("保存失败");
        }
        return ResponseFactory.buildSuccessResponse(null);
    }

    @CrossOrigin
    @GetMapping("/blog/{id}")
    @ResponseBody
    public Response getBlog(@PathVariable Integer id) {
        Blog blog = blogService.getBlogById(id);
        return ResponseFactory.buildSuccessResponse(blog);
    }

    @CrossOrigin
    @PostMapping("/blog/delete/{id}")
    @ResponseBody
    public Response deleteBlog(@PathVariable Integer id) {
        blogService.deleteBlog(id);
        return ResponseFactory.buildSuccessResponse(null);
    }

}

package self.hsb.blog.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import self.hsb.blog.po.Blog;
import self.hsb.blog.response.Response;
import self.hsb.blog.response.ResponseFactory;
import self.hsb.blog.service.BlogService;

import java.util.List;
import java.util.Map;

/**
 * @author SipooHe
 * @date 2020/4/26 21:15
 */
@Controller
@RequestMapping("/hsblog")
public class BlogShowController {

    @Autowired
    private BlogService blogService;

    @CrossOrigin
    @GetMapping("/blog")
    @ResponseBody
    public Response getBlogs(@PageableDefault(size = 10, sort = {"createTime"}, direction = Sort.Direction.DESC)Pageable pageable) {
        Page<Blog> blogPage = blogService.listBlog(pageable);
        return ResponseFactory.buildSuccessResponse(blogPage);
    }

    @CrossOrigin
    @GetMapping("/blog/{id}")
    @ResponseBody
    public Response getBlog(@PathVariable Integer id) {
        blogService.updateView(id);
        Blog blog = blogService.getBlogById(id);
        return ResponseFactory.buildSuccessResponse(blog);
    }

    @CrossOrigin
    @GetMapping("/blog/count")
    @ResponseBody
    public Response countBlog() {
        Long count = blogService.countBlog();
        return ResponseFactory.buildSuccessResponse(count);
    }

    @CrossOrigin
    @GetMapping("/blog/total")
    @ResponseBody
    public Response getBlogs() {
        Map<String, List<Blog>> map = blogService.archiveAll();
        return ResponseFactory.buildSuccessResponse(map);
    }

}

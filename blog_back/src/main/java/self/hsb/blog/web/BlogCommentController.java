package self.hsb.blog.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import self.hsb.blog.po.BlogComment;
import self.hsb.blog.response.Response;
import self.hsb.blog.response.ResponseFactory;
import self.hsb.blog.service.BlogCommentService;
import self.hsb.blog.service.BlogService;
import self.hsb.blog.util.GetGravatar;

import java.util.List;

/**
 * @author SipooHe
 * @date 2020/4/25 13:23
 */
@Controller
@RequestMapping("/hsblog")
public class BlogCommentController {

    @Autowired
    private BlogCommentService blogCommentService;
    @Autowired
    private BlogService blogService;

    @CrossOrigin
    @GetMapping("/blog/{blogId}/comments")
    @ResponseBody
    public Response getBlogComments(@PathVariable Integer blogId) {
        List<BlogComment> blogComments = blogCommentService.listBlogCommentByBlogId(blogId);
        return ResponseFactory.buildSuccessResponse(blogComments);
    }

    @CrossOrigin
    @PostMapping("/blog/comments")
    @ResponseBody
    public Response saveBlogComment(@RequestBody BlogComment blogComment) {
        blogComment.setAvatar(GetGravatar.getGravatar(blogComment.getEmail()));
        Integer blogId = blogComment.getBlog().getId();
        blogComment.setBlog(blogService.getBlogById(blogId));
        blogCommentService.saveBlogComment(blogComment);
        return ResponseFactory.buildSuccessResponse(null);
    }

}

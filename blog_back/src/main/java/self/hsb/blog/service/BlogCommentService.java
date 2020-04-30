package self.hsb.blog.service;

import self.hsb.blog.po.BlogComment;

import java.util.List;

/**
 * @author SipooHe
 * @date 2020/4/25 13:09
 */
public interface BlogCommentService {

    List<BlogComment> listBlogCommentByBlogId(Integer blogId);

    BlogComment saveBlogComment(BlogComment blogComment);

}

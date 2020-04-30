package self.hsb.blog.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import self.hsb.blog.dao.BlogCommentDao;
import self.hsb.blog.po.BlogComment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author SipooHe
 * @date 2020/4/25 13:09
 */
@Service
public class BlogCommentServiceImpl implements BlogCommentService {

    @Autowired
    private BlogCommentDao blogCommentDao;

    @Override
    public List<BlogComment> listBlogCommentByBlogId(Integer blogId) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createTime");
        List<BlogComment> blogComments = blogCommentDao.findByBlogIdAndParentCommentNull(blogId, sort);
        return eachComment(blogComments);
    }

    @Override
    public BlogComment saveBlogComment(BlogComment blogComment) {
        Integer parentId = blogComment.getParentComment().getId();
        if (parentId != -1) {
            blogComment.setParentComment(blogCommentDao.getOne(parentId));
        } else {
            blogComment.setParentComment(null);
        }
        blogComment.setCreateTime(new Date());
        return blogCommentDao.save(blogComment);
    }

    private List<BlogComment> eachComment(List<BlogComment> blogComments) {
        List<BlogComment> blogCommentsView = new ArrayList<>();
        for (BlogComment blogComment : blogComments) {
            BlogComment bc = new BlogComment();
            BeanUtils.copyProperties(blogComment, bc);
            blogCommentsView.add(bc);
        }
        combineChildren(blogCommentsView);
        return blogCommentsView;
    }

    private void combineChildren(List<BlogComment> blogCommentsView) {
        for (BlogComment blogComment : blogCommentsView) {
            List<BlogComment> replys1 = blogComment.getReplyComments();
            for (BlogComment reply1 : replys1) {
                recursively(reply1);
            }
            blogComment.setReplyComments(tempReplys);
            tempReplys = new ArrayList<>();
        }
    }

    private List<BlogComment> tempReplys = new ArrayList<>();

    private void recursively(BlogComment blogComment) {
        blogComment.getParentComment().setReplyComments(null);
        tempReplys.add(blogComment);
        if (blogComment.getReplyComments().size() > 0) {
            List<BlogComment> replys = blogComment.getReplyComments();
            for (BlogComment reply : replys) {
                recursively(reply);
            }
        }
    }
}

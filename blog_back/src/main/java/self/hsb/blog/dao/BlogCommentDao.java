package self.hsb.blog.dao;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import self.hsb.blog.po.BlogComment;

import java.util.List;

/**
 * @author SipooHe
 * @date 2020/4/25 13:05
 */
public interface BlogCommentDao extends JpaRepository<BlogComment, Integer> {

    List<BlogComment> findByBlogIdAndParentCommentNull(Integer blogId, Sort sort);

}

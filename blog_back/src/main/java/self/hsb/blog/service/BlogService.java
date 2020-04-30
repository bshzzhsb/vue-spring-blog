package self.hsb.blog.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import self.hsb.blog.po.Blog;
import self.hsb.blog.vo.BlogQuery;

import java.util.List;
import java.util.Map;

/**
 * @author SipooHe
 * @date 2020/4/17 11:25
 */
public interface BlogService {

    Page<Blog> listBlog(Pageable pageable);

    Page<Blog> listBlog(BlogQuery blogQuery, Pageable pageable);

    Page<Blog> listBlog(String query, Pageable pageable);

    Page<Blog> listBlog(Integer tagId, Pageable pageable);

    Blog saveBlog(Blog blog);

    Blog updateBlog(Blog blog);

    Blog getBlogById(Integer id);

    void updateView(Integer id);

    void deleteBlog(Integer id);

    Long countBlog();

    Map<String, List<Blog>> archiveAll();
}

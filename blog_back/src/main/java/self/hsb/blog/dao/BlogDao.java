package self.hsb.blog.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import self.hsb.blog.po.Blog;

/**
 * @author SipooHe
 * @date 2020/4/17 11:22
 */
public interface BlogDao extends JpaRepository<Blog, Integer>, JpaSpecificationExecutor<Blog> {

    @Query("select b from Blog b where b.title like ?1 or b.contentHtml like ?1")
    Page<Blog> findByQuery(String query, Pageable pageable);

}

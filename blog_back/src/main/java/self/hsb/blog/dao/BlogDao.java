package self.hsb.blog.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import self.hsb.blog.po.Blog;

import java.util.List;

/**
 * @author SipooHe
 * @date 2020/4/17 11:22
 */
public interface BlogDao extends JpaRepository<Blog, Integer>, JpaSpecificationExecutor<Blog> {

    @Query("select b from Blog b where b.title like ?1 or b.contentHtml like ?1")
    Page<Blog> findByQuery(String query, Pageable pageable);

    @Transactional
    @Modifying
    @Query("update Blog b set b.views = b.views+1 where b.id = ?1")
    void updateView(Integer id);

    @Query("select function('date_format', b.createTime, '%Y') as year from Blog b group by function('date_format', b.createTime, '%Y') order by year desc")
    List<String> findGroupYear();

    @Query("select b from Blog b where function('date_format', b.createTime, '%Y') = ?1 order by b.createTime desc ")
    List<Blog> findByYear(String year);
}

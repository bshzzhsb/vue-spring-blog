package self.hsb.blog.dao;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import self.hsb.blog.po.Tag;

import java.util.List;

/**
 * @author SipooHe
 * @date 2020/4/18 14:41
 */
public interface TagDao extends JpaRepository<Tag, Integer> {

    Tag findByName(String name);

    @Query("select t from Tag t")
    List<Tag> findTop(Pageable pageable);
}

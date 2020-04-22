package self.hsb.blog.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import self.hsb.blog.po.Tag;

/**
 * @author SipooHe
 * @date 2020/4/18 14:41
 */
public interface TagDao extends JpaRepository<Tag, Integer> {

    Tag findByName(String name);
}

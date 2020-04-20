package self.hsb.blog.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import self.hsb.blog.po.Type;

/**
 * @author SipooHe
 * @date 2020/4/17 22:30
 */
public interface TypeDao extends JpaRepository<Type, Integer> {

    Type findByName(String name);
}

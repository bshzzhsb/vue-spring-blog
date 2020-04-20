package self.hsb.blog.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import self.hsb.blog.po.User;

/**
 * @author SipooHe
 * @date 2020/4/12 16:11
 */
public interface UserDao extends JpaRepository<User, Integer> {

    User findByUsername(String username);
}

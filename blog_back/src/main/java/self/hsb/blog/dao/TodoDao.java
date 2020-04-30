package self.hsb.blog.dao;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import self.hsb.blog.po.Todo;

import java.util.List;

/**
 * @author SipooHe
 * @date 2020/4/24 19:55
 */
public interface TodoDao extends JpaRepository<Todo, Integer> {

    List<Todo> findByFinishedTrue(Sort sort);

    List<Todo> findByFinishedFalse(Sort sort);

}

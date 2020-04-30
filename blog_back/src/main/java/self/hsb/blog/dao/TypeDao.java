package self.hsb.blog.dao;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import self.hsb.blog.po.Type;

import java.util.List;

/**
 * @author SipooHe
 * @date 2020/4/17 22:30
 */
public interface TypeDao extends JpaRepository<Type, Integer> {

    Type findByName(String name);

    @Query("select t from Type t")
    List<Type> findTop(Pageable pageable);
}

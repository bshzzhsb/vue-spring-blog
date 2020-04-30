package self.hsb.blog.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import self.hsb.blog.po.Message;

/**
 * @author SipooHe
 * @date 2020/4/25 12:20
 */
public interface MessageDao extends JpaRepository<Message, Integer> {

    Page<Message> findByParentMessageNull(Pageable pageable);
}

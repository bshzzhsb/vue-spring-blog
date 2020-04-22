package self.hsb.blog.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import self.hsb.blog.po.Mood;

/**
 * @author SipooHe
 * @date 2020/4/22 17:05
 */
public interface MoodDao extends JpaRepository<Mood, Integer> {

}

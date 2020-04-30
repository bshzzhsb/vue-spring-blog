package self.hsb.blog.dao;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import self.hsb.blog.po.MoodComment;

import java.util.List;

/**
 * @author SipooHe
 * @date 2020/4/23 20:17
 */
public interface MoodCommentDao extends JpaRepository<MoodComment, Integer> {

    List<MoodComment> findByMoodIdAndParentMoodCommentNull(Integer moodId, Sort sort);

}

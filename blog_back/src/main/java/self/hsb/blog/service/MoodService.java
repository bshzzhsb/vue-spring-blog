package self.hsb.blog.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import self.hsb.blog.po.Mood;

/**
 * @author SipooHe
 * @date 2020/4/22 17:06
 */
public interface MoodService {

    Mood getMoodById(Integer id);

    Long countMood();

    Page<Mood> listMood(Pageable pageable);

    Mood saveMood(Mood mood);

    Mood updateMood(Mood mood);

    void deleteMood(Integer id);

}

package self.hsb.blog.service;

import self.hsb.blog.po.MoodComment;

import java.util.List;

/**
 * @author SipooHe
 * @date 2020/4/23 20:19
 */
public interface MoodCommentService {

    List<MoodComment> listMoodCommentByMoodId(Integer moodId);

    MoodComment saveMoodComment(MoodComment moodComment);

}

package self.hsb.blog.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import self.hsb.blog.dao.MoodCommentDao;
import self.hsb.blog.po.MoodComment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author SipooHe
 * @date 2020/4/23 20:19
 */
@Service
public class MoodCommentServiceImpl implements MoodCommentService {

    @Autowired
    private MoodCommentDao moodCommentDao;

    @Override
    public List<MoodComment> listMoodCommentByMoodId(Integer moodId) {
        Sort sort = Sort.by(Sort.Direction.ASC, "createTime");
        List<MoodComment> moodComments = moodCommentDao.findByMoodIdAndParentMoodCommentNull(moodId, sort);
        return eachComment(moodComments);
    }

    @Transactional
    @Override
    public MoodComment saveMoodComment(MoodComment moodComment) {
        Integer parentId = moodComment.getParentMoodComment().getId();
        if (parentId != -1) {
            moodComment.setParentMoodComment(moodCommentDao.getOne(parentId));
        } else {
            moodComment.setParentMoodComment(null);
        }
        moodComment.setCreateTime(new Date());
        return moodCommentDao.save(moodComment);
    }

    private List<MoodComment> eachComment(List<MoodComment> moodComments) {
        List<MoodComment> moodCommentsView = new ArrayList<>();
        for (MoodComment moodComment : moodComments) {
            MoodComment mc = new MoodComment();
            BeanUtils.copyProperties(moodComment, mc);
            moodCommentsView.add(mc);
        }
        combineChildren(moodCommentsView);
        return moodCommentsView;
    }

    private void combineChildren(List<MoodComment> moodCommentsView) {
        for (MoodComment moodComment : moodCommentsView) {
            List<MoodComment> replys1 = moodComment.getReplyMoodComments();
            for (MoodComment reply1 : replys1) {
                recursively(reply1);
            }
            moodComment.setReplyMoodComments(tempReplys);
            tempReplys = new ArrayList<>();
        }
    }

    private List<MoodComment> tempReplys = new ArrayList<>();

    private void recursively(MoodComment moodComment) {
        moodComment.getParentMoodComment().setReplyMoodComments(null);
        tempReplys.add(moodComment);
        if (moodComment.getReplyMoodComments().size() > 0) {
            List<MoodComment> replys = moodComment.getReplyMoodComments();
            for (MoodComment reply : replys) {
                recursively(reply);
            }
        }
    }
}

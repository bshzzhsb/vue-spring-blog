package self.hsb.blog.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import self.hsb.blog.dao.MoodDao;
import self.hsb.blog.po.Mood;
import self.hsb.blog.util.MyBeanUtils;

import java.util.Date;

/**
 * @author SipooHe
 * @date 2020/4/22 17:06
 */
@Service
public class MoodServiceImpl implements MoodService {

    @Autowired
    private MoodDao moodDao;


    @Override
    public Mood getMoodById(Integer id) {
        return moodDao.getOne(id);
    }

    @Override
    public Long countMood() {
        return moodDao.count();
    }

    @Override
    public Page<Mood> listMood(Pageable pageable) {
        return moodDao.findAll(pageable);
    }

    @Transactional
    @Override
    public Mood saveMood(Mood mood) {
        mood.setCreateTime(new Date());
        mood.setUpdateTime(new Date());
        mood.setLikes(0);
        return moodDao.save(mood);
    }

    @Transactional
    @Override
    public Mood updateMood(Mood mood) {
        Mood m = moodDao.getOne(mood.getId());
        BeanUtils.copyProperties(mood, m, MyBeanUtils.getNullPropertyNames(mood));
        m.setUpdateTime(new Date());
        return moodDao.save(m);
    }

    @Override
    public void deleteMood(Integer id) {
        moodDao.deleteById(id);
    }
}

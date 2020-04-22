package self.hsb.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import self.hsb.blog.dao.TagDao;
import self.hsb.blog.po.Tag;

import java.util.List;

/**
 * @author SipooHe
 * @date 2020/4/18 14:42
 */
@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagDao  tagDao;

    @Override
    public List<Tag> listTag() {
        return tagDao.findAll();
    }

    @Override
    public Tag getTagById(Integer id) {
        return tagDao.getOne(id);
    }

    @Transactional
    @Override
    public Tag saveTag(Tag tag) {
        if (tagDao.findByName(tag.getName()) == null) {
            return tagDao.save(tag);
        } else {
            return null;
        }
    }

    @Transactional
    @Override
    public Tag updateTag(Tag tag) {
        Tag t = tagDao.getOne(tag.getId());
        t.setName(tag.getName());
        return tagDao.save(t);
    }

    @Override
    public void deleteTag(Integer id) {
        try {
            tagDao.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

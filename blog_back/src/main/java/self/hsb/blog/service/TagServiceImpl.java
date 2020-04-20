package self.hsb.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
}

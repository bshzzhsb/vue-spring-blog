package self.hsb.blog.service;

import self.hsb.blog.po.Tag;

import java.util.List;

/**
 * @author SipooHe
 * @date 2020/4/18 14:42
 */
public interface TagService {

    List<Tag> listTag();

    Tag getTagById(Integer id);
}

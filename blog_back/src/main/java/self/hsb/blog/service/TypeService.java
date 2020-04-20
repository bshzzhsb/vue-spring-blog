package self.hsb.blog.service;

import self.hsb.blog.po.Type;

import java.util.List;

/**
 * @author SipooHe
 * @date 2020/4/17 22:31
 */
public interface TypeService {

    List<Type> listType();

    Type getTypeById(Integer id);

    Type saveType(Type type);

    Type updateType(Type type);

    void deleteType(Integer id);
}

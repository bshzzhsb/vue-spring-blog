package self.hsb.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import self.hsb.blog.dao.TypeDao;
import self.hsb.blog.po.Type;

import java.util.List;

/**
 * @author SipooHe
 * @date 2020/4/17 22:31
 */
@Service
public class TypeServiceImpl implements TypeService {

    @Autowired
    private TypeDao typeDao;

    @Override
    public List<Type> listType() {
        return typeDao.findAll();
    }

    @Override
    public Type getTypeById(Integer id) {
        return typeDao.getOne(id);
    }

    @Transactional
    @Override
    public Type saveType(Type type) {
        if (typeDao.findByName(type.getName()) == null) {
            return typeDao.save(type);
        } else {
            return null;
        }

    }

    @Transactional
    @Override
    public Type updateType(Type type) {
        Type t = typeDao.getOne(type.getId());
        t.setName(type.getName());
        return typeDao.save(t);
    }

    @Override
    public void deleteType(Integer id) {
        try {
            typeDao.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

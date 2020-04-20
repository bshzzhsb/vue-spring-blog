package self.hsb.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import self.hsb.blog.dao.UserDao;
import self.hsb.blog.po.User;

/**
 * @author SipooHe
 * @date 2020/4/12 16:56
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    @Override
    public void addUser(User user) {
        userDao.save(user);
    }

    @Override
    public boolean checkUser(String username) {
        return false;
    }

    @Override
    public User getUserByUsername(String username) {
        return userDao.findByUsername(username);
    }
}

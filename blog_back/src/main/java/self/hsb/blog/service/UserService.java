package self.hsb.blog.service;

import self.hsb.blog.po.User;

/**
 * @author SipooHe
 * @date 2020/4/12 16:23
 */
public interface UserService {

    void addUser(User user);

    boolean checkUser(String username);

    User getUserByUsername(String username);
}

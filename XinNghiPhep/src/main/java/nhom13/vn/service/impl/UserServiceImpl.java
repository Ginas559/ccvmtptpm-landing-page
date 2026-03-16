package nhom13.vn.service.impl;

import nhom13.vn.dao.IUserDao;
import nhom13.vn.dao.impl.UserDaoImpl;
import nhom13.vn.service.IUserService;
import nhom13.vn.entity.User;

public class UserServiceImpl implements IUserService {

    IUserDao userDao = new UserDaoImpl();

    @Override
    public User login(String username, String password) {

        return userDao.findByUsernameAndPassword(username, password);

    }
    
    @Override
    public int register(String username, String password, String email) {

        if (userDao.checkExistUsername(username)) {
            return 1;
        }

        if (userDao.checkExistEmail(email)) {
            return 2;
        }

        User user = new User();

        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);

        userDao.insert(user);

        return 0;
    }
    
    @Override
    public User findByEmail(String email) {

        return userDao.findByEmail(email);

    }

    @Override
    public void update(User user) {

        userDao.update(user);

    }
}
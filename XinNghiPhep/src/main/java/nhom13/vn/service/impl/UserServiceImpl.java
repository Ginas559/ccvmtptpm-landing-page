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
}
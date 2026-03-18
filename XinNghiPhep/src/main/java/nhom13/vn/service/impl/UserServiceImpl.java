package nhom13.vn.service.impl;

import nhom13.vn.dao.IUserDao;
import nhom13.vn.dao.impl.UserDaoImpl;
import nhom13.vn.service.IUserService;
import nhom13.vn.entity.User;

import java.util.List;

public class UserServiceImpl implements IUserService {

    IUserDao userDao = new UserDaoImpl();

    @Override
    public User login(String username, String password) {

        return userDao.findByUsernameAndPassword(username, password);

    }

    @Override
    public int register(String username, String password, String email, String role) {

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
        user.setRole(role);
        user.setStatus(1);

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

    @Override
    public User findById(int id) {
        return userDao.findById(id);
    }

    @Override
    public List<User> findByRole(String role) {
        return userDao.findByRole(role);
    }

    @Override
    public List<User> findByRoles(List<String> roles) {
        return userDao.findByRoles(roles);
    }

    @Override
    public void insert(User user) {

        if (userDao.checkExistUsername(user.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        if (userDao.checkExistEmail(user.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        userDao.insert(user);
    }
}
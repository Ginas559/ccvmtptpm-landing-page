package nhom13.vn.dao;

import nhom13.vn.entity.User;

public interface IUserDao {
    User findByUsernameAndPassword(String username, String password);
    
    void insert(User user);

    boolean checkExistUsername(String username);

    boolean checkExistEmail(String email);
    
    User findByEmail(String email);
    
    void update(User user);
}
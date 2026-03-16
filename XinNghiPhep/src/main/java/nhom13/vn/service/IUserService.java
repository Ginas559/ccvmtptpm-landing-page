package nhom13.vn.service;

import nhom13.vn.entity.User;

public interface IUserService {

    User login(String username, String password);
    
    int register(String username, String password, String email);
    
    User findByEmail(String email);

    void update(User user);
}
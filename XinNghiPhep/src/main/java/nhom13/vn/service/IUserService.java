package nhom13.vn.service;

import nhom13.vn.entity.User;

public interface IUserService {

    User login(String username, String password);

}
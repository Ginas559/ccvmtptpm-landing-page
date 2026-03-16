package nhom13.vn.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import nhom13.vn.config.JPAConfig;
import nhom13.vn.dao.IUserDao;
import nhom13.vn.entity.User;
import nhom13.vn.*;

public class UserDaoImpl implements IUserDao {

    @Override
    public User findByUsernameAndPassword(String username, String password) {

        EntityManager em = JPAConfig.getEntityManager();
        User user = null;

        try {

            String jpql = "SELECT u FROM User u WHERE u.username = :username AND u.password = :password";

            TypedQuery<User> query = em.createQuery(jpql, User.class);

            query.setParameter("username", username);
            query.setParameter("password", password);

            user = query.getSingleResult();

        } catch (Exception e) {
            user = null;
        } finally {
            em.close();
        }

        return user;
    }
}
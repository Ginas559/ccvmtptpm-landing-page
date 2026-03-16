package nhom13.vn.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
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
    
    @Override
    public void insert(User user) {

        EntityManager em = JPAConfig.getEntityManager();
        EntityTransaction trans = em.getTransaction();

        try {
            trans.begin();

            em.persist(user);

            trans.commit();

        } catch (Exception e) {

            trans.rollback();
            throw e;

        } finally {

            em.close();
        }
    }
    
    @Override
    public boolean checkExistUsername(String username) {

        EntityManager em = JPAConfig.getEntityManager();

        try {

            String jpql = "SELECT COUNT(u) FROM User u WHERE u.username = :username";

            Long count = em.createQuery(jpql, Long.class)
                    .setParameter("username", username)
                    .getSingleResult();

            return count > 0;

        } finally {

            em.close();
        }
    }
    
    @Override
    public boolean checkExistEmail(String email) {

        EntityManager em = JPAConfig.getEntityManager();

        try {

            String jpql = "SELECT COUNT(u) FROM User u WHERE u.email = :email";

            Long count = em.createQuery(jpql, Long.class)
                    .setParameter("email", email)
                    .getSingleResult();

            return count > 0;

        } finally {

            em.close();
        }
    }
}
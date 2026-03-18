package nhom13.vn.dao.impl;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import nhom13.vn.config.JPAConfig;
import nhom13.vn.dao.ILeaveRequestDao;
import nhom13.vn.entity.LeaveRequest;

public class LeaveRequestDaoImpl implements ILeaveRequestDao {

    @Override
    public void insert(LeaveRequest lr) {

        EntityManager em = JPAConfig.getEntityManager();
        EntityTransaction trans = em.getTransaction();

        try {
            trans.begin();

            em.persist(lr);

            trans.commit();

        } catch (Exception e) {
            trans.rollback();
            e.printStackTrace();
        } finally {
            em.close(); // 🔥 BẮT BUỘC
        }
    }
    @Override
    public List<LeaveRequest> findByUser(int userId) {

        EntityManager em = JPAConfig.getEntityManager();

        try {
            TypedQuery<LeaveRequest> query =
                em.createQuery(
                    "SELECT lr FROM LeaveRequest lr WHERE lr.user.id = :uid ORDER BY lr.startDate DESC",
                    LeaveRequest.class
                );

            query.setParameter("uid", userId);

            return query.getResultList();

        } finally {
            em.close();
        }
    }
}
package nhom13.vn.dao.impl;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import nhom13.vn.config.JPAConfig;
import nhom13.vn.dao.ILeaveRequestDao;
import nhom13.vn.entity.LeaveRequest;

public class LeaveRequestDaoImpl implements ILeaveRequestDao {

    private static LeaveRequestDaoImpl instance;

    private LeaveRequestDaoImpl() {
    }

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

    @Override
    public List<LeaveRequest> findPendingByUser(int userId) {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            return em.createQuery(
                    "SELECT lr FROM LeaveRequest lr WHERE lr.user.id = :uid AND lr.status = 'PENDING' ORDER BY lr.startDate DESC",
                    LeaveRequest.class
            ).setParameter("uid", userId).getResultList();
        } finally {
            em.close();
        }
    }
    @Override
    public List<LeaveRequest> findAll() {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            return em.createQuery(
                "SELECT lr FROM LeaveRequest lr ORDER BY lr.startDate DESC",
                LeaveRequest.class
            ).getResultList();

        } finally {
            em.close();
        }
    }

    @Override
    public List<LeaveRequest> findPendingAll() {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            return em.createQuery(
                    "SELECT lr FROM LeaveRequest lr WHERE lr.status = 'PENDING' ORDER BY lr.startDate DESC",
                    LeaveRequest.class
            ).getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<LeaveRequest> findAllEmployees() {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            return em.createQuery(
                "SELECT lr FROM LeaveRequest lr WHERE lr.user.role = 'EMPLOYEE' ORDER BY lr.startDate DESC",
                LeaveRequest.class
            ).getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<LeaveRequest> findPendingEmployees() {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            return em.createQuery(
                    "SELECT lr FROM LeaveRequest lr WHERE lr.user.role = 'EMPLOYEE' AND lr.status = 'PENDING' ORDER BY lr.startDate DESC",
                    LeaveRequest.class
            ).getResultList();
        } finally {
            em.close();
        }
    }

    public static LeaveRequestDaoImpl getInstance() {
        if (instance == null) {
            instance = new LeaveRequestDaoImpl();
        }
        return instance;
    }
}
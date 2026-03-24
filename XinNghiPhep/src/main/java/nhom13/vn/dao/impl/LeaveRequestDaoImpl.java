package nhom13.vn.dao.impl;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.LockModeType;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import nhom13.vn.config.JPAConfig;
import nhom13.vn.dao.ILeaveRequestDao;
import nhom13.vn.entity.LeaveBalance;
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
    public LeaveRequest findByIdForUser(int leaveId, int userId) {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            return em.createQuery(
                    "SELECT lr FROM LeaveRequest lr WHERE lr.id = :id AND lr.user.id = :uid",
                    LeaveRequest.class
            )
            .setParameter("id", leaveId)
            .setParameter("uid", userId)
            .getSingleResult();
        } catch (NoResultException e) {
            return null;
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
    public LeaveRequest findById(int leaveId) {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            return em.find(LeaveRequest.class, leaveId);
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
    public LeaveRequest findByIdForManager(int leaveId) {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            return em.createQuery(
                    "SELECT lr FROM LeaveRequest lr WHERE lr.id = :id AND lr.user.role = 'EMPLOYEE'",
                    LeaveRequest.class
            )
            .setParameter("id", leaveId)
            .getSingleResult();
        } catch (NoResultException e) {
            return null;
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

    @Override
    public boolean approvePendingForManager(int leaveId) {
        return approveAndConsumeDays(leaveId, true);
    }

    @Override
    public boolean approvePendingForAdmin(int leaveId) {
        return approveAndConsumeDays(leaveId, false);
    }

    private boolean approveAndConsumeDays(int leaveId, boolean managerScopeOnlyEmployee) {
        EntityManager em = JPAConfig.getEntityManager();
        EntityTransaction trans = em.getTransaction();

        try {
            trans.begin();

            String jpql = "SELECT lr FROM LeaveRequest lr WHERE lr.id = :id AND lr.status = 'PENDING'";
            if (managerScopeOnlyEmployee) {
                jpql += " AND lr.user.role = 'EMPLOYEE'";
            }

            LeaveRequest leaveRequest;
            try {
                leaveRequest = em.createQuery(jpql, LeaveRequest.class)
                        .setParameter("id", leaveId)
                        .setLockMode(LockModeType.PESSIMISTIC_WRITE)
                        .getSingleResult();
            } catch (NoResultException e) {
                trans.rollback();
                return false;
            }

            LeaveBalance leaveBalance;
            try {
                leaveBalance = em.createQuery(
                                "SELECT lb FROM LeaveBalance lb WHERE lb.user.id = :userId",
                                LeaveBalance.class
                        )
                        .setParameter("userId", leaveRequest.getUser().getId())
                        .setLockMode(LockModeType.PESSIMISTIC_WRITE)
                        .getSingleResult();
            } catch (NoResultException e) {
                String role = leaveRequest.getUser().getRole();
                if (!"EMPLOYEE".equals(role) && !"MANAGER".equals(role)) {
                    trans.rollback();
                    return false;
                }

                leaveBalance = new LeaveBalance();
                leaveBalance.setUser(leaveRequest.getUser());
                leaveBalance.setTotalDays(12);
                leaveBalance.setUsedDays(0);
                leaveBalance.setRemainingDays(12);
                leaveBalance.setLastResetYear(LocalDate.now().getYear());
                em.persist(leaveBalance);
            }

            int requestedDays = calculateRequestedDays(leaveRequest);
            if (requestedDays <= 0 || leaveBalance.getRemainingDays() < requestedDays) {
                trans.rollback();
                return false;
            }

            leaveRequest.setStatus("APPROVED");
            leaveBalance.setUsedDays(leaveBalance.getUsedDays() + requestedDays);
            leaveBalance.setRemainingDays(leaveBalance.getRemainingDays() - requestedDays);

            trans.commit();
            return true;
        } catch (Exception e) {
            if (trans.isActive()) {
                trans.rollback();
            }
            return false;
        } finally {
            em.close();
        }
    }

    private int calculateRequestedDays(LeaveRequest leaveRequest) {
        LocalDate startDate = LocalDate.ofInstant(
                java.time.Instant.ofEpochMilli(leaveRequest.getStartDate().getTime()),
                ZoneId.systemDefault()
        );
        LocalDate endDate = LocalDate.ofInstant(
                java.time.Instant.ofEpochMilli(leaveRequest.getEndDate().getTime()),
                ZoneId.systemDefault()
        );

        if (endDate.isBefore(startDate)) {
            return 0;
        }

        return (int) (ChronoUnit.DAYS.between(startDate, endDate) + 1);
    }

    public static LeaveRequestDaoImpl getInstance() {
        if (instance == null) {
            instance = new LeaveRequestDaoImpl();
        }
        return instance;
    }
}
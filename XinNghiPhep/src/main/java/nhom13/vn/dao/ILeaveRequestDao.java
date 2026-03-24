package nhom13.vn.dao;

import java.util.List;

import nhom13.vn.entity.LeaveRequest;

public interface ILeaveRequestDao {
    void insert(LeaveRequest lr);
    	
    List<LeaveRequest> findByUser(int userId);

    List<LeaveRequest> findByUserAndStatus(int userId, String status);

    LeaveRequest findByIdForUser(int leaveId, int userId);

    List<LeaveRequest> findPendingByUser(int userId);
    
    List<LeaveRequest> findAll(); // 🔥 admin

    List<LeaveRequest> findAllByStatus(String status);

    LeaveRequest findById(int leaveId);

    List<LeaveRequest> findPendingAll();

    List<LeaveRequest> findAllEmployees(); // 🔥 manager

    List<LeaveRequest> findAllEmployeesByStatus(String status);

    LeaveRequest findByIdForManager(int leaveId);

    List<LeaveRequest> findPendingEmployees();

    boolean approvePendingForManager(int leaveId);

    boolean approvePendingForAdmin(int leaveId);

    boolean rejectPendingForManager(int leaveId);

    boolean rejectPendingForAdmin(int leaveId);
}
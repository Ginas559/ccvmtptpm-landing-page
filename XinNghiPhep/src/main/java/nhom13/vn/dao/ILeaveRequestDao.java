package nhom13.vn.dao;

import java.util.List;

import nhom13.vn.entity.LeaveRequest;

public interface ILeaveRequestDao {
    void insert(LeaveRequest lr);
    	
    List<LeaveRequest> findByUser(int userId);

    LeaveRequest findByIdForUser(int leaveId, int userId);

    List<LeaveRequest> findPendingByUser(int userId);
    
    List<LeaveRequest> findAll(); // 🔥 admin

    LeaveRequest findById(int leaveId);

    List<LeaveRequest> findPendingAll();

    List<LeaveRequest> findAllEmployees(); // 🔥 manager

    LeaveRequest findByIdForManager(int leaveId);

    List<LeaveRequest> findPendingEmployees();

    boolean approvePendingForManager(int leaveId);

    boolean approvePendingForAdmin(int leaveId);
}
package nhom13.vn.dao;

import java.util.List;

import nhom13.vn.entity.LeaveRequest;

public interface ILeaveRequestDao {
    void insert(LeaveRequest lr);
    	
    List<LeaveRequest> findByUser(int userId);

    List<LeaveRequest> findPendingByUser(int userId);
    
    List<LeaveRequest> findAll(); // 🔥 admin

    List<LeaveRequest> findPendingAll();

    List<LeaveRequest> findAllEmployees(); // 🔥 manager

    List<LeaveRequest> findPendingEmployees();
}
package nhom13.vn.dao;

import java.util.List;

import nhom13.vn.entity.LeaveRequest;

public interface ILeaveRequestDao {
    void insert(LeaveRequest lr);
    List<LeaveRequest> findByUser(int userId);
}
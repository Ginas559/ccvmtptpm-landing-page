package nhom13.vn.service;

import java.util.List;

import nhom13.vn.entity.LeaveRequest;

public interface ILeaveRequestService {
    void create(LeaveRequest lr);

    List<LeaveRequest> getByUser(int userId);

    List<LeaveRequest> getAll();

    List<LeaveRequest> getAllEmployees();
}
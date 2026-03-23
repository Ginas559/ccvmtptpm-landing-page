package nhom13.vn.service;

import java.util.List;

import nhom13.vn.entity.LeaveRequest;
import nhom13.vn.entity.User;

public interface ILeaveRequestService {
    void create(LeaveRequest lr);

    List<LeaveRequest> getByUser(int userId);

    List<LeaveRequest> getPendingByUser(int userId);

    List<LeaveRequest> getAll();

    List<LeaveRequest> getPendingAll();

    List<LeaveRequest> getAllEmployees();

    List<LeaveRequest> getPendingEmployees();

    LeaveRequest getDetailForViewer(int leaveId, User viewer);
}
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

    List<LeaveRequest> getByUserWithReview(int userId, String status);

    List<LeaveRequest> getAllForViewer(User viewer, String status);

    LeaveRequest getDetailForViewer(int leaveId, User viewer);

    boolean approveForViewer(int leaveId, User viewer);

    boolean approveForViewer(int leaveId, User viewer, String note);

    boolean rejectForViewer(int leaveId, User viewer);

    boolean rejectForViewer(int leaveId, User viewer, String note);
}
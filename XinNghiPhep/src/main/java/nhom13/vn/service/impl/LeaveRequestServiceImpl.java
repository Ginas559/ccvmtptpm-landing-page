package nhom13.vn.service.impl;

import nhom13.vn.dao.impl.LeaveRequestDaoImpl;
import nhom13.vn.service.ILeaveRequestService;
import nhom13.vn.dao.ILeaveRequestDao;
import nhom13.vn.entity.LeaveRequest;

public class LeaveRequestServiceImpl implements ILeaveRequestService {

    ILeaveRequestDao dao = new LeaveRequestDaoImpl();

    @Override
    public void create(LeaveRequest lr) {
        dao.insert(lr);
    }
}
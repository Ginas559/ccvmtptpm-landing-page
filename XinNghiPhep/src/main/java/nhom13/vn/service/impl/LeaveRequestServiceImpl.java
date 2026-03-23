package nhom13.vn.service.impl;
import java.util.List;
import nhom13.vn.dao.impl.LeaveRequestDaoImpl;
import nhom13.vn.service.ILeaveRequestService;
import nhom13.vn.dao.ILeaveRequestDao;
import nhom13.vn.entity.LeaveRequest;
//Singleton
public class LeaveRequestServiceImpl implements ILeaveRequestService {

	private static LeaveRequestServiceImpl instance;
    private ILeaveRequestDao dao;

    public LeaveRequestServiceImpl() {
        dao = LeaveRequestDaoImpl.getInstance();
    }

    public static LeaveRequestServiceImpl getInstance() {
        if (instance == null) {
            instance = new LeaveRequestServiceImpl();
        }
        return instance;
    }

    @Override
    public void create(LeaveRequest lr) {
        dao.insert(lr);
    }

    @Override
    public List<LeaveRequest> getByUser(int userId) {
        return dao.findByUser(userId);
    }

    @Override
    public List<LeaveRequest> getPendingByUser(int userId) {
        return dao.findPendingByUser(userId);
    }

    @Override
    public List<LeaveRequest> getAll() {
        return dao.findAll();
    }

    @Override
    public List<LeaveRequest> getPendingAll() {
        return dao.findPendingAll();
    }

    @Override
    public List<LeaveRequest> getAllEmployees() {
        return dao.findAllEmployees();
    }

    @Override
    public List<LeaveRequest> getPendingEmployees() {
        return dao.findPendingEmployees();
    }
}
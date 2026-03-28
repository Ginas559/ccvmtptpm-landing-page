package nhom13.vn.service.impl;

import java.util.List;

import nhom13.vn.dao.INotificationDao;
import nhom13.vn.dao.impl.NotificationDaoImpl;
import nhom13.vn.entity.Notification;
import nhom13.vn.entity.User;
import nhom13.vn.service.INotificationService;

public class NotificationServiceImpl implements INotificationService {

    private static NotificationServiceImpl instance;

    private final INotificationDao notificationDao;

    private NotificationServiceImpl() {
        this.notificationDao = NotificationDaoImpl.getInstance();
    }

    public static NotificationServiceImpl getInstance() {
        if (instance == null) {
            instance = new NotificationServiceImpl();
        }
        return instance;
    }

    @Override
    public List<Notification> getByViewer(User viewer) {
        if (viewer == null || viewer.getId() <= 0) {
            return List.of();
        }

        return notificationDao.findByReceiver(viewer.getId());
    }

    @Override
    public boolean markAsReadForViewer(int notificationId, User viewer) {
        if (viewer == null || viewer.getId() <= 0 || notificationId <= 0) {
            return false;
        }

        return notificationDao.markAsRead(notificationId, viewer.getId());
    }
}

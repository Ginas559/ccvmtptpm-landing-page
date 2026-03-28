package nhom13.vn.service;

import java.util.List;

import nhom13.vn.entity.Notification;
import nhom13.vn.entity.User;

public interface INotificationService {
    List<Notification> getByViewer(User viewer);

    boolean markAsReadForViewer(int notificationId, User viewer);
}

package nhom13.vn.config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
//factory để tạo entity manager, quản lý kết nối đến database
public class JPAConfig {

    private static final EntityManagerFactory factory =
            Persistence.createEntityManagerFactory("XinNghiPhepPU");

    public static EntityManager getEntityManager() {
        return factory.createEntityManager();
    }
}
package nhom13.vn.entity;

import java.io.Serializable;
import java.util.List;
import jakarta.persistence.*;

@Entity
@Table(name = "LeaveRequests")
public class LeaveRequest implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @Temporal(TemporalType.DATE) // Lưu trữ ngày [5, 12]
    private java.util.Date startDate;

    @Temporal(TemporalType.DATE)
    private java.util.Date endDate;

    @Column(columnDefinition = "nvarchar(MAX)")
    private String reason;

    private String status; // Chờ duyệt, Đã duyệt, Từ chối

    // Constructors, Getters, Setters
}
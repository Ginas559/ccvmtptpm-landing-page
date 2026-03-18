package nhom13.vn.entity;

import java.io.Serializable;
import java.util.List;
import jakarta.persistence.*;

@Entity
@Table(name = "LeaveApprovals")
public class LeaveApproval implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "requestId")
    private LeaveRequest leaveRequest;

    @ManyToOne
    @JoinColumn(name = "managerId")
    private User manager; // Người duyệt

    private String decision; // Phê duyệt hoặc Từ chối
    
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date approvalTime;

    @Column(columnDefinition = "nvarchar(500)")
    private String note;

    // Constructors, Getters, Setters
}
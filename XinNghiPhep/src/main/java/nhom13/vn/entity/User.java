package nhom13.vn.entity;

import java.io.Serializable;
import java.util.List;
import jakarta.persistence.*;

@Entity
@Table(name = "Users")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    private String email;

    @Column(columnDefinition = "nvarchar(200)")
    private String fullName;

    private String role; // Super Admin, Manager, Employee
    private int status; // Trạng thái tài khoản

    @ManyToOne
    @JoinColumn(name = "companyId") // Mỗi người dùng thuộc một công ty [5, 9]
    private Company company;

    @OneToMany(mappedBy = "user")
    private List<LeaveRequest> leaveRequests;

    // Constructors, Getters, Setters [10]
}
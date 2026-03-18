package nhom13.vn.entity;

import java.io.Serializable;
import java.util.List;
import jakarta.persistence.*;

@Entity
@Table(name = "LeaveBalances")
public class LeaveBalance implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    private int totalDays;
    private int usedDays;
    private int remainingDays;

    // Constructors, Getters, Setters
}
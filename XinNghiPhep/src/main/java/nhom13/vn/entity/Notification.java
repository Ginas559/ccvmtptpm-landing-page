package nhom13.vn.entity;
import java.io.Serializable;
import java.util.List;
import jakarta.persistence.*;
@Entity
@Table(name = "Notifications")
public class Notification implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "receiverId")
    private User receiver;

    @Column(columnDefinition = "nvarchar(MAX)")
    private String content;

    private boolean isRead;

    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date sentTime;

    // Constructors, Getters, Setters
}
package nhom13.vn.entity;

import java.io.Serializable;
import java.util.List;
import jakarta.persistence.*;

@Entity
@Table(name = "Companies")
public class Company implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(columnDefinition = "nvarchar(255) not null")
    private String name;

    @Column(columnDefinition = "nvarchar(500)")
    private String address;

    private String contactInfo;

    @OneToMany(mappedBy = "company")
    private List<User> users;

    // Constructors, Getters, Setters [4]
}
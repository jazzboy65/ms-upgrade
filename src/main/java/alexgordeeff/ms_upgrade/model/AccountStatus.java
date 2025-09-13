package alexgordeeff.ms_upgrade.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity(name = "account_status")
public class AccountStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;
}

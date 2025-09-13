package alexgordeeff.ms_upgrade.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity(name = "account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id")
    private AccountStatus statusId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id")
    private Client clientId;

    @Column(name = "account_type")
    private String accountType;

    @Column(name = "currency_code")
    private String currencyCode;
}

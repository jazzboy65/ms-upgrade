package alexgordeeff.ms_upgrade.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public enum ClientStatus {
    ACTIVE("Активный клиент"),
    BLOCKED("Заблокирован"),
    DELETED("Удален (soft delete)");

    private final String status;

    ClientStatus(String status) {
        this.status = status;
    }
}

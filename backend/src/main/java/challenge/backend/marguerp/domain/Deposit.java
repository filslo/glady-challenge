package challenge.backend.marguerp.domain;

import java.time.LocalDate;

import lombok.*;

/**
 * A Deposit.
 */
@Data
@AllArgsConstructor
public abstract class Deposit {

    private double amount;

    private LocalDate distributionDate;

    private LocalDate expirationDate;

    public boolean isExpired() {
        return LocalDate.now().isAfter(expirationDate);
    }
}

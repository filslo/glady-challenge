package challenge.backend.marguerp.domain;


import java.time.LocalDate;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * A GiftDeposit.
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString
public class GiftDeposit extends Deposit {

    protected static final int LIFESPAN_IN_DAYS = 365;

    public GiftDeposit(double amount,
                       LocalDate distributionDate
    ) {
        super(
                amount,
                distributionDate,
                distributionDate.plusDays(LIFESPAN_IN_DAYS)
        );
    }
}



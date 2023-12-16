package challenge.backend.marguerp.domain;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

import static java.time.temporal.TemporalAdjusters.lastDayOfMonth;


@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString
public class MealDeposit extends Deposit {
    public MealDeposit(double amount, LocalDate distributionDate) {
        super(
                amount,
                distributionDate,
                //determine next year, the last day of february
                distributionDate
                        .withYear(distributionDate.getYear() + 1)
                        .withMonth(2)
                        .with(lastDayOfMonth()
                )
        );
    }

}

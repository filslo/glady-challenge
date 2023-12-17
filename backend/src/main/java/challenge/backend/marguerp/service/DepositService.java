package challenge.backend.marguerp.service;

import challenge.backend.marguerp.domain.GiftDeposit;
import challenge.backend.marguerp.domain.MealDeposit;
import challenge.backend.marguerp.domain.User;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Service
@Getter
public class DepositService {

    public static final int EMPTY_BALANCE = 0;
    private final Map<String, User> users = new HashMap<>();

    public GiftDeposit distributeGiftDeposit(
            String username,
            double amount,
            LocalDate distributionDate
    ) {
        GiftDeposit deposit = new GiftDeposit(amount, distributionDate);

        User user = this.getUser(username);
        user.addDeposit(deposit);

        return deposit;
    }

    public MealDeposit distributeMealDeposit(
            String username,
            double amount,
            LocalDate distributionDate
    ) {
        MealDeposit deposit = new MealDeposit(amount, distributionDate);

        User user = this.getUser(username);
        user.addDeposit(deposit);

        return deposit;
    }

    /**
     * Find requested user object from username,
     * if not found, create new User Object and add it to the map for next use
     *
     * @param username the name of the user to look for
     *
     * @return the corresponding User object
     */
    private User getUser(String username) {
        return users.computeIfAbsent(username, key -> new User(username) );
    }
}

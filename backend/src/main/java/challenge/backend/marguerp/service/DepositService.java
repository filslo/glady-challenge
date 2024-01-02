package challenge.backend.marguerp.service;

import challenge.backend.marguerp.domain.Deposit;
import challenge.backend.marguerp.domain.GiftDeposit;
import challenge.backend.marguerp.domain.MealDeposit;
import challenge.backend.marguerp.domain.User;
import challenge.backend.marguerp.dto.DepositDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Service
@Getter
public class DepositService {

    public static final int EMPTY_BALANCE = 0;
    private final Map<String, User> users = new HashMap<>();

    @Autowired
    private ObjectMapper objectMapper;

    public DepositDTO distributeGiftDeposit(
            String username,
            double amount,
            LocalDate distributionDate
    ) {
        GiftDeposit deposit = new GiftDeposit(amount, distributionDate);

        User user = this.getUser(username);
        user.addDeposit(deposit);

        return toDTO(deposit, user);
    }


    public DepositDTO distributeMealDeposit(
            String username,
            double amount,
            LocalDate distributionDate
    ) {
        MealDeposit deposit = new MealDeposit(amount, distributionDate);

        User user = this.getUser(username);
        user.addDeposit(deposit);

        return toDTO(deposit, user);
    }

    private DepositDTO toDTO(Deposit deposit, User user) {
        DepositDTO depositDTO = objectMapper.convertValue(deposit, DepositDTO.class);

        // add missing username in Deposit
        depositDTO.setUsername(user.getUsername());

        return depositDTO;
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

    public double calculateBalance(String username) {

        //no user existence checks / if unknown, its balance will be null / zero
        User user = this.getUser(username);

        return user.getDeposits().stream().mapToDouble(
                deposit ->
                        deposit.isExpired()
                                ? EMPTY_BALANCE
                                : deposit.getAmount()
        ).sum();

    }


}

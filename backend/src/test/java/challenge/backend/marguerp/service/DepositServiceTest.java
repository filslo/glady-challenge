package challenge.backend.marguerp.service;

import challenge.backend.marguerp.domain.User;
import challenge.backend.marguerp.dto.DepositDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class DepositServiceTest {

    @Autowired
    private DepositService depositService;

    @Test
    void test_GiftDeposit_Is_Distributed_To_User() {

        // GIVEN
        String userName = "aUserName";
        double amount = 10.0;
        LocalDate distributionDate = LocalDate.now();

        assertThat(this.depositService.getUsers().get(userName)).isNull();

        // WHEN
        DepositDTO deposit = this.depositService.distributeGiftDeposit(userName, amount, distributionDate);

        // THEN
        assertThat(deposit.getUsername()).isEqualTo(userName);
        assertThat(deposit.getAmount()).isEqualTo(amount);
        assertThat(deposit.getDistributionDate()).isEqualTo(distributionDate);

        User actualUser = this.depositService.getUsers().get(userName);
        assertThat(actualUser).isNotNull();
        assertThat(actualUser.getDeposits()).hasSize(1);


    }

    @Test
    void test_MealDeposit_Is_Distributed_To_User() {

        // GIVEN
        String userName = "anotherUserName";
        double amount = 20.0;
        LocalDate distributionDate = LocalDate.now();
        assertThat(this.depositService.getUsers().get(userName)).isNull();

        // WHEN
        DepositDTO deposit = this.depositService.distributeMealDeposit(userName, amount, distributionDate);

        // THEN
        assertThat(deposit.getUsername()).isEqualTo(userName);
        assertThat(deposit.getAmount()).isEqualTo(amount);
        assertThat(deposit.getDistributionDate()).isEqualTo(distributionDate);

        User actualUser = this.depositService.getUsers().get(userName);
        assertThat(actualUser).isNotNull();
        assertThat(actualUser.getDeposits()).hasSize(1);

    }

    @Test
    void test_userBalance_when_user_is_unknown_should_return_zero() {
        // GIVEN
        String userName = "unknownUser";

        // WHEN
        double balance = this.depositService.calculateBalance(userName);

        // THEN
        assertThat(balance).isEqualTo(0.0);

    }

    @Test
    void test_userBalance_when_deposit_has_expired_deposit_should_nolonger_counted_in_user_balance() {
        // GIVEN
        String userName = "userWithExpiredDeposits";

        LocalDate distributionDate = LocalDate.of(2021, 1, 1);

        //Add expired deposits
        this.depositService.distributeMealDeposit(userName, 10, distributionDate);
        this.depositService.distributeGiftDeposit(userName, 50, distributionDate);


        // WHEN
        double balance = this.depositService.calculateBalance(userName);

        // THEN
        assertThat(balance).isEqualTo(0.0);

    }

    @Test
    void test_userBalance_when_user_has_non_expired_deposits_should_count_them_in_user_balance() {
        // GIVEN
        String userName = "userWithExpiredDeposits";

        LocalDate distributionDate = LocalDate.now();

        //Add non expired deposits

        double expectedBalance = 0;

        double rangeMin = 0.1;
        double rangeMax = 1000;

        Random r = new Random();
        int mealDepositCountToDistribute = 10;

        for (int i = 0; i < mealDepositCountToDistribute; i++) {
            double mealDepositAmount = rangeMin + (rangeMax - rangeMin) * r.nextDouble();
            this.depositService.distributeMealDeposit(userName, mealDepositAmount, distributionDate);
            expectedBalance += mealDepositAmount;
        }

        int giftDepositCountToDistribute = 5;
        for (int i = 0; i < giftDepositCountToDistribute; i++) {
            double giftDepositAmount = rangeMin + (rangeMax - rangeMin) * r.nextDouble();
            this.depositService.distributeGiftDeposit(userName, giftDepositAmount, distributionDate);
            expectedBalance += giftDepositAmount;
        }

        // WHEN
        double balance = this.depositService.calculateBalance(userName);

        // THEN
        assertThat(balance).isEqualTo(expectedBalance);

    }

    @Test
    void test_userBalance_when_deposits_have_been_distributed_to_multiple_users_The_user_balances_should_not_be_mixed_up() {
        // GIVEN

        LocalDate distributionDate = LocalDate.now();

        String userName = "user1";

        //Add deposits
        double giftMealAmount = 1.4;
        double giftDepositAmount = 50.093;

        this.depositService.distributeMealDeposit(userName, giftMealAmount, distributionDate);
        this.depositService.distributeGiftDeposit(userName, giftDepositAmount, distributionDate);

        double expectedBalance = giftMealAmount + giftDepositAmount;

        String userName2 = "user2";

        //Add deposits
        this.depositService.distributeMealDeposit(userName2, 2, distributionDate);
        this.depositService.distributeGiftDeposit(userName2, 3, distributionDate);

        // WHEN
        double balance = this.depositService.calculateBalance(userName);

        // THEN
        assertThat(balance).isEqualTo(expectedBalance);

    }
}

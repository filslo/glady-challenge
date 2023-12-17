package challenge.backend.marguerp.service;

import challenge.backend.marguerp.domain.GiftDeposit;
import challenge.backend.marguerp.domain.MealDeposit;
import challenge.backend.marguerp.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

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

        // WHEN
        GiftDeposit deposit = this.depositService.distributeGiftDeposit(userName, amount, distributionDate);

        // THEN
        assertThat(deposit.getAmount()).isEqualTo(amount);
        assertThat(deposit.getDistributionDate()).isEqualTo(distributionDate);

        User actualUser = this.depositService.getUsers().get(userName);
        assertThat(actualUser).isNotNull();
        assertThat(actualUser.getDeposits()).contains(deposit);

    }

    @Test
    void test_MealDeposit_Is_Distributed_To_User() {

        // GIVEN
        String userName = "anotherUserName";
        double amount = 20.0;
        LocalDate distributionDate = LocalDate.now();

        // WHEN
        MealDeposit deposit = this.depositService.distributeMealDeposit(userName, amount, distributionDate);

        // THEN
        assertThat(deposit.getAmount()).isEqualTo(amount);
        assertThat(deposit.getDistributionDate()).isEqualTo(distributionDate);

        User actualUser = this.depositService.getUsers().get(userName);
        assertThat(actualUser).isNotNull();
        assertThat(actualUser.getDeposits()).contains(deposit);
    }

}

package challenge.backend.marguerp.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class GiftDepositTest {

    @Test
    void test_GiftDeposit_IsExpired_When_DistributionDate_IsOlder_Than_Lifespan() {

        //GIVEN
        LocalDate distributionDate = LocalDate.now().minusDays(GiftDeposit.LIFESPAN_IN_DAYS +1);

        //WHEN
        Deposit deposit = new GiftDeposit(100, distributionDate);

        //THEN
        assertThat(deposit.isExpired()).isTrue();
    }

    @Test
    void test_GiftDeposit_Not_IsExpired_When_DistributionDate_IsEqualTo_Lifespan() {

        //GIVEN
        LocalDate distributionDate = LocalDate.now().minusDays(GiftDeposit.LIFESPAN_IN_DAYS);

        //WHEN
        Deposit deposit = new GiftDeposit(100, distributionDate);

        //THEN
        assertThat(deposit.isExpired()).isFalse();
    }

    @Test
    void test_GiftDeposit_IsExpired_When_DistributionDate_IsLowerThan_Lifespan() {

        //GIVEN
        LocalDate distributionDate = LocalDate.now();

        //WHEN
        Deposit deposit = new GiftDeposit(100, distributionDate);

        //THEN
        assertThat(deposit.isExpired()).isFalse();
    }

}
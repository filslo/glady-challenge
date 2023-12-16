package challenge.backend.marguerp.domain;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mockStatic;

class MealDepositTest {

    @Test
    void test_MealDeposit_IsNotExpired_When_today_date_is_before_the_lastday_of_february_distributionDate_nextYear() {

        //GIVEN
        LocalDate currentLocalDate = LocalDate.of(2023, 2, 13);
        try (MockedStatic<LocalDate> topDateTimeUtilMock = mockStatic(LocalDate.class, Mockito.CALLS_REAL_METHODS)) {
            topDateTimeUtilMock.when(LocalDate::now).thenReturn(currentLocalDate);

            LocalDate distributionDate = LocalDate.now().minusYears(1);

        //WHEN
            Deposit deposit = new MealDeposit(100, distributionDate);

        //THEN
            assertThat(deposit.isExpired()).isFalse();

        }
    }

    @Test
    void test_MealDeposit_IsExpired_When_today_date_is_after_the_lastday_of_february_distributionDate_nextYear() {

        //GIVEN
        LocalDate currentLocalDate = LocalDate.of(2023, 3, 13);
        try (MockedStatic<LocalDate> topDateTimeUtilMock = mockStatic(LocalDate.class, Mockito.CALLS_REAL_METHODS)) {
            topDateTimeUtilMock.when(LocalDate::now).thenReturn(currentLocalDate);

            LocalDate distributionDate = LocalDate.now().minusYears(1);

        //WHEN
            Deposit deposit = new MealDeposit(100, distributionDate);

        //THEN
            assertThat(deposit.isExpired()).isTrue();

        }
    }

}
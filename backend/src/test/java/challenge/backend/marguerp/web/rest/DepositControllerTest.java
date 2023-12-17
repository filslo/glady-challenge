package challenge.backend.marguerp.web.rest;

import challenge.backend.marguerp.dto.DepositDTO;
import challenge.backend.marguerp.service.DepositService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@AutoConfigureMockMvc
class DepositControllerTest {

    @MockBean
    private DepositService depositService;

    @InjectMocks
    private DepositController depositController;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void test_GiftDeposit_Is_Distributed_To_User_with_missing_distribution_should_fail_with_badrequest_httpstatus() throws Exception {

        //GIVEN
        String username = "username";
        double amount = 100;

        DepositDTO request = DepositDTO.builder()
                .amount(amount)
                .build();


        //WHEN
        mockMvc.perform(post("/users/{username}/gift-deposits", username)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))

                //THEN
                .andExpect(status().isBadRequest());

        // Ensure only the distributeGiftDeposit method as been called
        verify(depositService, never()).distributeGiftDeposit(any(), anyDouble(), any());
    }

    @Test
    void test_GiftDeposit_Is_Distributed_To_User_with_negative_amount_should_fail_with_badrequest_httpstatus() throws Exception {

        //GIVEN
        String username = "username";
        double amount = -1.0;

        DepositDTO request = DepositDTO.builder()
                .amount(amount)
                .build();


        //WHEN
        mockMvc.perform(post("/users/{username}/gift-deposits", username)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))

                //THEN
                .andExpect(status().isBadRequest());

        // Ensure only the distributeGiftDeposit method as been called
        verify(depositService, never()).distributeGiftDeposit(any(), anyDouble(), any());
    }

    @Test
    void test_GiftDeposit_Is_Distributed_To_User() throws Exception {

        //GIVEN
        String username = "username";
        double amount = 100;
        LocalDate distributionDate = LocalDate.of(2021, 6, 15);
        boolean isNotExpired = false;

        DepositDTO request = DepositDTO.builder()
                .amount(amount)
                .distributionDate(distributionDate)
                .build();

        when(depositService.distributeGiftDeposit(username, amount, distributionDate))
                .thenReturn(DepositDTO.builder()
                        .username(username)
                        .amount(amount)
                        .distributionDate(distributionDate)
                        .expired(isNotExpired)
                        .build()
                );

        //WHEN
        mockMvc.perform(post("/users/{username}/gift-deposits", username)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))

        //THEN
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value(username))
                .andExpect(jsonPath("$.amount").value(amount))
                .andExpect(jsonPath("$.distributionDate").value(formatLocalDate(distributionDate)))
                .andExpect(jsonPath("$.isExpired").value(isNotExpired));

        // Ensure only the distributeGiftDeposit method as been called
        verify(depositService, never()).distributeMealDeposit(any(), anyDouble(), any());
    }


    @Test
    void test_MealDeposit_Is_Distributed_To_User_with_missing_distribution_should_fail_with_badrequest_httpstatus() throws Exception {

        //GIVEN

        String username = "anotherUsername";
        double amount = 50;

        DepositDTO request = DepositDTO.builder()
                .amount(amount)
                .build();


        //WHEN
        mockMvc.perform(post("/users/{username}/meal-deposits", username)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))

                // THEN
                .andExpect(status().isBadRequest());

        // Ensure only the distributeMealDeposit method as been called
        verify(depositService, never()).distributeMealDeposit(any(), anyDouble(), any());

    }

    @Test
    void test_MealDeposit_Is_Distributed_To_User_with_missing_amount_should_fail_with_badrequest_httpstatus() throws Exception {

        //GIVEN

        String username = "anotherUsername";
        LocalDate distributionDate = LocalDate.of(2021, 1, 1);

        DepositDTO request = DepositDTO.builder()
                .distributionDate(distributionDate)
                .build();


        //WHEN
        mockMvc.perform(post("/users/{username}/meal-deposits", username)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))

                // THEN
                .andExpect(status().isBadRequest());

        // Ensure only the distributeMealDeposit method as been called
        verify(depositService, never()).distributeMealDeposit(any(), anyDouble(), any());

    }

    @Test
    void test_MealDeposit_Is_Distributed_To_User_with_negative_amount_should_fail_with_badrequest_httpstatus() throws Exception {

        //GIVEN

        String username = "anotherUsername";
        double amount = -50.0;

        DepositDTO request = DepositDTO.builder()
                .amount(amount)
                .build();


        //WHEN
        mockMvc.perform(post("/users/{username}/meal-deposits", username)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))

                // THEN
                .andExpect(status().isBadRequest());

        // Ensure only the distributeMealDeposit method as been called
        verify(depositService, never()).distributeMealDeposit(any(), anyDouble(), any());

    }

    @Test
    void test_MealDeposit_Is_Distributed_To_User() throws Exception {

        //GIVEN

        String username = "anotherUsername";
        double amount = 50;
        LocalDate distributionDate = LocalDate.of(2021, 1, 1);
        boolean isNotExpired = false;

        DepositDTO request = DepositDTO.builder()
                .amount(amount)
                .distributionDate(distributionDate)
                .build();

        when(depositService.distributeMealDeposit(username, amount, distributionDate))
                .thenReturn(DepositDTO.builder()
                        .username(username)
                        .amount(amount)
                        .distributionDate(distributionDate)
                        .expired(isNotExpired)
                        .build()
                );


        //WHEN
        mockMvc.perform(post("/users/{username}/meal-deposits", username)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))

       // THEN
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value(username))
                .andExpect(jsonPath("$.amount").value(amount))
                .andExpect(jsonPath("$.distributionDate").value(formatLocalDate(distributionDate)))
                .andExpect(jsonPath("$.isExpired").value(isNotExpired));

        // Ensure only the distributeMealDeposit method as been called
        verify(depositService, never()).distributeGiftDeposit(any(), anyDouble(), any());

    }

    private String formatLocalDate(LocalDate distributionDate) throws JsonProcessingException {
        // use jackson to convert the date in the common date formatting and remove starting and ending "
        return objectMapper.writeValueAsString(distributionDate).replaceAll("\"", "");
    }

}
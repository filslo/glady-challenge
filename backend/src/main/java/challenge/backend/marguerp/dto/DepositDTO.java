package challenge.backend.marguerp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.READ_ONLY;

@Data
@Builder
public class DepositDTO {

    @NotNull
    @Positive
    @JsonProperty(required = true)
    private Double amount;

    @NotNull
    @JsonProperty(required = true)
    private LocalDate distributionDate;

    // only to be provided as response of the API
    @JsonProperty(access = READ_ONLY)
    private String username;

    @JsonProperty(access = READ_ONLY, value="isExpired")
    private boolean expired;

}

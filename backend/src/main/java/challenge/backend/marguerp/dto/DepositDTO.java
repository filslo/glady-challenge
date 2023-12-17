package challenge.backend.marguerp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;

@Data
@Builder
public class DepositDTO {

    private double amount;

    private LocalDate distributionDate;

    // only to be provided as response of the API
    @JsonProperty(access = WRITE_ONLY)
    private String username;

    @JsonProperty(access = WRITE_ONLY)
    private boolean isExpired;

}

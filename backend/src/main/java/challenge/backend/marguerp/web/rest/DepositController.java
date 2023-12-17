package challenge.backend.marguerp.web.rest;

import challenge.backend.marguerp.dto.DepositDTO;

import challenge.backend.marguerp.service.DepositService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.*;
import io.swagger.v3.oas.annotations.responses.*;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@AllArgsConstructor
@Validated
public class DepositController {

    private DepositService depositService;

    @PostMapping("/users/{username}/gift-deposits")
    @ResponseStatus(CREATED)
    @Operation(summary = "Distribute a gift deposit for a user")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Deposit",
                    content = {
                            @Content(
                                    array = @ArraySchema(
                                            schema = @Schema(
                                                    implementation = DepositDTO.class
                                            )
                                    )
                            )
                    }
            )
    })
    public DepositDTO distributeGiftDeposit(
            @PathVariable String username,
            @RequestBody @Valid DepositDTO request
    ) {

        return depositService.distributeGiftDeposit(
                username,
                request.getAmount(),
                request.getDistributionDate()
        );
    }

    @PostMapping("/users/{username}/meal-deposits")
    @ResponseStatus(CREATED)
    @Operation(summary = "Distribute a Meal deposit for a user")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Deposit",
                    content = {
                            @Content(
                                    array = @ArraySchema(
                                            schema = @Schema(
                                                    implementation = DepositDTO.class
                                            )
                                    )
                            )
                    }
            )
    })
    public DepositDTO distributeMealDeposit(
            @PathVariable String username,
            @RequestBody @Valid DepositDTO request
    ) {
        return depositService.distributeMealDeposit(
                username,
                request.getAmount(),
                request.getDistributionDate()
        );
    }

}

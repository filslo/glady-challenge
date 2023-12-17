package challenge.backend.marguerp.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;

@Data
public class User {

    @NonNull
    @EqualsAndHashCode.Include
    private String username;

    private final List<Deposit> deposits = new ArrayList<>();

    public void addDeposit(Deposit deposit) {
        deposits.add(deposit);
    }

}

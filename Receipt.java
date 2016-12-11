

import java.util.List;

/**
 * An interface to print the Receipt
 */

public interface Receipt {
    void print(List<Reservation> reservations, User user);
}

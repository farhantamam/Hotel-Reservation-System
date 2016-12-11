
import java.util.List;

/**
 * Simple Receipt class implements the Receipt interface
 * 
 */

public class SimpleReceipt implements Receipt {

	@Override
	/** print method displays the receipt of the reservation in a simple format 
	 * with room number and cost
	 * @param reservations : list of Reservation
	 * @param user	: Object of User containing the user info
	 */
	public void print(List<Reservation> reservations, User user) {
		System.out.println(user.getUid() + " " + user.getUserName());
		for (Reservation entry : reservations) {
			if (!entry.isActive()) {
				continue;
			}
			System.out.println(entry.getRoom().getRoomId() + " $" + entry.totalCost());
		}
	}

}

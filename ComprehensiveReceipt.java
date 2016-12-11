
import java.util.List;
/**
 * Comprehensive Receipt class implements the Receipt interface
 * 
 */

public class ComprehensiveReceipt implements Receipt {

	@Override
	/** print method displays the receipt of the reservation in a detailed format 
	 * with user name, user id, cost, room number
	 * @param reservations : list of Reservation
	 * @param user	: Object of User containing the user info
	 */
	
	public void print(List<Reservation> reservations, User user) {
		System.out.println(user.getUid() + " " + user.getUserName());
		int cumCost = 0;
		for (Reservation entry : reservations) {
			int ecost = entry.totalCost();
			cumCost += ecost;
			System.out.println("room: " + entry.getRoom().getRoomId() + " $" + ecost);
		}
		System.out.println("total cost: " + cumCost);
	}

}

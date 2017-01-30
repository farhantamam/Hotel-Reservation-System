
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
	public String print(GuestUser user) {
		List<Reservation> reservations = user.getTransaction();
		String receipt;
		int cumCost = 0;
		receipt = "      RECIEPT\n\n";
		receipt+= String.format(" guest id: %d%n guest name: %s%n%n", user.getUid(), user.getUserName());
		receipt+= String.format(" Room #     Cost%n");
		for (Reservation entry : reservations) {
			int ecost = entry.totalCost();
			cumCost += ecost;
			receipt+= String.format(" %6d     $%d%n" , entry.getRoom().getRoomId(), ecost);
		}
		receipt+= ("\n total cost: $" + cumCost + " ");
		//System.out.println(receipt);
		return receipt;
}

}

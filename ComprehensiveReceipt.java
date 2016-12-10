
import java.util.List;

//concrete strategy
public class ComprehensiveReceipt implements Receipt {

	@Override
	public String print(GuestUser user) {
		List<Reservation> reservations = user.getAllReservations();
		String receipt;
		int cumCost = 0;
		receipt = "      RECIEPT\n";
		receipt+= String.format(" guest id: %d%n guest name: %s%n%n", user.getUid(), user.getUserName());
		receipt+= String.format(" Room #     Cost%n");
		for (Reservation entry : reservations) {
			int ecost = entry.totalCost();
			cumCost += ecost;
			receipt+= String.format(" %6d     $%d%n" , entry.getRoom().getRoomId(), ecost);
		}
		receipt+= ("\n total cost: $" + cumCost +" ");
		//System.out.println(receipt);
		return receipt;
	}

}

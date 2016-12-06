
import java.util.List;

public class ComprehensiveReceipt implements Receipt {

	@Override
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

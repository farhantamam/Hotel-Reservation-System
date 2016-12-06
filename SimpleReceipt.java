
import java.util.List;

public class SimpleReceipt implements Receipt {

	@Override
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

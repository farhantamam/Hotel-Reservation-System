
import java.util.Date;
import java.util.List;

public class Manager extends User {

	public Manager(int uid, String uname) {
		super(uid, uname);
	}

	public boolean isGuest() {
    	return false;
    }
	
	/**
	 * Return reserved rooms, and available for the given day
	 * @param today
	 * @param available
	 * @param reservedRooms
	 */
	public void getTodayDetails(Date day, List<Room> available, List<ReservedEntry> reservedRooms) {
		List<Room> rooms = Hotel.getInstance().getAllRooms();
		for (Room entry : rooms) {
			User user = entry.reserved(day);
			if (user == null) {
				available.add(entry);
			} else {
				reservedRooms.add(new ReservedEntry(entry, user));
			}
		}
	}
}


public class ReservedEntry {
    private final Room room;
    private final User user;
	public ReservedEntry(Room room, User user) {
		this.room = room;
		this.user = user;
	}
	public Room getRoom() {
		return room;
	}
	public User getUser() {
		return user;
	}
	@Override
	public String toString() {
		return " room: " + Integer.toString(room.getRoomId()) + "(" + (room.isEconomical() ? "Econ" : "Lux") + ")"
	                  + ", user: " + user;
	}

}

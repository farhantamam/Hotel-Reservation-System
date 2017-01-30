/**
 * This class is to keep track of the entry of reservation
 * @author
 *
 */
public class ReservedEntry {
    private final Room room;
    private final User user;
    
    /**
     * Constructor to initialize the data members from the parameters
     * @param room 
     * @param user
     */
	public ReservedEntry(Room room, User user) {
		this.room = room;
		this.user = user;
	}
	
	/**
	 * Accessor to return room
	 * @return room
	 */
	
	public Room getRoom() {
		return room;
	}
	
	/**
	 * Accessor to return user
	 * @return user
	 */
	
	public User getUser() {
		return user;
	}
	@Override
	/**
	 * Overridden toString method to convert user and room to string
	 * @return String
	 */
	public String toString() {
		return " room: " + Integer.toString(room.getRoomId()) + "(" + (room.isEconomical() ? "Econ" : "Lux") + ")"
	                  + ", user: " + user;
	}

}

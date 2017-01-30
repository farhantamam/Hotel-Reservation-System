
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
/**
 * This Reservation Class represents each reservation made at the hotel
 * this class contains the basic information on each individual reservation
 * duration of stay, user and room
 * @author 
 *
 */
public class Reservation {
    final Date from;
    final Date to;
    final User who;
    final Room room;
    boolean active;
    
    /**
     * This constructor initializes all the data members of the class
     *  from the parameters thats been passes in
     * @param from : first day of stay
     * @param to	: last dya of stay
     * @param who	: User info
     * @param room	: room info
     */
    
	public Reservation(Date from, Date to, User who, Room room) {

		this.from = from;
		this.to = to;
		this.who = who;
		this.room = room;
		this.active = false;
	}
	
	/**
	 * Accessor to to return the first day of stay
	 * @return from
	 */
	
	public Date getFrom() {
		return from;
	}
	
	/**
	 * Accessor to return the last day of stay
	 * @return to
	 */
	
	public Date getTo() {
		return to;
	}
	
	/**
	 * Accessor to return the user info
	 * @return who
	 */
	
	public User getWho() {
		return who;
	}
	
	/** Accessor to return the room info
	 * @return room
	 */
	
	public Room getRoom() {
		return room;
	}
	
	/**
	 * Method to check if the room is active
	 * @return active
	 */
	
	public boolean isActive() {
		return active;
	}
	
	/**
	 * Method to se the room as active from the parameter
	 * @param isActive
	 */
	
	public void setActive(boolean isActive) {
		this.active = isActive;
	}
	
	/**
	 * This method returns the total cost of the entire stay
	 * @return int
	 */
	
	int totalCost() {
		return room.cost() * (int)days();
	}
	
	/**
	 * This method returns the duration of the stay
	 * @return long
	 */
	
	private long days() {
		return Duration.of(to.getTime() - from.getTime(), ChronoUnit.MILLIS).toDays();
	}
	
	/**
	 * Release the room as the reservation is getting cancelled
	 */
	
	public void releaseRoom() {
		Calendar cal = Calendar.getInstance();
		cal.clear();
		cal.setTime(from);
		for (int j=0; j < days(); ++j) {
			room.release(cal.getTime());
			cal.add(Calendar.DATE, 1);;
		}
	}
}

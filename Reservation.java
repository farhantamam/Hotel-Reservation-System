
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;

public class Reservation implements java.io.Serializable {
	private static final long serialVersionUID = 94289568L;
	
	private final Date from;
    private final Date to;
    private final User who;
    private final Room room;

    public Reservation(Date from, Date to, User who, Room room) {
		//super();//who is the parent class of this class? object?; don't think this is doing anything
		this.from = from;
		this.to = to;
		this.who = who;
		this.room = room;
	}
	public Date getFrom() {
		return from;
	}
	public Date getTo() {
		return to;
	}
	public User getWho() {
		return who;
	}
	public Room getRoom() {
		return room;
	}
	
	int totalCost() {
		return room.cost() * (int)days();
	}
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

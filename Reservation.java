
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;

public class Reservation {
    final Date from;
    final Date to;
    final User who;
    final Room room;
    boolean active;
	public Reservation(Date from, Date to, User who, Room room) {
		//super();//who is the parent class of this class?; don't think this is doing anything
		this.from = from;
		this.to = to;
		this.who = who;
		this.room = room;
		this.active = false;
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
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean isActive) {
		this.active = isActive;
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

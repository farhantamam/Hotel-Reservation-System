
import java.util.Date;

public class DayReservedEntry {
    private final Date day;
	private final User user;
    public DayReservedEntry(Date day, User user) {
		this.day = day;
		this.user = user;
	}
	public Date getDay() {
		return day;
	}
	public User getUser() {
		return user;
	}
	public boolean matches(Date date) {
	    return date.equals(day);
	}
}

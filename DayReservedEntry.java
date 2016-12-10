
import java.util.Date;

public class DayReservedEntry implements java.io.Serializable {
	private static final long serialVersionUID = 94289568L;
	
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
	    if(date.getYear() == (this.getDay().getYear())) {
		    if(date.getMonth() == (this.getDay().getMonth())) {
			    if(date.getDate() == (this.getDay().getDate())) {
			    	return true;
			    }
		    }
	    }
		return false;
	}
}

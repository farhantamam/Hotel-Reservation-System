
import java.util.Date;

/**
 * This class represents the entry for each reservation 
 * It has the date and user who reserved it
 * @author 
 *
 */

public class DayReservedEntry {
	private final Date day;
	private final User user;

	/**
	 * Constructor to initialize the data memebers
	 * @param day : parameter to initialize the day
	 * @param user : parameter to initialize the user
	 */

	public DayReservedEntry(Date day, User user) {
		this.day = day;
		this.user = user;
	}

	/**
	 * Accessor to access day
	 * @return Date : This return day
	 */

	public Date getDay() {
		return day;
	}

	/**
	 * Accessor to access user
	 * @return User : this returns user
	 */

	public User getUser() {
		return user;
	}

	/**
	 * This method checks if the date passed matched with the date of the entry
	 * @param date : parameter Date that is needed to check
	 * @return boolean : return true if the same else false
	 */

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

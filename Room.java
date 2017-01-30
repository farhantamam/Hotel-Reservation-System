
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
/**
 * Room is an Abstract class that has all the information 
 * needed for the hotel room like room id, cost .
 * @author
 *
 */
public abstract class Room {
    private int roomId;
    private List<DayReservedEntry> dayReservations = new ArrayList<>();
    
    /**
     * Mutator to initialize the roomId 
     * @param roomId : parameter that hold the room id to initialize roomId
     */
    public Room(int roomId) {
        this.roomId = roomId;
	}
    //for testing
	public List<DayReservedEntry> getDayReservations() {
		return dayReservations;
	}
	
	/* abstract members that will be initialized and implemented the concrete class 
	 */
	
	abstract int cost();
    abstract boolean isEconomical();
    
    /**
     * Accessor to get the roomId
     * @return roomId
     */
	public int getRoomId() {
        return roomId;
	}
	

	/**
	 * This method makes the room available for the given date
	 * @param date
	 */
	
	public void release(Date date) {
		Iterator<DayReservedEntry> iter = dayReservations.iterator();
		while  (iter.hasNext()) {
			DayReservedEntry item = iter.next();
			if (item.matches(date)) {
			    iter.remove();
			    break;
			}
		}
	}

	/**
	 * This method checks for the reservation entry for the given date
	 * if found return the entry else return null
	 * @param date : parameter with the date that need to checked
	 * @return DayReservedEntry
	 */
	private DayReservedEntry get(Date date) {
		if (dayReservations.isEmpty()) {
			return null;
		}
		Iterator<DayReservedEntry> iter = dayReservations.iterator();
		while  (iter.hasNext()) {
			DayReservedEntry item = iter.next();
			if (item.matches(date)) {
				return item;
			}
		}
		return null;
	}
	
	
	/**
	 * This method check if room is available for the duration between dates
	 * @param from : parameter with the first date
	 * @param to	: parameter with the last date
	 * @return boolean 
	 */
	public boolean isAvailable(Date from, Date to) {
		Calendar cal = Calendar.getInstance();
		cal.clear();
		cal.setTime(from);
		Date w = cal.getTime();
		//System.out.println("inside isAvail, date: " + w.toString());
		while (!datesEqual(w, to)) {
			if (get(cal.getTime()) != null) {
				return false;
			}
			cal.add(Calendar.DATE, 1);
			w = cal.getTime();
		}
		if (get(to) != null) {
			return false;
		}
		return true;
	}
	// reserve the room for the all days [from, to]
	
	/**
	 * This method reserves the room for all the duration of the stay
	 * @param from: parameter with the first date of stay
	 * @param to : parameter with the last date of stay
	 * @param user : parameter with the user info
	 */
	public void reserved(Date from, Date to, User user) {
		Calendar cal = Calendar.getInstance();
		cal.clear();
		cal.setTime(from);
		List<DayReservedEntry> entries = new ArrayList<>();
		while (!datesEqual(cal.getTime(), to)) {
			entries.add(new DayReservedEntry(cal.getTime(), user));
			//System.out.printf("date reserved for room %d: %s%n", getRoomId(), entries.get(entries.size()-1).getDay().toString());
			cal.add(Calendar.DATE, 1);
		}
		entries.add(new DayReservedEntry(to, user));
		dayReservations.addAll(entries);
	}
	@Override
	/**
	 * This method converts the roomId to a string
	 * @return String : roomId in string type 
	 */
	public String toString() {
		return Integer.toString(roomId); // +"(" + (isEconomical() ? "Econ" : "Lux") + ")";
	}
	
	
	/* if today is reserved, determine who reserved?
	 * else return null for available indication
	 * ~ has some problem, doesn't seem to return correct answer all the time
	 */
	
	/**
	 * This method checks if today is reserved.
	 * If reserved return the user who reserved or else return its available
	 * @param today
	 * @return null or user
	 */
	public User reserved(Date today) {
		DayReservedEntry entry = get(today);
        return entry != null ? entry.getUser() : null;
	}
	
	/**
	 *This method checks if from date and to date are equal 
	 * @param from: parameter with the first date
	 * @param to: parameter with the second date.
	 * @return boolean
	 */
	
	private boolean datesEqual(Date from, Date to)
	{
		if(from.getYear() == (to.getYear())) {
		    if(from.getMonth() == (to.getMonth())) {
			    if(from.getDate() == (to.getDate())) {
			    	return true;
			    }
		    }
	    }
		return false;
	}
}

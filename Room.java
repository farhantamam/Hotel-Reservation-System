
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public abstract class Room implements java.io.Serializable {
	public static final long serialVersionUID = 94289568L;
	
	private int roomId;
    private List<DayReservedEntry> dayReservations = new ArrayList<>();
    
    public Room(int roomId) {
        this.roomId = roomId;
	}
    //for testing
	public List<DayReservedEntry> getDayReservations() {
		return dayReservations;
	}
	abstract int cost();
    abstract boolean isEconomical();
	public int getRoomId() {
        return roomId;
	}
	
	// make the date available for the room
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
	
	// check if room is available for the duration [from, to] days
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
	public void reserve(Date from, Date to, User user) {
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
	public String toString() {
		return Integer.toString(roomId) +"(" + (isEconomical() ? "Econ" : "Lux") + ")";
	}
	/** if today is reserved, determine who reserved?
	 *  else return null for available indication
	 *  
	*/
	public User reserved(Date today) {
		DayReservedEntry entry = get(today);
        return entry != null ? entry.getUser() : null;
	}
	
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

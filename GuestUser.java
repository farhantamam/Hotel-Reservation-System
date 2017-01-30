
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This GuestUser class is a child of User class
 * In addition to the User Class members this class has list of reservations 
 */

public class GuestUser extends User {
	private static final long serialVersionUID = 94289568L;
	
    private List<Reservation> myReservations;
    private List<Reservation> transaction;

    /**
     * This constructor initializes the data members of the class
     * @param uid : parameter with the the user Id
     * @param uname : parameter with the user name 
     */
    
	public GuestUser(int uid, String uname) {
        super(uid, uname);
        this.myReservations = new ArrayList<>();
        this.transaction = new ArrayList<>();
	}
	
	/**
	 * This method confirms the user is a guest
	 * @return boolean
	 */
    public boolean isGuest() {
    	return true;
    }

	
    /**
     * This accessor return all the current resrvations of the user
     * return list
     */
    
	List<Reservation> getAllReservations() {
		return myReservations; 
	}
	
	/**get all reservations in this transaction
	 * @return transaction
	 */
		public List<Reservation> getTransaction() {
	return transaction; 
		}

	/**
	 * This method sets all the existing reservation of the user to be active
	 */
	
	void commitReservations() {
		for (Reservation entry: myReservations) {
			if (entry.isActive()) {
				entry.setActive(false);
			}
		}
	}
	
	
	/**
	 * This method cancels the particular reservation chosen by the user
	 * @param r : parameter with the reservation
	 * @return boolean : return false if room is not in the list
	 * 					and true if it is removed
	 */
	
	/* assume reservation exists in the list */
	
	boolean cancelReservation(Reservation r) {
		boolean isRemoved = myReservations.remove(r);
		if (!isRemoved) {
			return false;  // no in the list!
		}
		// make room again available for the dates in the release
		r.releaseRoom();
		return true;
	}
	

	/**
	 * This methods returns list of all available rooms thats available for the given
	 *  duration of time 
	 * @param from : parameter with the starting date
	 * @param to	: parameter with the ending date
	 * @param isLuxory : parameter with the type of room
	 * @return : this returns list of all available rooms for that date
	 */
	
	List<Room> getAvailableRooms(Date from, Date to, boolean isLuxory) {
		Hotel hotel = Hotel.getInstance();
		List<Room> rooms = isLuxory ? hotel.getAllLuxoriousRooms() : hotel.getAllEconomyRooms();
		List<Room> available = new ArrayList<>();
		for (Room room : rooms) {
			if (room.isAvailable(from, to)) {
				available.add(room);
			}
		}
		return available;
	}
	
	/**
	 * This methods stores the individual room reservation in to the transaction array
	 * until the transaction is done
	 * @param from : parameter with the from date
	 * @param to : parameter with the to date
	 * @param room : parameter with the room info
	 * @return boolean : return the status as true if it added else false
	 */
	
	boolean makeReservation(Date from, Date to, Room room) {
		// check again if room is indeed available
		if (!room.isAvailable(from, to)) {
			return false;
		}
		// make the reservation active
		Reservation r = new Reservation(from, to, this, room);
		r.setActive(true);
		transaction.add(r);
		room.reserved(from, to, this);
		return true;
	}
	
	/**
	 * This method adds all the reservations from the transaction array 
	 * to the myReservation list once the user is 
	 * done with all the individual room reservation
	 */
	
	public void done() {
		myReservations.addAll(transaction);
		// not sure if this will work as intended
		// maybe should just instantiate again with "new"
		transaction.removeAll(transaction);
	}
	
	/**
	 * This method calls the print method of the receipt with Receipt object pased
	 * @param receipt : parameter with object of Receipt
	 */
	
	public String makeReceipt(Receipt receipt) {
		String s = receipt.print(this);
		finishTransaction();
		return s;
	}
	
	private void finishTransaction() {
		// not sure if this will work as intended
		// maybe should just instantiate again with "new"
transaction.removeAll(transaction);
}
}




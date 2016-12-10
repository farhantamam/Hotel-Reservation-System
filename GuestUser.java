
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GuestUser extends User {
	private static final long serialVersionUID = 94289568L;
	
    private List<Reservation> myReservations;
    private List<Reservation> transaction;

	public GuestUser(int uid, String uname) {
        super(uid, uname);
        this.myReservations = new ArrayList<>();
        this.transaction = new ArrayList<>();
	}
	
    public boolean isGuest() {
    	return true;
    }

	// get all my current reservations
	public List<Reservation> getAllReservations() {
		return myReservations; 
	}
	
	// get all reservations in this transaction
	public List<Reservation> getTransaction() {
		return transaction; 
	}
		
	/*
	// make currently active reservations to accepted 
	void commitReservations() {
		for (Reservation entry: myReservations) {
			if (entry.isActive()) {
				entry.setActive(false);
			}
		}
	}*/
	// assume reservation exists in the list
	boolean cancelReservation(Reservation r) {
		boolean isRemoved = myReservations.remove(r);
		if (!isRemoved) {
			return false;  // no in the list!
		}
		// make room again available for the dates in the release
		r.releaseRoom();
		return true;
	}
	// get available rooms [from, to] dates for a room type luxury  (/economy)
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
	// choose this room reservation [from, to] days
	// stores it in both transaction and myReservations array, until this transaction is done
	boolean makeReservation(Date from, Date to, Room room) {
		// check again if room is indeed available
		if (!room.isAvailable(from, to)) {
			return false;
		}
		Reservation r = new Reservation(from, to, this, room);
		transaction.add(r);
		myReservations.add(r);
		room.reserve(from, to, this);
		return true;
	}
	
	//context for strategy
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
	
	public String getReservations() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		String resrvations = " id Room #        From - To\n";
		for(int i=0; i<myReservations.size(); i++) {
			Reservation r = myReservations.get(i);
			resrvations += String.format(" %2d %6d  %10s - %10s %n", (i+1), r.getRoom().getRoomId(), dateFormat.format(r.getFrom()), dateFormat.format(r.getTo()) );
		}
		return resrvations;
	}
}

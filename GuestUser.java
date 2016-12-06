
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GuestUser extends User {
    List<Reservation> myReservations;

	public GuestUser(int uid, String uname) {
        super(uid, uname);
        this.myReservations = new ArrayList<>();
	}
	
    public boolean isGuest() {
    	return true;
    }

	// get all my current reservations
	List<Reservation> getAllReservations() {
		return myReservations; 
	}
	// make currently active reservations to accepted 
	void commitReservations() {
		for (Reservation entry: myReservations) {
			if (entry.isActive()) {
				entry.setActive(false);
			}
		}
	}
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
	// get available rooms [from, to] dates for a room type luxory  (/economy)
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
	boolean makeReservation(Date from, Date to, Room room) {
		// check again if room is indeed available
		if (!room.isAvailable(from, to)) {
			return false;
		}
		// make the reservation active
		Reservation r = new Reservation(from, to, this, room);
		r.setActive(true);
		myReservations.add(r);
		room.reserved(from, to, this);
		return true;
	}
	void makeReceipt(Receipt receipt) {
		receipt.print(myReservations, this);
	}
}

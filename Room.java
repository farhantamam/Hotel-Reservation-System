import java.util.ArrayList;
import java.util.Date;

public class Room {
	
	int roomNumber;
	int price;
	ArrayList<Reservation> reservations;
	
	public Room(int num, int price)
	{
		roomNumber = num;
		this.price = price;
		reservations = new ArrayList<Reservation>();
	}

	public Reservation getReservation(int index) {
		return reservations.get(index);
	}

	// returns the index containing that reservation
	public int addReservation(Date start, Date end) {
		Reservation r = new Reservation(start, end);
		reservations.add(r);
		return reservations.size()-1;
	}

	public int getRoomNumber() {
		return roomNumber;
	}
	
	public int getPrice() {
		return price;
	}


	
}

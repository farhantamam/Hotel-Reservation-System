
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Hotel {
	
	private static Hotel instance = new Hotel();
	private GuestUser currentGuest;
	
    private List<Room> rooms;
    private List<User> users;
    private List<Room> luxorious;
    private List<Room> economy;
    
    public Hotel()
    {
    	rooms = new ArrayList<>();
        users = new ArrayList<>();
        luxorious = new ArrayList<>();
        economy = new ArrayList<>();
        makeRooms();
        createManager(1234, "manager");//only one default manager
    }
    
	public static Hotel getInstance() {
		return instance;
	}

    /**
	 * @return the currentGuest
	 */
	public GuestUser getCurrentGuest() {
		return currentGuest;
	}

	/**
	 * @param currentGuest the currentGuest to set
	 */
	public void setCurrentGuest(GuestUser currentGuest) {
		this.currentGuest = currentGuest;
	}

	List<Room> getAllRooms() {
    	return rooms;
    }
    List<Room> getAllLuxoriousRooms() {
    	return luxorious;
    }
    List<Room> getAllEconomyRooms() {
    	return economy;
    }
    List<User> getAllUsers() {
    	return users;
    }
    /**
     * Creating 10 economic and 10 luxurious rooms -
     *     economic: 1 - 10, luxurious: 101 - 110
     */
    private void makeRooms() {
    	for (int i=1; i<=10; ++i) {
    		economy.add(new EconomicalRoom(i));
    	}
    	rooms.addAll(economy);
    	
    	for (int i=101; i<=110; ++i) {
    		luxorious.add(new LuxoriousRoom(i));
    	}
    	rooms.addAll(luxorious);
    }

 
    /** no checks are made
     */
    public GuestUser createGuestUser(int uid, String uname) {
        GuestUser user = (GuestUser)findUser(uid, uname, true);
        if (user == null) {
        	user = new GuestUser(uid, uname);
        	users.add(user);
        }
        return (GuestUser)user;
    }

    private void createManager(int uid, String uname) {
        User user = findUser(uid, uname, false);
        if (user == null) {
        	user = new Manager(uid, uname);
        	users.add(user);
        }
        //return (Manager)user;
    }
    private User findUser(int uid, String uname, boolean isGuest) {
    	for (User user: users) {
    		if (user.getUid() == uid) {
	    		if (user.getUserName().equals(uname)) {
	    		   if (isGuest && user.isGuest() || (!isGuest && !user.isGuest())) {
	    			   return user;
	    		   }
	    		}
    		}
    	}
    	return null;
    }
    public GuestUser findUser(int uid)
    {
    	for (User user: users) {
    		if (user.getUid() == uid) {
    			return (GuestUser)user;
    		}
    	}
    	return null;
    }

    void load() {
    	// :todo: load users, rooms, reservations
    }
    
    void save() {
    	// :todo: save users, rooms, reservations
    }
    
}
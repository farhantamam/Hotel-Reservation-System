
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
/**
 * This Hotel class contains objects of GuestUser and Manager
 * and List of of rooms and users
 */

public class Hotel {
	
	private static Hotel instance = new Hotel();
	private GuestUser currentGuest;
	private Manager manager;
	
    private List<Room> rooms;
    private List<User> users;
    private List<Room> luxorious;
    private List<Room> economy;
    
    /**This constructor initializes the data members of the Hotel class
     * and creates a single default manager
     */
    
    public Hotel()
    {
    	rooms = new ArrayList<>();
        users = new ArrayList<>();
        luxorious = new ArrayList<>();
        economy = new ArrayList<>();
        makeRooms();
        manager = createManager(1234, "manager");//only one default manager
    }
    
	public static Hotel getInstance() {
		return instance;
	}

    /**Accessor to return the current Guest
	 * @return currentGuest : this returns the currentGuest
	 */
	
	public GuestUser getCurrentGuest() {
		return currentGuest;
	}

	/**Mutator to set the currentGuest to the parameter passed in
	 * @param currentGuest : parameter to set the currentGuest
	 */
	public void setCurrentGuest(GuestUser currentGuest) {
		this.currentGuest = currentGuest;
	}

	/**Accessor to return the manager
	 * @return manager : this return the manager
	 */
	public Manager getManager() {
		return manager;
	}

	/**Accessor to return list of all rooms
	 * @return rooms
	 */
	
	List<Room> getAllRooms() {
    	return rooms;
    }
	
	/**Accessor to return the list of all luxurious rooms
	 * @return luxurious : this return all luxurious rooms
	 */
	
    List<Room> getAllLuxoriousRooms() {
    	return luxorious;
    }
    
    /**Accessor to return the list of all Economy rooms
	 * @return economy : this return all economy rooms
	 */
    
    List<Room> getAllEconomyRooms() {
    	return economy;
    }
    
    /**Accessor to return the list of all users
	 * @return users : this return all users
	 */
    
    List<User> getAllUsers() {
    	return users;
    }
    
    /**This methods creates 10 economic and 10 luxurious rooms.
     *  Economic : 1 - 10, Luxurious: 101 - 110
     *  and add them to the list
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

 
    /**
     * This method creates a new Guestuser with the user id and user name passed in
     * and adds it to th user list
     * @param uid : user id
     * @param uname: user name
     * @return GuestUser : return the the newly created user
     */
    public GuestUser createGuestUser(int uid, String uname) {
        GuestUser user = (GuestUser)findUser(uid, uname, true);
        if (user == null) {
        	user = new GuestUser(uid, uname);
        	users.add(user);
        }
        return (GuestUser)user;
    }

    /**
     * This method creates a new Manager with the id and name passed in
     * @param uid : parameter with userId
     * @param uname : parameter with userName
     * @return Manager : this returns newly created manager 
     */
    
    private Manager createManager(int uid, String uname) {
        User user = findUser(uid, uname, false);
        if (user == null) {
        	user = new Manager(uid, uname);
        	users.add(user);
        }
        return (Manager)user;
    }
    
    /**
     * This method finds the user from the name and id passes in and type of user
     * @param uid : parameter with userId
     * @param uname: parameter with userName
     * @param isGuest: parameter with type fo user (guest/manager)
     * @return User : this returns the user if found else null
     */
    
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
    
    /**
     * This method finds the Guest user with the userId passed in
     * @param uid : parameter with the userId
     * @return GuestUser: this returns the GuestUser if found else return null
     */
    
    public GuestUser findUser(int uid)
    {
    	for (User user: users) {
    		if (user.getUid() == uid) {
    			return (GuestUser)user;
    		}
    	}
    	return null;
    }
    
    // fix load and save (file DNE)
    
    /**
     * This method lets the manager read all
     * the reservation info from the database onto the application
     * 
     */
    
    public void load() {
        try {
       		//System.out.println("Start loading data");
            FileInputStream fi = new FileInputStream("serialized.bin");
            ObjectInputStream si = new ObjectInputStream(fi);
            users = (List<User>)si.readObject();
            //System.out.println("User: " + users);
            rooms = (List<Room>) si.readObject();
            //System.out.println("Rooms: " + rooms);
            for (Room room : rooms) {
            	if (room.isEconomical())
            		economy.add(room);
            	else luxorious.add(room);
            }
            /*
            for (User user : users) {
            	if (!user.isGuest()) {
            		continue;
            	}
            	System.out.println(user);
            	System.out.println(((GuestUser)user).getAllReservations());
            }
            for (Room room : rooms) {
            	if (!room.getDayReservations().isEmpty()) {
            		System.out.println(room.getDayReservations());
                	System.out.println(room);
            	}
            }
       		System.out.println("Loaded data");
       		*/
       		si.close();
       		fi.close();
        } catch (FileNotFoundException ffex) { 
        } catch (Exception e) {
            System.out.println("Exception in loading" + e);
            System.exit(1);
        }    }

    
    /**
     * This method lets the manager writes all 
     * the reservation info from the application to the database
     */
    
    public void save() {
    	File file = new File("serialized.bin");
    	try {
            FileOutputStream fo = new FileOutputStream("serialized.bin");
            ObjectOutputStream so = new ObjectOutputStream(fo);
            so.writeObject(users);
            so.writeObject(rooms);
            so.flush();
            so.close();
            fo.close();
        } catch (FileNotFoundException fnf) {
			//e.printStackTrace();
			try {
				// creates the empty file
				file.createNewFile();
				save();//dont know if this is necessary
			}  catch (Exception x) {
				// Some other sort of failure, such as permissions.
				x.printStackTrace();
				System.err.format("createFile error: %s%n", x);
			}
		} catch (Exception e) {
            System.out.println(e);
            System.exit(1);
        }
}
    
}

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class Hotel {
	
	private static Hotel instance = new Hotel();
	private GuestUser currentGuest;
	private Manager manager;
	
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
        if(!new File("serialized.bin").exists()) {
        	makeRooms();
        }
    	manager = createManager(1234, "manager");//only one default manager
        currentGuest = null;
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

	public Manager getManager() {
		return manager;
	}

	public List<Room> getAllRooms() {
    	return rooms;
    }
	public List<Room> getAllLuxoriousRooms() {
    	return luxorious;
    }
	public List<Room> getAllEconomyRooms() {
    	return economy;
    }
	public List<User> getAllUsers() {
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

    private Manager createManager(int uid, String uname) {
        User user = findUser(uid, uname, false);
        if (user == null) {
        	user = new Manager(uid, uname);
        	//users.add(user);
        }
        return (Manager)user;
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
    
    // fix load and save (file DNE)
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
/**
 * This abstract class contains all the user informations : name and id
 * @author
 *
 */
public abstract class User {
    int uid;
    String userName;

    /**
     * Consructor to initialize name and id
     * @param uid
     * @param uname
     */
	public User(int uid, String uname) {
        this.uid = uid;
        this.userName = uname;
	}
	
	/**
	 * Accessor to return the user id
	 * @return uid
	 */
	
	public int getUid() {
		return uid;
	}
	
	/**
	 * Mutator to set the user id
	 * @param uid
	 */
	
	public void setUid(int uid) {
		this.uid = uid;
	}
	
	/**
	 * Accessor to return the user name
	 * @return userName
	 */
	
	public String getUserName() {
		return userName;
	}
	
	/** 
	 * Mutator to set the user name
	 * @param userName
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	/**
	 * Abstract method to check if the user is a guest
	 * @return
	 */
	
	public abstract boolean isGuest();
	
	/**
	 * Overridden toString method to convert user id and anme to string
	 */
	
	@Override
	public String toString() {
		return Integer.toString(uid) + ", " + userName;
	}

}

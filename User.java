
public abstract class User implements java.io.Serializable {
    
	public static final long serialVersionUID = 94289568L;
	
	private int uid;
    private String userName;

	public User(int uid, String uname) {
        this.uid = uid;
        this.userName = uname;
	}
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public abstract boolean isGuest();
	
	@Override
	public String toString() {
		return Integer.toString(uid) + ", " + userName;
	}

}

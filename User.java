
public abstract class User {
    int uid;
    String userName;

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

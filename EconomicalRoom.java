/**
 * Concrete class of the abstract class Room
 * @author
 */
public class EconomicalRoom extends Room{

	/**
	 * Constructor that initializes the roomId with the help of superclass Constructor 
	 * @param roomId : parameter that has the room id
	 */
	
	public EconomicalRoom(int roomId) {
		super(roomId);
	}

	/**
	 * This method returns the cost as 80
	 * @return int
	 */
	
	@Override
	int cost() {
		return 80;//changed this
	}

	@Override
	/**
	 * This method is to confirm room is economical
	 * @return boolean : this returns true
	 */
	
	boolean isEconomical() {
		return true;
	}

}

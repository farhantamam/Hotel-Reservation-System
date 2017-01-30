
/** This luxuriousRoom class is extended from the room class 
 * @author 
 */

public class LuxoriousRoom extends Room {

	/**
	 * This constructor initializes the roomId with the help of super constructor
	 * @param roomId : parameter with the roomId
	 */
	
	public LuxoriousRoom(int roomId) {
		super(roomId);
	}

	@Override
	
	/**
	 *This methods sets the cost of the luxury room as 200 
	 */
	
	int cost() {
		return 200;
	}

	/**
	 * This methods returns the room is not economical
	 * @return boolean : this returns room is not not economical
	 */
	
	@Override
	boolean isEconomical() {
		return false;
	}

}

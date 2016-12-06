
public class LuxoriousRoom extends Room {

	public LuxoriousRoom(int roomId) {
		super(roomId);
	}

	@Override
	int cost() {
		return 200;//changed this
	}

	@Override
	boolean isEconomical() {
		return false;
	}

}

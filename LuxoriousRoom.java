
public class LuxoriousRoom extends Room {
	private static final long serialVersionUID = 94289568L;

	public LuxoriousRoom(int roomId) {
		super(roomId);
	}

	@Override
	int cost() {
		return 200;
	}

	@Override
	boolean isEconomical() {
		return false;
	}

}

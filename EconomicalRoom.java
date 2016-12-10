
public class EconomicalRoom extends Room {
	private static final long serialVersionUID = 94289568L;
	
	public EconomicalRoom(int roomId) {
		super(roomId);
	}

	@Override
	int cost() {
		return 80;
	}

	@Override
	boolean isEconomical() {
		return true;
	}

}


public class EconomicalRoom extends Room{

	public EconomicalRoom(int roomId) {
		super(roomId);
	}

	@Override
	int cost() {
		return 80;//changed this
	}

	@Override
	boolean isEconomical() {
		return true;
	}

}

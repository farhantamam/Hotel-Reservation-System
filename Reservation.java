import java.util.Date;

public class Reservation
{
	Date startDate;
	Date endDate;

	public Reservation(Date start, Date end)
	{
		startDate = start;
		endDate = end;
	}
	public Date getStartDate() {
		return startDate;
	}

	public Date getEndDate() {
		return endDate;
	}
}
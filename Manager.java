import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import java.util.Date;
import java.util.List;

public class Manager extends User {

	private JFrame viewFrame;
	private JPanel panel;
	private JPanel monthViewPane;
	private JLabel monthYear;	
	private JLabel week;
	private JPanel btnPanel;
	private JButton[][] buttons;
	GregorianCalendar calendar;

	public Manager(int uid, String uname) {
		super(uid, uname);
	}

	public boolean isGuest() {
		return false;
	}

	/**
	 * Return reserved rooms, and available for the given day
	 * @param today
	 * @param available
	 * @param reservedRooms
	 */
	public void getTodayDetails(Date day, List<Room> available, List<ReservedEntry> reservedRooms)
	{
		List<Room> rooms = Hotel.getInstance().getAllRooms();
		for (Room entry : rooms) {
			User user = entry.reserved(day);
			if (user == null) {
				available.add(entry);
			} else {
				reservedRooms.add(new ReservedEntry(entry, user));
			}
		}
	}

	/*
	 * load two files, users: contains information about user and its reservations
	 * rooms: stores information regarding all the rooms
	 */
	public void load()
	{

	}

	public void getViewFrame()
	{
		viewFrame = new JFrame("Manager View");
		panel = new JPanel();//new BorderLayout());
		monthViewPane = new JPanel(new BorderLayout());

		calendar = new GregorianCalendar(); 
		getMonthView(calendar.get(Calendar.DAY_OF_MONTH));

		viewFrame.add(panel);
		viewFrame.pack();
		viewFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		viewFrame.setVisible(true);
	}

	private void updateViewFrame()
	{
		panel.removeAll();
	}

	private void getMonthView(int highlight)
	{
		panel.removeAll();
		monthViewPane.removeAll();//to make sure nothing gets overlapped b/c of subsequent calls	
		monthViewPane.add(getMonthYearPane(), BorderLayout.NORTH);
		//getMonthYearPane();
		week = new JLabel("Su  Mo  Tu  We  Th  Fr  Sa");
		monthViewPane.add(week, BorderLayout.CENTER);
		buttons = new JButton[6][7];	
		monthViewPane.add( addButtons(), BorderLayout.SOUTH );
		highlight( Integer.toString(highlight) );
		panel.add(monthViewPane);
		viewFrame.revalidate();
		/*viewFrame.add(panel);		
		viewFrame.pack();
		viewFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		viewFrame.setVisible(true);
		 */
	}

	private JPanel getMonthYearPane()
	{
		JPanel monthYearPane = new JPanel();

		JButton monthPrevious = new JButton("<");
		monthPrevious.setBorder(null);
		monthPrevious.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				calendar.add(Calendar.MONTH, -1);
				getMonthView(0);
			}
		});

		JLabel monthLabel = new JLabel(calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.US));
		JButton monthNext = new JButton(">");
		monthNext.setBorder(null);
		monthNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				calendar.add(Calendar.MONTH, 1);
				//getViewFrame();
				getMonthView(0);//this returns new month with no days highlighted
			}
		});

		JButton yearPrevious = new JButton("<");
		yearPrevious.setBorder(null);
		yearPrevious.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				calendar.add(Calendar.YEAR, -1);
				getMonthView(0);
			}
		});

		JLabel yearLabel = new JLabel("" + calendar.get(Calendar.YEAR));
		JButton yearNext = new JButton(">");
		yearNext.setBorder(null);
		yearNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				calendar.add(Calendar.YEAR, 1);
				getMonthView(0);
			}
		});

		monthYearPane.add(monthPrevious);
		monthYearPane.add(monthLabel);
		monthYearPane.add(monthNext);
		monthYearPane.add(yearPrevious);
		monthYearPane.add(yearLabel);
		monthYearPane.add(yearNext);

		//panel.add(monthYearPane, BorderLayout.NORTH);
		return monthYearPane;
	}

	private void highlight(String dayStr) 
	{
		//if(dayStr.equals(null))
		//return;
		for(int i=0; i<buttons.length; i++)
		{
			for(int j=0; j<buttons[0].length; j++)
			{
				if( buttons[i][j].getText().equals(dayStr) )
					buttons[i][j].setBorder(new LineBorder(Color.BLACK));
				else
					buttons[i][j].setBorder(null);
			}
		}
	}

	private JPanel addButtons()
	{
		btnPanel = new JPanel();
		btnPanel.setFont(new Font("Arial", Font.PLAIN, 10));
		btnPanel.setLayout(new GridLayout(0, 7, 3, 3));
		//panel.setBackground(Color.white);

		int x = calendar.get(Calendar.DAY_OF_MONTH);		
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		int firstWeekdayOfMonth = calendar.get(Calendar.DAY_OF_WEEK);
		calendar.set(Calendar.DAY_OF_MONTH, x);

		int numberOfMonthDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

		int day = 1;
		for (int j = 0; j < firstWeekdayOfMonth-1; j++) {
			JButton numBtn = new JButton("");
			numBtn.setBorder(null);
			numBtn.setHorizontalAlignment(SwingConstants.RIGHT);
			buttons[0][j] = numBtn;
			btnPanel.add(numBtn);
		}
		for (int j = firstWeekdayOfMonth-1; j < 7; j++) {
			JButton numBtn = new JButton( Integer.toString(day++) );
			numBtn.setBorder(null);
			numBtn.setHorizontalAlignment(SwingConstants.RIGHT);
			numBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					JButton source = (JButton) event.getSource();
					int newDay = Integer.parseInt(source.getText());
					calendar.set(Calendar.DAY_OF_MONTH, newDay);
					highlight(source.getText());
					//add appropriate method to display a proper view
				}
			});

			buttons[0][j] = numBtn;
			btnPanel.add(numBtn);
		}

		for (int i = 1; i < 6; i++)
		{
			for (int j = 0; j < 7; j++) {
				if(day <= numberOfMonthDays)
				{
					JButton numBtn = new JButton( Integer.toString(day++) );
					numBtn.setBorder(null);
					numBtn.setHorizontalAlignment(SwingConstants.RIGHT);
					numBtn.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent event) {
							JButton source = (JButton) event.getSource();
							if (source.getText().length() == 0) {
								return;
							}
							int newDay = Integer.parseInt(source.getText());
							calendar.set(Calendar.DAY_OF_MONTH, newDay);
							//numBtn.setForeground(Color.GRAY);
							highlight(source.getText());
							//updateDayView(calendar.getTime());
						}
					});

					buttons[i][j] = numBtn;
					btnPanel.add(numBtn);
				}
				else
				{
					JButton numBtn = new JButton("");
					numBtn.setBorder(null);
					numBtn.setHorizontalAlignment(SwingConstants.RIGHT);
					buttons[i][j] = numBtn;
					btnPanel.add(numBtn);
				}
			}
		}
		return btnPanel;   

	}

	private void save()
	{

	}

	private void quit()
	{
		save();
	}

	// fix this so it casts to proper  format
	private void load(File thisFile)
	{
		File file = thisFile;
		FileInputStream fileIn;
		ObjectInputStream in;
		anEvent e;
		try {
			fileIn = new FileInputStream(file);
			in = new ObjectInputStream(fileIn);
			try{
				while(true)	
				{
					e = (anEvent) in.readObject();
					//add the corresponding method to record this data
					//c.addEvent(e);
				}
			}
			catch ( EOFException eof ) { 
				// ObjectInputStream doesn't have anything similar to hasNext()
				// therefore, relying on EOF to stop
				in.close();
				fileIn.close();
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			}
		}catch (FileNotFoundException fnf) {

		}catch ( EOFException eof ) { 

		}catch(IOException i) {
			i.printStackTrace();
		}
	}
	/*
	private void save(File thisFile)
	{
		File file = thisFile;
		try {
			FileOutputStream fileOut = new FileOutputStream(file.getName());
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			for(anEvent e: ts)
			{
				out.writeObject(e);
			}
			out.close();
			fileOut.close();
		} catch (FileNotFoundException fnf) {
			//e.printStackTrace();
			try {
				// creates the empty file
				file.createNewFile();
			}  catch (Exception x) {
				// Some other sort of failure, such as permissions.
				x.printStackTrace();
				System.err.format("createFile error: %s%n", x);
			}
		}catch(IOException i) {
         i.printStackTrace();
      }
	}*/

}

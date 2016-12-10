import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class HotelReservationSystem {

	/**
	 * using different panels to change between views based on user input
	 * don't know if this is the best approach to change between view on the same frame,
	 * it is meant to be a starting point for GUI of this application
	 * @param args
	 */
	public static void main(String[] args) 
	{
		JFrame frame = new JFrame("Hotel Reservation System");

		Hotel hotel = Hotel.getInstance();
		hotel.load();// first thing that needs to happen on start up is load
		//guest is set once the user logs in
		
		/*//this part is for testing purposes
		GuestUser guest = hotel.getCurrentGuest();//hotel.createGuestUser(1, "test");
		Room r101 = hotel.getAllLuxoriousRooms().get(0);
		Room r7 = hotel.getAllEconomyRooms().get(6);
		if(guest != null) {
			if( guest.makeReservation(new Date(2016-1900, 11, 5), new Date(2017-1900, 0, 5), r101) )
				System.out.println("successfully booked room number: " + r101.getRoomId());
			else System.out.println("Error: room wasn't booked");
			
			if(guest.makeReservation(new Date(2016-1900, 11, 22), new Date(2016-1900, 11, 24), r7))
				System.out.println("successfully booked room number: " + r7.getRoomId());
		}
		*/
		// until here
		
		JPanel userPanel = new JPanel();
		JButton signInBtnInitial = new JButton("Sign In");		
		JButton signUpBtnInitial = new JButton("Sign Up");
		userPanel.add(signInBtnInitial);
		userPanel.add(signUpBtnInitial);

		JPanel signInPanel = new JPanel();
		JLabel id_signIn_Label = new JLabel("id:");
		JTextField id_signInField = new JTextField(20);
		JButton signInBtnActual = new JButton("Sign In");
		signInPanel.add(id_signIn_Label);
		signInPanel.add(id_signInField);
		signInPanel.add(signInBtnActual);

		JPanel signedInPanel= new JPanel();
		JButton makeReservBtn=new JButton("Make Reservation");
		JButton viewCancelBtn=new JButton("View/Cancel Reservation");
		JButton signOutBtn=new JButton("Sign Out");
		signedInPanel.add(makeReservBtn);
		signedInPanel.add(viewCancelBtn);
		signedInPanel.add(signOutBtn);
		
		
		JPanel viewCancelPanel = new JPanel();
		
		JPanel viewReservationPanel = new JPanel(new BorderLayout());
		JLabel viewHeaderLabel = new JLabel("Your Reservations:");
		JTextArea viewArea = new JTextArea();
		viewArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
		viewArea.setEditable(false);
		viewReservationPanel.add(viewHeaderLabel, BorderLayout.NORTH);
		viewReservationPanel.add(viewArea, BorderLayout.CENTER);
		viewReservationPanel.setBorder(new LineBorder(Color.BLACK));
		
		JPanel cancelReservationPanel = new JPanel();
		cancelReservationPanel.setLayout(new BoxLayout(cancelReservationPanel, BoxLayout.Y_AXIS));
		JLabel cancelLabel = new JLabel("Enter Reservation id to cancel:");
		JTextField cancelIdField = new JTextField(5);
		JButton cancelReservationButton = new JButton("Cancel Reservation");
		JButton viewCancelDoneButton = new JButton("Done");
		cancelReservationPanel.add(cancelLabel);
		cancelReservationPanel.add(cancelIdField);
		cancelReservationPanel.add(cancelReservationButton);
		cancelReservationPanel.add(viewCancelDoneButton);
		
		viewCancelPanel.add(viewReservationPanel);
		viewCancelPanel.add(cancelReservationPanel);

		
		JPanel preReservationPanel=new JPanel();
		preReservationPanel.setLayout(new BoxLayout(preReservationPanel,BoxLayout.Y_AXIS));

		JPanel checkInPanel = new JPanel();
		checkInPanel.setLayout(new GridLayout(2,2));
		JLabel checkInLabel=new JLabel("Check in (mm/dd/yyyy)");
		JLabel checkOutLabel=new JLabel("Check out (mm/dd/yyyy)");
		JTextField checkInField=new JTextField(10);
		JTextField checkOutField= new JTextField(10);
		checkInPanel.add(checkInLabel);
		checkInPanel.add(checkOutLabel);
		checkInPanel.add(checkInField);
		checkInPanel.add(checkOutField);

		JPanel roomTypePanel= new JPanel();
		JLabel roomTypeLabel=new JLabel("Room type:");
		JButton luxuryButton=new JButton("$200");
		JButton regularButton =new JButton("$80");
		//JButton nextButton =new JButton("Next");

		roomTypePanel.add(roomTypeLabel);
		roomTypePanel.add(regularButton);
		roomTypePanel.add(luxuryButton);
		//roomTypePanel.add(nextButton);

		preReservationPanel.add(checkInPanel);
		preReservationPanel.add(roomTypePanel);

		
		// used in one of the buttons in preReservationPanel
		//JFrame reservationFrame = new JFrame("Room Availability");
		
		JPanel reservationPanel=new JPanel();
		reservationPanel.setLayout(new BoxLayout(reservationPanel,BoxLayout.X_AXIS));
		
		final RoomAvailabilityPanel RoomAvailabilityModel = (new HotelReservationSystem()).new RoomAvailabilityPanel();
		JPanel roomAvailablePanel=new JPanel(new BorderLayout());
		final JLabel availableRoomsLabel=new JLabel(" Available Rooms            "+ "Start-End           ");
		final JTextArea roomList = new JTextArea();
		roomList.setEditable(false);
		roomAvailablePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		roomAvailablePanel.add(availableRoomsLabel, BorderLayout.NORTH);
		roomAvailablePanel.add(roomList, BorderLayout.CENTER);
		
		ChangeListener cl = new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				availableRoomsLabel.setText(RoomAvailabilityModel.getLabel());
				roomList.setText(RoomAvailabilityModel.getTextArea());
			}
		};
		RoomAvailabilityModel.addListner(cl);
		
		//include the list of reserved rooms
		JPanel confirmationPanel=new JPanel();
		confirmationPanel.setLayout(new BoxLayout(confirmationPanel,BoxLayout.Y_AXIS));

		JPanel northPanel=new JPanel();
		JLabel roomNumberLabel= new JLabel("Enter the room number to reserve");
		JTextField roomNumberField =new JTextField(5);
		northPanel.add(roomNumberLabel);
		northPanel.add(roomNumberField);

		JPanel southPanel=new JPanel();
		JButton confirmButton=new JButton("Confirm");
		JButton moreReservationButton=new JButton("More Reservations?");
		JButton doneButton=new JButton("Done");
		southPanel.add(confirmButton);
		southPanel.add(moreReservationButton);
		southPanel.add(doneButton);

		confirmationPanel.add(northPanel);
		confirmationPanel.add(southPanel);

		reservationPanel.add(roomAvailablePanel);
		reservationPanel.add(confirmationPanel);
		
		/*reservationFrame.add(reservationPanel);
		reservationFrame.setLocation(0, 162);
		reservationFrame.pack();
		reservationFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		reservationFrame.setVisible(false);
		*/
		
		JPanel signUpPanel = new JPanel();
		JLabel id_signUp_Label = new JLabel("id:");
		JLabel name_signUp_Label = new JLabel("Name:");
		JTextField id_signUpField = new JTextField(20);
		JTextField name_signUpField = new JTextField(30);
		JButton signUpBtnActual = new JButton("Sign Up");
		signUpPanel.add(id_signUp_Label);
		signUpPanel.add(id_signUpField);
		signUpPanel.add(name_signUp_Label);
		signUpPanel.add(name_signUpField);
		signUpPanel.add(signUpBtnActual);
		//probably should use spring layout
		signUpPanel.setLayout(new BoxLayout(signUpPanel, BoxLayout.PAGE_AXIS));
		
		JPanel reciptSelectPanel = new JPanel();
		JLabel formatSelectLabel = new JLabel("Select a format to print the receipt in:");
		JButton simpleButton = new JButton("simple");
		JButton comprehensiveButton = new JButton("comprehensive");
		reciptSelectPanel.add(formatSelectLabel);
		reciptSelectPanel.add(simpleButton);
		reciptSelectPanel.add(comprehensiveButton);
		reciptSelectPanel.setPreferredSize(new Dimension(250, 60));
		
		JPanel recieptPanel = new JPanel(new BorderLayout());
		JTextArea recieptArea = new JTextArea();
		recieptArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
		recieptArea.setEditable(false);
		JButton printButton = new JButton("print");
		recieptPanel.add(recieptArea, BorderLayout.NORTH);
		recieptPanel.add(printButton, BorderLayout.CENTER);
		
		JPanel managerPanel = new JPanel();
		JButton loadButton = new JButton("Load");
		loadButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				hotel.load();
			}
		});
		JButton viewButton = new JButton("View");
		viewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				hotel.getManager().getViewFrame(hotel.getAllRooms());
			}
		});

		JButton saveButton = new JButton("Save");
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				hotel.save();//make sure this part is working
			}
		});
		JButton quitButton = new JButton("Quit");
		quitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				hotel.save();//make sure this part is working
				frame.setVisible(false);
				frame.dispose();
			}
		});
		managerPanel.add(loadButton);
		managerPanel.add(viewButton);
		managerPanel.add(saveButton);
		managerPanel.add(quitButton);

		JPanel mainPanel = new JPanel();		
		JButton userButton = new JButton("User");
		userButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) 
			{
				frame.remove(mainPanel);
				frame.add(userPanel);
				frame.setSize(200, 70);
				frame.revalidate();
			}
		});

		JButton managerButton = new JButton("Manager");
		managerButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) 
			{
				frame.remove(mainPanel);
				frame.add(managerPanel);
				frame.setSize(400, 70);
				frame.revalidate();
			}
		});
		mainPanel.add(userButton);
		mainPanel.add(managerButton);
		

		//all the button listeners
		signInBtnInitial.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				frame.remove(userPanel);
				frame.add(signInPanel);
				frame.setSize(400, 70);
				frame.revalidate();
				//frame.pack();
			}
		});
		signUpBtnInitial.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				frame.remove(userPanel);
				frame.add(signUpPanel);
				//frame.setSize(400, 200);
				frame.pack();
				frame.revalidate();
			}
		});

		signInBtnActual.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				if(id_signInField.getText().equals(""))
				{
					JOptionPane.showMessageDialog(null, "Error: please enter an id!", "ERROR", JOptionPane.ERROR_MESSAGE);
					return;
				}
				GuestUser u = hotel.findUser(Integer.parseInt(id_signInField.getText()));
				if(u == null) {
					JOptionPane.showMessageDialog(null, "Error: please enter a valid id!", "ERROR", JOptionPane.ERROR_MESSAGE);
					return;
				}
				hotel.setCurrentGuest(u);
				frame.remove(signInPanel);
				frame.add(signedInPanel);
				frame.setSize(400, 100);
				frame.revalidate();
				//frame.pack();
			}
		});
		
		signUpBtnActual.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(id_signUpField.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Error: please enter an id!", "ERROR", JOptionPane.ERROR_MESSAGE);
					return;
				}
				if(name_signUpField.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Error: please enter name!", "ERROR", JOptionPane.ERROR_MESSAGE);
					return;
				}
				int id = Integer.parseInt(id_signUpField.getText());
				String name = name_signUpField.getText();
				GuestUser u = hotel.findUser(id);
				if(u == null)
					u = hotel.createGuestUser(id, name);
				else {
					JOptionPane.showMessageDialog(null, "Error: user with this id already exists, please choose another id!", "ERROR", JOptionPane.ERROR_MESSAGE);
					return;
				}
				hotel.setCurrentGuest(u);
				frame.remove(signUpPanel);
				frame.add(signedInPanel);
				frame.setSize(400, 100);
				frame.revalidate();
		}
	});

		makeReservBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				frame.remove(signedInPanel);
				frame.add(preReservationPanel);
				//frame.setSize(400, 200);
				frame.pack();
				frame.revalidate();
			}
		});
		
		viewCancelBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				viewArea.setText(hotel.getCurrentGuest().getReservations());
				frame.remove(signedInPanel);
				frame.add(viewCancelPanel);
				frame.pack();
				frame.revalidate();
			}
		});
		
		signOutBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				hotel.setCurrentGuest(null);
				frame.remove(signedInPanel);
				frame.add(mainPanel);
				frame.setSize(250, 70);
				frame.revalidate();
			}
		});
		
		cancelReservationButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(cancelIdField.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Error!! Please enter a reservation id to cancel!", "ERROR", JOptionPane.ERROR_MESSAGE);
					return;
				}
				int roomId = Integer.parseInt(cancelIdField.getText());
				if(roomId > hotel.getCurrentGuest().getAllReservations().size()) {
					JOptionPane.showMessageDialog(null, "Error!! Please enter a valid reservation id!", "ERROR", JOptionPane.ERROR_MESSAGE);
					return;
				}
				hotel.getCurrentGuest().cancelReservation(hotel.getCurrentGuest().getAllReservations().get(roomId-1));
				viewArea.setText(hotel.getCurrentGuest().getReservations());
			}
		});
		
		viewCancelDoneButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cancelIdField.setText("");
				frame.remove(viewCancelPanel);
				frame.add(signedInPanel);
				frame.setSize(400, 100);
				frame.revalidate();
			}
		});
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		regularButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {						
				Date checkInDate = null;
				Date checkOutDate = null;
				try {
					checkInDate = dateFormat.parse(checkInField.getText());
					checkOutDate = dateFormat.parse(checkOutField.getText());
				} catch (ParseException e1) {
					JOptionPane.showMessageDialog(null, "Error: either Check in/out date is formatted Wrong.", "ERROR", JOptionPane.ERROR_MESSAGE);
					return;
				}
				// enforce all the constraints 
				if(checkDates(checkInDate, checkOutDate)) 
				{
					frame.remove(preReservationPanel);
					frame.setTitle("Room Availability");
					frame.add(reservationPanel);
					frame.pack();
					frame.revalidate();
					//reservationFrame.setVisible(true);
					RoomAvailabilityModel.mutator(checkInDate, checkOutDate, (ArrayList)hotel.getAllEconomyRooms());
				}
			}
		});
		
		luxuryButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Date checkInDate = null;
				Date checkOutDate = null;
				try {
					checkInDate = dateFormat.parse(checkInField.getText());
					checkOutDate = dateFormat.parse(checkOutField.getText());
				} catch (ParseException e1) {
					//e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "Error: either Check in/out date is formatted Wrong.", "ERROR", JOptionPane.ERROR_MESSAGE);
					return;
				}
				// enforce all the constraints 
				if(checkDates(checkInDate, checkOutDate)) 
				{
					frame.remove(preReservationPanel);
					frame.setTitle("Room Availability");
					frame.add(reservationPanel);
					frame.pack();
					frame.revalidate();
					//reservationFrame.setVisible(true);
					RoomAvailabilityModel.mutator(checkInDate, checkOutDate, (ArrayList)hotel.getAllLuxoriousRooms());
				}			
			}
		});
		
		confirmButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int roomNum;
				if(roomNumberField.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Error!! Please enter a Room Number.", "ERROR", JOptionPane.ERROR_MESSAGE);
					return;
				}
				try {
					roomNum = Integer.parseInt(roomNumberField.getText());
				} catch (NumberFormatException e1) {
					JOptionPane.showMessageDialog(null, "Error!! Invalid Room Number!", "ERROR", JOptionPane.ERROR_MESSAGE);
					return; }
				Date checkInDate = null;
				Date checkOutDate = null;
				try {
					checkInDate = dateFormat.parse(checkInField.getText());
					checkOutDate = dateFormat.parse(checkOutField.getText());
				} catch (ParseException e1) {}// dates are already checked earlier
				if(roomNum < 11) { //regular room
					roomNum--;
					if(hotel.getCurrentGuest().makeReservation(checkInDate, checkOutDate, hotel.getAllEconomyRooms().get(roomNum))) {
						//System.out.println("Room Succussfully Reserved!");
					}
					else {//System.out.println("Error, room wasn't reserved.");
						JOptionPane.showMessageDialog(null, "Error!! Room cannot be reserved", "ERROR", JOptionPane.ERROR_MESSAGE);
						return;
					}
					// update view
					RoomAvailabilityModel.mutator(checkInDate, checkOutDate, (ArrayList)hotel.getAllEconomyRooms());
					roomNumberField.setText("");
				}
			else { //luxury room
				roomNum = (roomNum-1) % 10;
				if(hotel.getCurrentGuest().makeReservation(checkInDate, checkOutDate, hotel.getAllLuxoriousRooms().get(roomNum))) {
					//System.out.println("Room Succussfully Reserved!");
				}
				else {//System.out.println("Error, room wasn't reserved.");
					JOptionPane.showMessageDialog(null, "Error!! Room cannot be reserved", "ERROR", JOptionPane.ERROR_MESSAGE);
					return;
				}
				// update view
				RoomAvailabilityModel.mutator(checkInDate, checkOutDate, (ArrayList)hotel.getAllLuxoriousRooms());
				roomNumberField.setText("");
			}
			}
		});
		
		moreReservationButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.remove(reservationPanel);
				frame.setTitle("Make Reservation");
				frame.add(preReservationPanel);
				frame.pack();
				frame.revalidate();
				
				//reservationFrame.setVisible(false);
				roomNumberField.setText("");
				//checkInField.setText("");
				//checkOutField.setText("");
			}
		});
		
		doneButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//reservationFrame.setVisible(false);
				roomNumberField.setText("");
				checkInField.setText("");
				checkOutField.setText("");
				
				frame.setTitle("Hotel Reservation System");
				frame.remove(reservationPanel);
				
				frame.add(reciptSelectPanel);
				frame.pack();
				frame.revalidate();
			}
		});
		
		simpleButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				recieptArea.setText(hotel.getCurrentGuest().makeReceipt(new SimpleReceipt()));
				frame.remove(reciptSelectPanel);
				frame.add(recieptPanel);
				frame.pack();
				frame.revalidate();
			}
		});
		
		comprehensiveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				recieptArea.setText(hotel.getCurrentGuest().makeReceipt(new ComprehensiveReceipt()));
				frame.remove(reciptSelectPanel);
				frame.add(recieptPanel);
				frame.pack();
				frame.revalidate();
			}
		});
		
		printButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.remove(recieptPanel);
				frame.add(signedInPanel);
				frame.setSize(400, 100);
				frame.revalidate();
			}
		});

		frame.add(mainPanel);
		frame.setSize(250, 70);
		frame.revalidate();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	//return true if there are no errors; false otherwise
	private static boolean checkDates(Date checkInDate, Date checkOutDate)
	{
		Date today = new Date();
		Date myDate = new Date(today.getYear(),today.getMonth(),today.getDate());
		//make sure Check In date is not after Check Out date
		if(checkInDate.compareTo(checkOutDate)>0)
		{
			JOptionPane.showMessageDialog(null, "Error!! Invalid Request\nCheck In date cannot be after Check Out date.", "ERROR", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		//make sure Check In and Check Out date is not before today
		else if(checkInDate.compareTo(myDate)<0 ||checkOutDate.compareTo(myDate)<0)
		{
			JOptionPane.showMessageDialog(null, "Error!! Invalid Request\nCheck In/Out date cannot be in the past.", "ERROR", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		//make sure Check In and Check Out date is not on the same day
		else if(Duration.of(checkOutDate.getTime() - checkInDate.getTime(), ChronoUnit.MILLIS).toDays() < 1)
		{
			JOptionPane.showMessageDialog(null, "Error!! Invalid Request\nThe length of stay cannot be less than a night.", "ERROR", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		//make sure Check In and Check Out date is not more than 60 nights
		else if(Duration.of(checkOutDate.getTime() - checkInDate.getTime(), ChronoUnit.MILLIS).toDays() > 60)
		{
			JOptionPane.showMessageDialog(null, "Error!! Invalid Request\nThe length of stay cannot be longer than 60 nights.", "ERROR", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}
	
	// model for MVC
	private class RoomAvailabilityPanel
	{
		private String label;
		private String textArea;
		private ArrayList<ChangeListener> views;
		
		public RoomAvailabilityPanel() {
			label = "";
			textArea = "";
			this.views = new ArrayList<ChangeListener>();
		}
		public String getLabel() {
			return label;
		}
		
		public String getTextArea() {
			return textArea;
		}
		
		public void mutator(Date checkIn, Date checkOut, ArrayList<Room> rooms) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
			label = " Available Rooms "+ dateFormat.format(checkIn)+"-"+dateFormat.format(checkOut);
			String room = "";
			for(int i = 0; i<rooms.size(); i++) {
				Room r = rooms.get(i);
				if(r.isAvailable(checkIn, checkOut)) {
					if(i==rooms.size()-1) room+=r.getRoomId();
					else if(i==(rooms.size()-1)/2) room+=(r.getRoomId()+",\n" );
					else room+=(r.getRoomId()+", " );
				}
			}
			textArea = room;
			//System.out.println("textArea: "+textArea);
			notifyView();
		}
		
		private void notifyView() {
			ChangeEvent e = new ChangeEvent(this);
			for(ChangeListener c: views){
				c.stateChanged(e);
			}
		}
		
		private void addListner(ChangeListener cl) {
			views.add(cl);		
		}
	}
}

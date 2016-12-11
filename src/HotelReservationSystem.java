import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
* <h1>Hotel Reservation System</h1>
* The Hotel Reservation System program implements a reservation system for a small hotel.
* 
* <p>

* @author 
* @version 
*
*/

public class HotelReservationSystem {

	/**This main method is responsible for the 
	 * view of the application for the users who sign in
	 * Different panels have been used to change between views based on user input
	 * don't know if this is the best approach to change between view on the same frame,
	 * @param args
	 */
	
	public static void main(String[] args) 
	{
		JFrame frame = new JFrame("Hotel Reservation System");
		Hotel hotel = Hotel.getInstance();
		hotel.load();
		
		
		
		/******** TESTING PURPOSES **************/
		
		GuestUser guest = hotel.createGuestUser(1, "test");
		//hotel.setCurrentGuest(guest);
		Room r101 = hotel.getAllLuxoriousRooms().get(0);
		Room r7 = hotel.getAllEconomyRooms().get(6);
		if( guest.makeReservation(new Date(2016-1900, 11, 5), new Date(2017-1900, 0, 5), r101) )
			System.out.println("successfully booked room number: " + r101.getRoomId());
		else System.out.println("Error: room wasn't booked");
		//System.out.println(r101.reserved(new Date(2016-1900, 11, 1)).getUserName());
		System.out.println(r101.reserved(new Date(2016-1900, 11, 7)).getUserName());
		System.out.println(r101.isAvailable(new Date(2016-1900, 11, 2), new Date(2017-1900, 0, 4)));
		System.out.println(r101.isAvailable(new GregorianCalendar().getTime(), new GregorianCalendar().getTime()));
		System.out.println(r101.isAvailable(new Date(2017-1900, 0, 12), new Date(2017-1900, 0, 12)));
		/*for(DayReservedEntry d: r101.getDayReservations()) {
			System.out.printf("DayReservedEntry for room %d: %s%n", r101.getRoomId(), d.getDay().toString());
		}*/
		//System.out.println(r101.get(new Date(2017-1900, 0, 12)).getDay().toString());
		///*
		guest.makeReservation(new Date(2016-1900, 11, 22), new Date(2016-1900, 11, 24), r7);
		System.out.println("successfully booked room number: " + r7.getRoomId());
		//System.out.println(r7.reserved(new Date(2016-1900, 11, 1)).getUserName());
		//System.out.println(r7.reserved(new Date(2016-1900, 11, 28)).getUserName());
		System.out.println(r7.isAvailable(new Date(2016-1900, 11, 2), new Date(2017-1900, 0, 4)));
		System.out.println(r7.isAvailable(new GregorianCalendar().getTime(), new GregorianCalendar().getTime()));
		System.out.println(r7.isAvailable(new Date(2016-1900, 11, 2), new Date(2016-1900, 11, 4)));
		System.out.println(r7.isAvailable(new Date(2017-1900, 0, 2), new Date(2017-1900, 0, 4)));
		//*/
		
		/*********UNTIL HERE**********************/
		
		
		
		//replace error with JOptionPane.show...(error message)
		//only used by signIn and signUp panel
		JTextArea error = new JTextArea("error");
		error.setEditable(false);
		error.setForeground(Color.RED);

		/* Sign in and Sign up Panel */
		
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

		/* Panel for signed in users to make, view or cancel reservations */
		
		JPanel signedInPanel= new JPanel();
		JButton makeReservBtn=new JButton("Make Reservation");
		JButton viewCancelButn=new JButton("View/Cancel Reservation");
		signedInPanel.add(makeReservBtn);
		signedInPanel.add(viewCancelButn);

		JPanel preReservationPanel=new JPanel();
		preReservationPanel.setLayout(new BoxLayout(preReservationPanel,BoxLayout.Y_AXIS));
		
		/* Panel for signed in users to choose dates and room type*/
		
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

		/* Shows the available rooms for the selected dates on the frame */
		
		// used in one of the buttons in preReservationPanel
		JFrame reservationFrame = new JFrame("Room Availability");
		
		JPanel reservationPanel=new JPanel();
		reservationPanel.setLayout(new BoxLayout(reservationPanel,BoxLayout.X_AXIS));
		
		final RoomAvailabilityPanel RoomAvailabilityModel = (new HotelReservationSystem()).new RoomAvailabilityPanel();
		JPanel roomAvailablePanel=new JPanel(new BorderLayout());
		final JLabel availableRoomsLabel=new JLabel(" Available Rooms            "+ "Start-End           ");
		final JTextArea roomList = new JTextArea();
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

		/* Asks the user to enter the rooms to reserve and confirm it alo gives option
		 * to make multiple reservations */
		
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
		
		reservationFrame.add(reservationPanel);
		reservationFrame.setLocation(0, 162);
		reservationFrame.pack();
		reservationFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		reservationFrame.setVisible(false);

		/* Panel for users to sign up using name and id*/
		
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

		/* Manager panel with options to Load and view reservations */
		
		JPanel managerPanel = new JPanel();
		JButton loadButton = new JButton("Load");
		JButton viewButton = new JButton("View");
		viewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				hotel.getManager().getViewFrame(hotel.getAllRooms());
			}
		});

	/* Save Button for the manager to save the reservations */
		
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

		/* First Panel with options for user or manager */
		
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
			}
		});
		mainPanel.add(userButton);
		mainPanel.add(managerButton);
		frame.add(mainPanel);

		/*** All the button listeners from here on ***/
		
		signInBtnInitial.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				frame.remove(userPanel);
				signInPanel.add(error);
				frame.add(signInPanel);
				frame.setSize(400, 90);
				frame.revalidate();
				//frame.pack();
			}
		});
		signUpBtnInitial.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				frame.remove(userPanel);
				signUpPanel.add(error);
				frame.add(signUpPanel);
				//frame.setSize(400, 200);
				frame.pack();
			}
		});

		signInBtnActual.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				if(id_signInField.getText().equals(""))
				{
					error.setText("Error: please enter an id");
				}
				else {
					GuestUser u = hotel.findUser(Integer.parseInt(id_signInField.getText()));
					if(u == null)
						error.setText("Error: please enter a valid id");
					else {
						hotel.setCurrentGuest(u);
						signInPanel.remove(error);
						frame.remove(signInPanel);
						frame.add(signedInPanel);
						frame.setSize(400, 70);
						frame.revalidate();
						//frame.pack();
					}
				}
			}
		});
		
		/* Action Listener for sign up button it checks to see 
		 * validation of the id that can be used to sign up 
		 */
		
		signUpBtnActual.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(id_signUpField.getText().equals(""))
					error.setText("Error: please enter an id");
				if(name_signUpField.getText().equals(""))
					error.setText("Error: please enter name");
				else if(!id_signUpField.getText().equals("")) {
					int id = Integer.parseInt(id_signUpField.getText());
					String name = name_signUpField.getText();
					GuestUser u = hotel.findUser(id);
					if(u == null)
						u = hotel.createGuestUser(id, name);
					else
						error.setText("Error: user with this id already exists, please choose another id");
					hotel.setCurrentGuest(u);
					signUpPanel.remove(error);
					frame.remove(signUpPanel);
					frame.add(signedInPanel);
					frame.setSize(400, 70);
					frame.revalidate();
				}
		}
	});

		makeReservBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				frame.remove(signedInPanel);
				frame.add(preReservationPanel);
				//frame.setSize(400, 200);
				frame.pack();
			}
		});
		
		/* Taking dates from the check in and check out dates text field */
		
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
					reservationFrame.setVisible(true);
					RoomAvailabilityModel.mutator(checkInDate, checkOutDate, (ArrayList)hotel.getAllEconomyRooms());
				}
			}
		});
		
		 /* Listener for luxury room */
		
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
					reservationFrame.setVisible(true);
					RoomAvailabilityModel.mutator(checkInDate, checkOutDate, (ArrayList)hotel.getAllLuxoriousRooms());
				}			
			}
		});
		
		/* Confirmation button listener */
		
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
					if(hotel.getCurrentGuest().makeReservation(checkInDate, checkOutDate, hotel.getAllEconomyRooms().get(roomNum)))
					{
						System.out.println("Room Succussfully Reserved!");
					}
					else System.out.println("Error, room wasn't reserved."); 
				}
				else { //luxury room
					roomNum = roomNum % 10 - 1;
					if(hotel.getCurrentGuest().makeReservation(checkInDate, checkOutDate, hotel.getAllLuxoriousRooms().get(roomNum)))
					{
						System.out.println("Room Succussfully Reserved!");
					}
					else System.out.println("Error, room wasn't reserved."); 
				}
			}
		});
		
		moreReservationButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reservationFrame.setVisible(false);
				checkInField.setText("");
				checkOutField.setText("");
			}
		});


		//frame.pack();
		frame.setSize(250, 70);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	

	 /**
	   * This method is used to check the validity of the check in and check out dates
	   * by checking if the dates are in the past or check out date is before check in date
	   * or the stay is more than 60 nights
	   * @param checkInDate first parameter : date of check in
	   * @param checkOutDate second parameter : date of check out
	   * @return boolean this returns the validity of the stay
	   */
	
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
		//make sure Check In and Check Out date is not more than 60 nights
		// in this method (in general) 1 day is considered 1 night stay
		// i.e. a Reservation from 12/01/2016 to 12/01/2016 => 1 night stay
		else if(Duration.of(checkOutDate.getTime() - checkInDate.getTime(), ChronoUnit.MILLIS).toDays() > 60)
		{
			JOptionPane.showMessageDialog(null, "Error!! Invalid Request\nThe length of stay cannot be longer than 60 nights.", "ERROR", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}
	
	/**
	 * Inner class RoomAvailabilty panel to show the available all the rooms
	 * 
	 */
	
	private class RoomAvailabilityPanel
	{
		private String label;
		private String textArea;
		private ArrayList<ChangeListener> views;
	/**
	 * Default Constructor to initialize the  label, textArea and views
	 */
		
		public RoomAvailabilityPanel() {
			label = "";
			textArea = "";
			this.views = new ArrayList<ChangeListener>();
		}
		
		/**
		 * Accessor to return label
		 * @return this.label
		 */
		
		public String getLabel() {
			return label;
		}
		
		/**
		 * Accessor to return textArea
		 * @return this.textArea
		 */
		
		public String getTextArea() {
			return textArea;
		}
		
		/*This mutator methods sets textArea field and display with all the available rooms
		 * by calling isAvailable method
		 * @param checkInDate first parameter : date of check in
		 * @param checkOutDate second parameter : date of check out
		 * @param rooms third parameter : ArrayList of room to store the available rooms
		 * 
		 */
		
		public void mutator(Date checkIn, Date checkOut, ArrayList<Room> rooms) {
			label = " Available Rooms "+ convetToString(checkIn)+"-"+convetToString(checkOut);
			String room = "";
			for(int i = 0; i<rooms.size(); i++) {
				Room r = rooms.get(i);
				if(r.isAvailable(checkIn, checkOut)) {
					if(i==rooms.size()-1) room+=r.getRoomId();
					else if(i==rooms.size()/2) room+=(r.getRoomId()+",\n" );
					else room+=(r.getRoomId()+", " );
				}
			}
			textArea = room;
			System.out.println("textArea: "+textArea);
			notifyView();
		}
		/**
		 * Method to convert the date to a string format
		 * @param date : date in Date format
		 * @return String 
		 */
		
		private String convetToString(Date date) {
			return (""+ (date.getMonth()+1) +"/"+date.getDate()+"/"+(date.getYear()-100));
		}
		
		/**
		 * This methods notifies the view with the chanage in state
		 */
		
		private void notifyView() {
			ChangeEvent e = new ChangeEvent(this);
			for(ChangeListener c: views){
				c.stateChanged(e);
			}
		}
		
		/**
		 * This method adds the ChangeListener to each of member of view ArrayList
		 * @param cl : parameter of type ChangeListener
		 */
		
		private void addListner(ChangeListener cl) {
			views.add(cl);		
		}
	}
}

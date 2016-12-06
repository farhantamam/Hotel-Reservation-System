import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.*;

public class HotelReservationSystem {

	/**
	 * using different panels to change between views based on user input
	 * don't know if this is the best approach to change between view on the same frame,
	 * it is meant to be a starting point for GUI of this application
	 * @param args
	 */
	public static void main(String[] args) 
	{
		SimpleDateFormat formatter = new SimpleDateFormat("MM/DD/YYYY");
		//final Date startDate;
		//Date endDate;
		
		JFrame frame = new JFrame("Hotel Reservation System");
		
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
		JButton viewCancelButn=new JButton("View/Cancel Reservation");
		signedInPanel.add(makeReservBtn);
		signedInPanel.add(viewCancelButn);
		
		JPanel preReservationPanel=new JPanel();
		preReservationPanel.setLayout(new BoxLayout(preReservationPanel,BoxLayout.Y_AXIS));
		
		JPanel checkInPanel = new JPanel();
		checkInPanel.setLayout(new GridLayout(2,2));
		JLabel checkInLabel=new JLabel("Check in");
		JLabel checkOutLabel=new JLabel("Check out");
		JTextField checkInField= new JTextField(10);
		JTextField checkOutField= new JTextField(10);
		checkInPanel.add(checkInLabel);
		checkInPanel.add(checkOutLabel);
		checkInPanel.add(checkInField);
		checkInPanel.add(checkOutField);
		
		JPanel roomTypePanel= new JPanel();
		JLabel roomTypeLabel=new JLabel("Room type");
		JButton regularButton=new JButton("$200");
		JButton luxuryButton =new JButton("$80");
		JButton nextButton =new JButton("Next");
		
		roomTypePanel.add(roomTypeLabel);
		roomTypePanel.add(luxuryButton);
		roomTypePanel.add(regularButton);
		roomTypePanel.add(nextButton);
		
		
		preReservationPanel.add(checkInPanel);
		preReservationPanel.add(roomTypePanel);
		
		JPanel reservationPanel=new JPanel();
		reservationPanel.setLayout(new BoxLayout(reservationPanel,BoxLayout.X_AXIS));
		
		JPanel roomAvailablePanel=new JPanel();
		JLabel availableRoomsLabel=new JLabel(" Available Rooms "+ "Start-End");
		
		roomAvailablePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		
		
		roomAvailablePanel.add(availableRoomsLabel);
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
		
		JPanel managerPanel = new JPanel();
		JButton loadButton = new JButton("Load");
		JButton viewVutton = new JButton("View");
		JButton saveButton = new JButton("Save");
		JButton quitButton = new JButton("Quit");
		managerPanel.add(loadButton);
		managerPanel.add(viewVutton);
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
				frame.setSize(400, 200);
			}
		});
		
		JButton managerButton = new JButton("Manager");
		managerButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				frame.remove(mainPanel);
				frame.add(managerPanel);
				frame.setSize(400, 200);
			}
		});
		mainPanel.add(userButton);
		mainPanel.add(managerButton);
		frame.add(mainPanel);
		
		//all the button listeners
		signInBtnInitial.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				frame.remove(userPanel);
				frame.add(signInPanel);
				//frame.setSize(400, 200);
				frame.pack();
			}
		});
		signUpBtnInitial.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				frame.remove(userPanel);
				frame.add(signUpPanel);
				frame.setSize(400, 200);
				//frame.pack();
			}
		});
		
		signInBtnActual.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				frame.remove(signInPanel);
				frame.add(signedInPanel);
				frame.setSize(400, 200);
				//frame.pack();
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
		
		nextButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				String checkInDate = checkInField.getText();
				String checkOutDate=checkOutField.getText();
				/*try
				{
				Date startDate= formatter.parse(checkInDate);
				Date endDate=formatter.parse(checkOutDate);
				System.out.println(new Date());
				/*if(startDate.compareTo(endDate)>0)
				{
					JOptionPane.showMessageDialog(null, "Error!! Invalid Request");
					frame.add(preReservationPanel);
				}
				else	
				if(startDate.compareTo(new Date())<0||endDate.compareTo(new Date())<0)
				{
					JOptionPane.showMessageDialog(null, "Error!! Invalid Request");
					frame.add(preReservationPanel);
				}
				else
				*/
				frame.remove(preReservationPanel);
				frame.add(reservationPanel);
				//frame.setSize(400, 200);
				frame.pack();
				//System.out.println(startDate);
				//System.out.println(endDate);
				}
				/*}
				
				catch(ParseException p)
				{
					p.printStackTrace();
				}
				
				*/
		//}
		});

		
		//frame.pack();
		frame.setSize(500, 250);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

}

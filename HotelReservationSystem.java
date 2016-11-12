import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
		JFrame frame = new JFrame("Hotel Reservation System");
		
		JPanel userPanel = new JPanel();
		JButton signInBtnInitial = new JButton("Sign In");		
		JButton signUpBtnInitial = new JButton("Sign Up");
		userPanel.add(signInBtnInitial);
		userPanel.add(signUpBtnInitial);
		
		JPanel signInPanel = new JPanel();
		JLabel id_signIn_Label = new JLabel("id:");
		JTextField id_signInField = new JTextField(20);
		JButton signInBtnActual = new JButton("Sign Up");
		signInPanel.add(id_signIn_Label);
		signInPanel.add(id_signInField);
		signInPanel.add(signInBtnActual);
		
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
				//frame.setSize(400, 200);
				frame.pack();
			}
		});
		
		
		//frame.pack();
		frame.setSize(500, 250);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

}

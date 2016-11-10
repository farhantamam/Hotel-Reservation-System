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
		
		JPanel signInPanel = new JPanel();
		JButton signInButton = new JButton("Sign In");
		JButton signUpButton = new JButton("Sign Up");
		signInPanel.add(signInButton);
		signInPanel.add(signUpButton);
		
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
				frame.add(signInPanel);
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
		
		//frame.pack();
		frame.setSize(500, 250);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

}

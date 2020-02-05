import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JTextField;
import javax.swing.JButton;

public class UI {		// Front end

	static JFrame frame;
	private static JFormattedTextField arrivalDate;
	private static JFormattedTextField returnDate;
	private static JTextField numOfTravelers;
	private static JCheckBox checkboxTurkish;
	private static JCheckBox checkboxAegean;
	private static JCheckBox checkboxComfy;
	private static JCheckBox checkboxMaldives;
	
	
	public static String getArrivalDate() {
		return arrivalDate.getText();
	}
	
	public static String getReturnDate() {
		return returnDate.getText();
	}
	
	public static int getNumOfTravelers() {
		return Integer.parseInt(numOfTravelers.getText());
	}
	
	public static int getAirlinePreference() {
		if(checkboxTurkish.isSelected() && !(checkboxAegean.isSelected()))
			return 2;
		else if(checkboxAegean.isSelected() && !(checkboxTurkish.isSelected()))
			return 1;
		else
			return 0;
	}
	
	public static int getHotelPreference() {
		if(checkboxComfy.isSelected() && !(checkboxMaldives.isSelected()))
			return 1;
		else if(checkboxMaldives.isSelected() && !(checkboxComfy.isSelected()))
			return 2;
		else
			return 0;
	}
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UI window = new UI();
					UI.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @throws InterruptedException 
	 */
	public UI() throws InterruptedException {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws InterruptedException 
	 */
	private void initialize() throws InterruptedException {
		frame = new JFrame();
		frame.setBounds(100, 100, 721, 495);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblSd = new JLabel("Travel Agency");
		lblSd.setFont(new Font("Tahoma", Font.PLAIN, 26));
		lblSd.setHorizontalAlignment(SwingConstants.CENTER);
		lblSd.setBounds(10, 10, 687, 40);
		frame.getContentPane().add(lblSd);
		
		checkboxComfy = new JCheckBox("Comfy Hotel");
		checkboxComfy.setFont(new Font("Tahoma", Font.PLAIN, 16));
		checkboxComfy.setBounds(525, 107, 172, 21);
		frame.getContentPane().add(checkboxComfy);
		
		checkboxMaldives = new JCheckBox("Maldives Hotel");
		checkboxMaldives.setFont(new Font("Tahoma", Font.PLAIN, 16));
		checkboxMaldives.setBounds(525, 130, 172, 21);
		frame.getContentPane().add(checkboxMaldives);
		
		JLabel lblHotelPreferences = new JLabel("Hotel Preferences");
		lblHotelPreferences.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblHotelPreferences.setBounds(510, 72, 172, 29);
		frame.getContentPane().add(lblHotelPreferences);
		
		JLabel lblAirlinePreferences = new JLabel("Airline Preferences");
		lblAirlinePreferences.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblAirlinePreferences.setBounds(510, 166, 172, 29);
		frame.getContentPane().add(lblAirlinePreferences);
		
		checkboxTurkish = new JCheckBox("Turkish Airlines");
		checkboxTurkish.setFont(new Font("Tahoma", Font.PLAIN, 16));
		checkboxTurkish.setBounds(525, 201, 172, 21);
		frame.getContentPane().add(checkboxTurkish);
		
		checkboxAegean = new JCheckBox("Aegean Airlines");
		checkboxAegean.setFont(new Font("Tahoma", Font.PLAIN, 16));
		checkboxAegean.setBounds(525, 223, 172, 21);
		frame.getContentPane().add(checkboxAegean);
		
		JLabel lblNumberOfTravelers = new JLabel("Number of Travelers");
		lblNumberOfTravelers.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNumberOfTravelers.setBounds(24, 227, 236, 29);
		frame.getContentPane().add(lblNumberOfTravelers);
		
		JLabel lblArrivalDate = new JLabel("Arrival Date");
		lblArrivalDate.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblArrivalDate.setBounds(24, 72, 236, 29);
		frame.getContentPane().add(lblArrivalDate);
		
		JLabel lblDepartureDate = new JLabel("Return Date");
		lblDepartureDate.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblDepartureDate.setBounds(24, 148, 236, 29);
		frame.getContentPane().add(lblDepartureDate);
		
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		
		arrivalDate = new JFormattedTextField(df);
		arrivalDate.setText("02/04/2016");
		arrivalDate.setBounds(34, 110, 96, 19);
		frame.getContentPane().add(arrivalDate);
		arrivalDate.setColumns(10);
		arrivalDate.addKeyListener(new KeyAdapter() {
		    public void keyTyped(KeyEvent e) {
		      char c = e.getKeyChar();
		      if (!((c >= '0') && (c <= '9') ||
		         (c == KeyEvent.VK_BACK_SPACE) ||
		         (c == KeyEvent.VK_DELETE) || (c == KeyEvent.VK_SLASH)))        
		      {
		        JOptionPane.showMessageDialog(null, "Please enter the date in day/month/year format.");
		        e.consume();
		      }
		    }
		  });
		
		
		returnDate = new JFormattedTextField(df);
		returnDate.setText("04/04/2016");
		returnDate.setColumns(10);
		returnDate.setBounds(34, 187, 96, 19);
		frame.getContentPane().add(returnDate);
		returnDate.addKeyListener(new KeyAdapter() {
		    public void keyTyped(KeyEvent e) {
		      char c = e.getKeyChar();
		      if (!((c >= '0') && (c <= '9') ||
		         (c == KeyEvent.VK_BACK_SPACE) ||
		         (c == KeyEvent.VK_DELETE) || (c == KeyEvent.VK_SLASH)))        
		      {
		        JOptionPane.showMessageDialog(null, "Please enter the date in day/month/year format.");
		        e.consume();
		      }
		    }
		  });
		
		numOfTravelers = new JFormattedTextField();
		numOfTravelers.setText("2");
		numOfTravelers.setBounds(34, 261, 96, 19);
		frame.getContentPane().add(numOfTravelers);
		numOfTravelers.setColumns(10);
		numOfTravelers.addKeyListener(new KeyAdapter() {
		    public void keyTyped(KeyEvent e) {
		      char c = e.getKeyChar();
		      if (!((c >= '1') && (c <= '9') ||
		         (c == KeyEvent.VK_BACK_SPACE) ||
		         (c == KeyEvent.VK_DELETE)))      
		      {
		        JOptionPane.showMessageDialog(null, "Please enter a valid number.");
		        e.consume();
		      }
		    }
		  });
		
		JButton sendBtn = new JButton("Send");
		sendBtn.setBounds(290, 307, 120, 40);
		frame.getContentPane().add(sendBtn);
		
		sendBtn.addActionListener(new ActionListener()
		{
		  public void actionPerformed(ActionEvent e)
		  {
			if(arrivalDate.getText().isEmpty() || returnDate.getText().isEmpty() || numOfTravelers.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Empty fields are not allowed.");
			}
			else {
				try {
					ClientHandler.main(null);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		  }
		});
	}
	
	public static void reservationQuery(String message) {
		JOptionPane.showMessageDialog(frame, message);
		
	}
	public static boolean alternateQuery(String message) {
		int dialogButton = JOptionPane.YES_NO_OPTION;
		int dialogResult = 100; // will wait
		dialogResult = JOptionPane.showConfirmDialog (null, message,"Warning",dialogButton);
		while(dialogResult == 100) {}	// wait till an option is chosen
		
		if(dialogResult == JOptionPane.YES_OPTION){
			reservationQuery("Reservation successful!");
			return true;
		}
		else if(dialogResult == JOptionPane.NO_OPTION){
		}
		return false;
	}
	
	public static void alternateItinerary(String message) throws UnknownHostException, IOException {
		int dialogButton = JOptionPane.YES_NO_OPTION;
		int dialogResult = 100; // will wait
		dialogResult = JOptionPane.showConfirmDialog (null, message,"Warning",dialogButton);
		while(dialogResult == 100) {}	// wait till an option is chosen
		
		if(dialogResult == JOptionPane.YES_OPTION){
			reservationQuery("Reservation successful!");
			ClientProposer.connectToAgency("yes");
		}
		else if(dialogResult == JOptionPane.NO_OPTION){
			ClientProposer.connectToAgency("no");
		}
	}
	
	
	
	
	
}

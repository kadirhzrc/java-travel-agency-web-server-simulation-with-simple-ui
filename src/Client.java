import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Client {	// Client level class. Handles connection back from Agency to UI. Depending on the feedback from the agency, invokes required...
	// method, or message in UI
	
	public static void main(String[] args) throws IOException {
		String str;

	    ServerSocket servSocket = new ServerSocket(8070);	// Listen to port 8070
	    System.out.println("Waiting for a connection on " + "8070");

	    Socket fromClientSocket = servSocket.accept();	// Accept the connection, block till a connection is made
	    PrintWriter pw = new PrintWriter(fromClientSocket.getOutputStream(), true);
	    BufferedReader br = new BufferedReader(new InputStreamReader(fromClientSocket.getInputStream()));
	    
	    String temp = null;
	    // Perform certain actions depending on feedback message from client (Agency)
	    while ((str = br.readLine()) != null) {
	      System.out.println("The message: " + str);
	      if (str.equals("bye")) {
	        break;
	      }
	      else if (str.equals("No available reservation for specified dates!")){
	    	  UI.reservationQuery("No available reservation for specified dates!");
	      }
	      else if(str.equals("Reservation successful!")) {
	    	  UI.reservationQuery("Reservation succesful!");
	      }
	      else if(str.equals("itinerary")) {
	    	  UI.alternateItinerary(temp);
	      }
	      else {
	        str = "Server returns " + str;
	        pw.println(str);
	      }
	      
	    }
	}
}

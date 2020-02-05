import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientProposer {	// Client level class. Handles connection from UI to Agency when an itinerary is proposed.
	public static void connectToAgency(String answer) throws UnknownHostException, IOException {
	    	Socket socket2 = new Socket("localhost", 8060);
	    	boolean autoflush = true;
	        PrintWriter out = new PrintWriter(socket2.getOutputStream(), autoflush);
	        BufferedReader in = new BufferedReader(new InputStreamReader(socket2.getInputStream()));
	        out.println(answer);	// Answer boolean variable is the user response to itinerary propostion, send it to Agency level Itinerary Handler class
	        out.println();
	}
}

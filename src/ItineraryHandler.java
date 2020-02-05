import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class ItineraryHandler {
	static boolean stop = true;
	static String temp;
	public static void main(String[] args) throws IOException {
		String str;
		
		// Handler server at Agency level. Gets itinerary response from ClientProposer class and sends it to Agency class
	    ServerSocket servSocket = new ServerSocket(8060);
	    System.out.println("Waiting for a connection on " + "8060");

	    Socket fromClientSocket = servSocket.accept();
	    PrintWriter pw = new PrintWriter(fromClientSocket.getOutputStream(), true);

	    BufferedReader br = new BufferedReader(new InputStreamReader(fromClientSocket.getInputStream()));
	    while ((str = br.readLine()) != null) {
	      System.out.println("The message: " + str); 
	      // Reserve place if itinerary is accepted
	      if (str.equals("yes")) {
	    	  Agency.sendRequest(temp,"m");
	        break;
	      }
	      else if(str.equals("no")) {
	    	  break;
	      }
	    }
	    stop = false;
	    
	    
	}
	}

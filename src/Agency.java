import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

public class Agency {	// Handles connections back and forth to HttpServers and to Client
	static Preferences preferences;
	static String itinerary;
	
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
    	Socket socket;
        ServerSocket ss = new ServerSocket(7777);
        System.out.println("ServerSocket awaiting connections...");
        socket = ss.accept();
        InputStream inputStream = socket.getInputStream();
        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
        preferences = (Preferences) objectInputStream.readObject();		// receive Preferences object
        //inputStream.close();
        //socket.close();
        
        // connect to specific HttpServers depending on the preference
       String message;
       
       if(preferences.getHotelPreference() == 1) {	// Hotel preferences: 1 for Comfy Hotel, 2 for Maldives Hotel, 0 for either
    	   message = sendRequest("comfy","m");	// Send a request to specific HttpServer
    	   if(isSuccesful(message))	// Check if the feedback is positive
    		   returnMessage(message);	// Return positive message to UI
    	   else {
    		   message = sendRequest("maldives","c");
    		   if(isSuccesful(message)) {
    			   if(Itinerary("Unable to find a place in Comfy Hotel. Would you like to reserve place in Maldives Hotel?"))
    				   sendRequest("maldives","m");	  // Finalize the request
    		   }
    		   else {
    			   returnMessage("No available reservation for specified dates!");	// If neither are available
    		   }
    	   }
       }
       else if(preferences.getHotelPreference() == 2) {	// Hotel preferences: 1 for Comfy Hotel, 2 for Maldives Hotel, 0 for either
    	   message = sendRequest("maldives","m");	// Send a request to specific HttpServer
    	   if(isSuccesful(message))	// Check if the feedback is positive
    		   returnMessage(message);	// Return positive message to UI
    	   else {
    		   message = sendRequest("comfy","c");
    		   if(isSuccesful(message)) {
    			   if(Itinerary("Unable to find a place in Maldives Hotel. Would you like to reserve place in Comfy Hotel?"))
    				  sendRequest("comfy","m");	
    		   }
    		   else {
    			   returnMessage("No available reservation for specified dates!");
    		   }
    	   }
       }
       else {	// If a choice isn't made, don't ask the user.
    	   message = sendRequest("comfy","m");
    	   if(isSuccesful(message))
    		   returnMessage(message);
    	   else {
    		   message = sendRequest("maldives","m");
    		   if(isSuccesful(message)) {
    			   returnMessage(message);
    		   }
    		   else {
    			   returnMessage("No available reservation for specified dates!");
    		   }
    	   }
       }
       
       // Same implementation for airlines
       if(preferences.getAirlinePreference() == 1) {
    	   message = sendRequest("aegean","m");
    	   if(isSuccesful(message))
    		   returnMessage(message);
    	   else {
    		   message = sendRequest("turkish","c");
    		   if(isSuccesful(message)) {
    			   if(Itinerary("Unable to find a place in Aegean Airlines. Would you like to reserve place in Turkish Airlines?"))
    				  sendRequest("turkish","m");	
    		   }
    		   else {
    			   // check if there exists another airline
    			   returnMessage("No available reservation for specified dates!");
    		   }
    	   }
       }
       else if(preferences.getAirlinePreference() == 2) {
    	   message = sendRequest("turkish","m");
    	   if(isSuccesful(message))
    		   returnMessage(message);
    	   else {
    		   message = sendRequest("aegean","c");
    		   if(isSuccesful(message)) {
    			   if(Itinerary("Unable to find a place in Turkish Airlines. Would you like to reserve place in Aegean Airlines?"))
    				  sendRequest("aegean","m");	
    		   }
    		   else {
    			   returnMessage("No available reservation for specified dates!");
    		   }
    	   }
       }
       else {
    	   message = sendRequest("aegean","m");
    	   if(isSuccesful(message))
    		   returnMessage(message);
    	   else {
    		   message = sendRequest("turkish","m");
    		   if(isSuccesful(message)) {
    				  sendRequest("maldives","m");	
    		   }
    		   else {
    			   returnMessage("No available reservation for specified dates!");
    		   }
    	   }
       }
       
    }
    // Send itinerary message to return itinerary method
    public static void itinerary(String message) throws UnknownHostException, IOException, InterruptedException {
    	returnItineraryMessage(message);
    }
    // Waits until a response from itinerary arrives. Uses ItineraryHandler class' temp string
    public static boolean waitForItinerary() throws InterruptedException {
    	System.out.println(ItineraryHandler.temp);
    	if(ItineraryHandler.temp.equals("yes"))
    		return true;
    	else
    		return false;
    	
    	
    }
    
    // Sends request to HttpServers. Takes comapny name and type of request. c for checking availability m for reserving space
    public static String sendRequest(String company, String typeOfRequest) throws UnknownHostException, IOException {
    	int port = getPort(company);	// Each server has its own port. Get the port
    	Socket socket = new Socket("localhost", port);
    	boolean autoflush = true;
        PrintWriter out = new PrintWriter(socket.getOutputStream(), autoflush);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        
        // Send HTTP GET message to the server.
        out.println("GET /" + preferences.arrivalDate + "-" + preferences.returnDate + "-" + preferences.numOfTravelers + "-" + typeOfRequest + " HTTP/1.1");
        out.println("Host: localhost:" + port);
        out.println("Connection: Close");
        out.println();
        
        // Get the response from server
        String line = null, temp = null;
        int counter = 0;
        while ((line = in.readLine()) != null) {
        	if(counter==2)
        		temp = line;
        	counter++;
        }
        // Return response from server
        return temp;
    }
    
    // Depending on the company name, return the port number
    public static int getPort(String company) {
    	if(company.equals("aegean"))
    		return 8080;
    	else if(company.equals("turkish"))
    		return 8081;
    	else if(company.equals("comfy"))
    		return 8082;
    	else
    		return 8083;
    }
    
    //	Return message to Client server
    public static void returnMessage(String message) throws UnknownHostException, IOException {
    	Socket socket2 = new Socket("localhost", 8070);
    	boolean autoflush = true;
        PrintWriter out = new PrintWriter(socket2.getOutputStream(), autoflush);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket2.getInputStream()));
        out.println(message);
        out.println("bye");

    }
    
    // When an itinerary is needed send a specialized message to invoke connection to ItineraryHandler
    public static void returnItineraryMessage(String message) throws UnknownHostException, IOException, InterruptedException {
    	Socket socket2 = new Socket("localhost", 8070);
    	boolean autoflush = true;
        PrintWriter out = new PrintWriter(socket2.getOutputStream(), autoflush);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket2.getInputStream()));
        out.println(message);
        out.println("itinerary");
        waitForItinerary();
    }
    
    // Turn message into a boolean response and return it
    public static boolean isSuccesful(String message) {
    	if(message.equals("No reservation available!"))
    		return false;
    	else {
    		return true;
    	}
    }
    
    
    
    
    
    
    
    
    
    
    public static boolean Itinerary(String message) throws UnknownHostException, IOException {
    	return Itinerary.run(message);
    }
    
   

    
    
}
    
    
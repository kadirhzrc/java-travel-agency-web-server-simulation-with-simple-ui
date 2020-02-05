import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class HttpServerAegean {	// HTTP server for airline
	static String arrivalDate, returnDate;
	static int numOfTravelers;
	static String method;
	// Capacity is set to 5
	final static int capacity = 5;
	
	 public static void main(String[] args) throws Exception { 
		 final ServerSocket server = new ServerSocket(8080);  // Listens at port 8080
		 System.out.println("Listening for connection on port 8080 ...."); 
		 
		 while (true) { // Spin forever and wait for a connection as if it's a server running on a separate machine
			 try (Socket socket = server.accept()) { // Block until a connection is made
				 
				 BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));	
				 String inputLine, request = null;
				 int counter = 0;
				 // Receive HTTP requests, only parse the first GET message
				 while (!(inputLine = in.readLine()).equals("")) {
					    if(counter==0)
					    	request = inputLine;	// Set GET message to request string
					    counter++;
				 }
				 // Successfully received GET request
				 parseRequest(request);	// Parse GET request
				 String httpResponse = "HTTP/1.1 200 OK\r\n\r\n";	// Prepare an HTTP OK message
				 
				 if(checkComfy()==true) // Checks whether the conditions are available for reservation, returns boolean
					 httpResponse = httpResponse + "Reservation successful!";	// If successful
				 else 
					 httpResponse = httpResponse + "No reservation available!";	// If failed
				 
				 socket.getOutputStream().write(httpResponse.getBytes("UTF-8")); 	// Send answer to the request back to Agency
			}
		 }
	 }
	 
	 // Checks whether the airline is available
	 public static boolean checkComfy() throws IOException {
		 File file = new File("AegeanAirlines.txt").getAbsoluteFile();	// Get database .txt file
		 BufferedReader br = new BufferedReader(new FileReader(file)); // Read from buffer
		 String str = arrivalDate;
		 String temp;
		 int capacityCounter = 0;
		 for(int k=0; k<2; k++) {	// Check arrival and return dates in the database for availability
			 while((temp=br.readLine())!=null && temp.length()!=0) {
				 if(str.equals(temp))
					 capacityCounter++;
				 if((capacityCounter + numOfTravelers) > capacity)
					 return false;	// If it's not available
			 }
			 capacityCounter = 0;
			 str = returnDate;
			 br.close();
			 br = new BufferedReader(new FileReader(file));
		 }
		 
		 // Update database if the request is "m"
		 if(method.equals("m")) {
			 try
			 {
				 String filename= "AegeanAirlines.txt";
				 FileWriter fw = new FileWriter(filename,true); //the true will append the new data
				 str = arrivalDate;
				 // Modify database by appending new entries to the .txt file
				 for(int m=0;m<2;m++) {
					 for(int k=0;k<numOfTravelers; k++) {
						 fw.write(str + "\n");
					 }
					 str = returnDate; 
				 }
				 fw.close();
			 }
			 catch(IOException ioe)
			 {
				 System.err.println("IOException: " + ioe.getMessage());
			 }
		 }
		 return true;
		 
	 }
	 
	 // Parses the GET request into variables
	 public static void parseRequest(String request) {
		 request = request.substring(5,request.length());	
		 request = request.substring(0,request.indexOf(" "));

		 arrivalDate = request.substring(0,10);	// Arrival date

		 returnDate = request.substring(11,21);	// Return date

		 numOfTravelers = Integer.parseInt(request.substring(22,23));	// Number of travelers 
		 
		 method =  request.substring(24,25);	// "m" for modify database, "c" for check for space

	 }
	 
	 
	 
	 
}
	 

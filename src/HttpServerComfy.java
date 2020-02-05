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

public class HttpServerComfy {
	static String arrivalDate, returnDate;
	static int numOfTravelers;
	static String method;
	final static int capacity = 5;
	
	 public static void main(String[] args) throws Exception { 
		 final ServerSocket server = new ServerSocket(8082); 
		 System.out.println("Listening for connection on port 8082 ...."); 
		 
		 while (true) { // spin forever } } }
			 try (Socket socket = server.accept()) { 
				 
				 BufferedReader in = new BufferedReader(

			                new InputStreamReader(socket.getInputStream()));
				 String inputLine, request = null;
				 int counter = 0;
				 while (!(inputLine = in.readLine()).equals("")) {
					    if(counter==0)
					    	request = inputLine;
					    counter++;
				 }
				 parseRequest(request);
				 String httpResponse = "HTTP/1.1 200 OK\r\n\r\n";
				 if(checkComfy()==true) 
					 httpResponse = httpResponse + "Reservation successful!";
				 else 
					 httpResponse = httpResponse + "No reservation available!";
				 
				 socket.getOutputStream().write(httpResponse.getBytes("UTF-8")); 
				 //server.close();
			}
		 }
	 }
	 
	 
	 public static boolean checkComfy() throws IOException {
		 File file = new File("ComfyHotel.txt").getAbsoluteFile();	// get comfyHotel.txt
		 BufferedReader br = new BufferedReader(new FileReader(file)); // read from buffer
		 String str = arrivalDate;
		 String temp;
		 int capacityCounter = 0;
		 while(!str.equals(returnDate)) {
			 while((temp=br.readLine())!=null && temp.length()!=0) {
				 if(str.equals(temp))
					 capacityCounter++;
				 if((capacityCounter + numOfTravelers) > capacity)
					 return false;
			 }
			 capacityCounter = 0;
			 str = addOneDay(str);
			 br.close();
			 br = new BufferedReader(new FileReader(file));
		 }
		 
		 // update database
		 if(method.equals("m")) {
			 try
			 {
				 String filename= "ComfyHotel.txt";
				 FileWriter fw = new FileWriter(filename,true); //the true will append the new data
				 str = arrivalDate;
				 while(!str.equals(returnDate)) {
					 for(int k=0;k<numOfTravelers; k++) {
						 fw.write(str + "\n");
					 }
					 str = addOneDay(str); 
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
	 // Converts string to date format then adds one day to the date format finally converting it back to string to return it
	  public static String addOneDay(String date) {
		  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		    LocalDate dateTemp = LocalDate.parse(date,formatter).plusDays(1);
		    return formatter.format(dateTemp).toString();
	  }
	 
	 
	 public static void parseRequest(String request) {
		 request = request.substring(5,request.length());
		 request = request.substring(0,request.indexOf(" "));

		 arrivalDate = request.substring(0,10);

		 returnDate = request.substring(11,21);

		 numOfTravelers = Integer.parseInt(request.substring(22,23));
		 
		 method =  request.substring(24,25);

	 }
	 
	 
	 
	 
}
	 

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;

public class ClientHandler {	// Client level class. Handles sending preferences object to Agency class.

    public static void main(String[] args) throws IOException {
        // Connect to server at port 7777
        Socket socket = new Socket("localhost", 7777);
        System.out.println("Connected!");
        
        // Get the output stream from the socket.
        OutputStream outputStream = socket.getOutputStream();
        // Create an object output stream from the output stream so we can send an object through it
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);

        // Create preferences object to send to the server
        Preferences preferences = new Preferences(UI.getArrivalDate(), UI.getReturnDate(), UI.getNumOfTravelers(), UI.getHotelPreference(), UI.getAirlinePreference());

        System.out.println("Sending messages to the ServerSocket");
        objectOutputStream.writeObject(preferences);
    }
    
}
import java.io.Serializable;

public class Preferences implements Serializable {	// Simple preferences class to be objectified and sent to Agency
	String arrivalDate, returnDate;
	int numOfTravelers, hotelPreference, airlinePreference;
	
	Preferences(String arrivalDate, String returnDate, int numOfTravelers, int hotelPreference, int airlinePreference) {
		this.arrivalDate = arrivalDate;
		this.returnDate = returnDate;
		this.numOfTravelers = numOfTravelers;
		this.hotelPreference = hotelPreference;
		this.airlinePreference = airlinePreference;
	}
	
	public String getArrivalDate() {
		return this.arrivalDate;
	}
	
	public String getReturnDate() {
		return this.returnDate;
	}
	
	public int returnNumOfTravelers() {
		return this.numOfTravelers;
	}
	
	public int getHotelPreference() {
		return this.hotelPreference;
	}
	
	public int getAirlinePreference() {
		return this.airlinePreference;
	}
}


package model;

public class Reservations {
    public static int countReservation = 0;
    private boolean isCheckIn;
    private String reservationID;
    private Passenger passenger;
    private Flight flight;

    public Reservations(String reservationID, Passenger passenger, Flight flight) {
        this.reservationID = reservationID;
        this.passenger = passenger;
        this.flight = flight;
        this.isCheckIn = false;
    }

    public String getReservationID() {
        return reservationID;
    }

    public boolean isIsCheckIn() {
        return isCheckIn;
    }

    public void setIsCheckIn(boolean isCheckIn) {
        this.isCheckIn = isCheckIn;
    }
    
    public void setReservationID(String reservationID) {
        this.reservationID = reservationID;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }
    
}

package model;

import java.util.Map;

public class Reservations {

    public static int countReservationAndSeat = 0;
    public static int incReservationID = 0;
    private boolean isCheckIn;
    private String reservationID;
    private Passenger passenger;
    private Flight flight;
    private int seatNumber;

    public Reservations(String reservationID, Passenger passenger, Flight flight) {
        this.reservationID = reservationID;
        this.passenger = passenger;
        this.flight = flight;
        this.isCheckIn = false;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
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

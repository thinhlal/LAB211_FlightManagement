
package model;

import java.util.Map;

public class Flight {
    private int countSeat;
    private String flightNumber;
    private String departureCity;
    private String destinationCity;
    private String departureTime;
    private String arrivalTime;
    private int availableSeats;
    private Map<Integer, Boolean> seatMap;
    private int maxSeat;

    public Flight(String flightNumber, String departureCity, String destinationCity, String departureTime, String arrivalTime, int maxSeat) {
        this.flightNumber = flightNumber;
        this.departureCity = departureCity;
        this.destinationCity = destinationCity;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.maxSeat = maxSeat;
        this.availableSeats = maxSeat;
        this.countSeat = 0;
    }

    public int getCountSeat() {
        return countSeat;
    }

    public void setCountSeat(int countSeat) {
        this.countSeat = countSeat;
    }

    public int getMaxSeat() {
        return maxSeat;
    }

    public void setMaxSeat(int maxSeat) {
        this.maxSeat = maxSeat;
    }
    public Map<Integer, Boolean> getSeatMap() {
        return seatMap;
    }

    public void setSeatMap(Map<Integer, Boolean> seatMap) {
        this.seatMap = seatMap;
    }

    
    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getDepartureCity() {
        return departureCity;
    }

    public void setDepartureCity(String departureCity) {
        this.departureCity = departureCity;
    }

    public String getDestinationCity() {
        return destinationCity;
    }

    public void setDestinationCity(String destinationCity) {
        this.destinationCity = destinationCity;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }
    
    public void getInforOfFlight(){
        System.out.println("FlightNumber: " + flightNumber + "_DepartureCity: " + departureCity + "_DestinationCity: " + destinationCity + "Start: " + departureTime + "_End: " + arrivalTime + "_Remaining seat: " + availableSeats + "_MaxseatofFlight: " + maxSeat);
    }
    public void getInforOfFlightForPas(){
        System.out.println("FlightNumber: " + flightNumber + "_DepartureCity: " + departureCity + "_DestinationCity: " + destinationCity + "Start: " + departureTime + "_Remaining seat: " + availableSeats);
    }
    @Override
    public String toString() {
        return flightNumber + "_" + departureCity + "_" + destinationCity + "_" + departureTime + "_" + arrivalTime + "_" + availableSeats + "_" + maxSeat;
    }
    
}

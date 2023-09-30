
package model;

public class Flight {
    private String flightNumber;
    private String departureCity;
    private String destinationCity;
    private String departureTime;
    private String arrivalTime;
    private String availableSeats;
    private int countSeatAvailable = 0;

    public Flight(String flightNumber, String departureCity, String destinationCity, String departureTime, String arrivalTime, String availableSeats) {
        this.flightNumber = flightNumber;
        this.departureCity = departureCity;
        this.destinationCity = destinationCity;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.availableSeats = availableSeats;
        ++countSeatAvailable;
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

    public String getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(String availableSeats) {
        this.availableSeats = availableSeats;
    }
    public void decreaseSeatAvailable(){
        countSeatAvailable--;
    }
    public boolean checkSeatAvailable(){
        if(countSeatAvailable == 0){
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        return flightNumber + "_" + departureCity + "_" + destinationCity + "_" + departureTime + "_" + arrivalTime + "_" + availableSeats;
    }
    
}

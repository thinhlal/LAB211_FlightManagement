package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import model.Flight;
import model.Passenger;

public class FlightManagementSystem {

    Scanner sc = new Scanner(System.in);
    List<Flight> listFlight = new ArrayList<>();

    private boolean askToBackToEnterAgain() {
        System.out.print("Do you want to type again?(Y/N): ");
        return sc.nextLine().trim().equalsIgnoreCase("Y");
    }

    private String createFlightNumber() {
        while (true) {
            System.out.print("Flight number: ");
            String flightNumber = sc.nextLine().trim();
            if (flightNumber.matches("^F\\d{4}$")) {
                return flightNumber;
            } else {
                System.out.println("must be follow as: Fxyzt, with xyzt is a number and no spaces");
                if (!askToBackToEnterAgain()) {
                    break;
                }
            }
        }
        return null;
    }

    private String createDepartureCity() {
        System.out.print("Departure city: ");
        return sc.nextLine().trim();
    }

    private boolean isValidTime(String time) {
        if (time.matches("^(?:(?:(?:19|20)\\d\\d)-(?:0[1-9]|1[0-2])-(?:0[1-9]|[12]\\d|3[01]) (?:[01]\\d|2[0-3]):[0-5]\\d:[0-5]\\d)$")) {
            return true;
        }
        return false;
    }

    private String createDestinationCity() {
        System.out.print("Destination city: ");
        return sc.nextLine().trim();
    }

    private String createDepartureTime() {
        String time;
        while (true) {
            System.out.print("Departure time: ");
            time = sc.nextLine();
            if (isValidTime(time)) {
                break;
            } else {
                System.out.println("Not valid time");
                if (!askToBackToEnterAgain()) {
                    time = null;
                    break;
                }
            }
        }
        return time;
    }

    private String createArrivalTime() {
        String time;
        while (true) {
            System.out.print("Arrival time: ");
            time = sc.nextLine();
            if (isValidTime(time)) {
                break;
            } else {
                System.out.println("Not valid time");
                if (!askToBackToEnterAgain()) {
                    time = null;
                    break;
                }
            }
        }
        return time;
    }
    
    private String createSeatAvailable(){
        System.out.print("Seat available: ");
        return sc.nextLine().trim();
    }

    public void addNewFlight() {
        listFlight.add(new Flight(createFlightNumber(), createDepartureCity(), createDestinationCity(), createDepartureTime(), createArrivalTime(), createSeatAvailable()));
    }
    
    private void showList(List<Flight> list){
        for (Flight flight : list) {
            System.out.println(flight.toString());
        }
    }
    
    public List availableFlightBaseOnDepartureAndArrival(){
        List<Flight> listAvailableFlight = new ArrayList<>();
        String destinationCity = createDestinationCity();
        String startTime = createDepartureTime();
        String endTime = createArrivalTime();
        for (Flight flight : listFlight) {
            if(flight.getDestinationCity().equalsIgnoreCase(destinationCity) && flight.getDepartureTime().equalsIgnoreCase(startTime) && flight.getArrivalTime().equalsIgnoreCase(endTime)){
                listAvailableFlight.add(flight);
            }
        }
        return listAvailableFlight;
    }
    
    public void makeReservation(){
        System.out.print("Enter Your Name: ");
        String passengerName = sc.nextLine().trim();
        System.out.print("Enter Contact Details: ");
        String contactDetails = sc.nextLine().trim();
        showList(availableFlightBaseOnDepartureAndArrival());
        System.out.print("Enter Flight Number to Reserve: ");
        String flightNumber = sc.nextLine().trim();
        for (Flight flight : listFlight) {
            if (flight.getFlightNumber().equalsIgnoreCase(flightNumber)) {
                Passenger passenger = new Passenger(passengerName, contactDetails);
                break;
            }
        }
    }
}

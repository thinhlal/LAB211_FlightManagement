package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import model.Flight;
import model.Passenger;
import model.Reservations;

public class FlightManagementSystem {

    Scanner sc = new Scanner(System.in);
    List<Flight> listFlight = new ArrayList<>();
    List<Passenger> listPassenger = new ArrayList<>();
    List<Reservations> listReservations = new ArrayList<>();
    Map<Integer, Boolean> listSeat;
    Map<Reservations, Map<Integer, Boolean>> listSeatWithPassenger;

    private boolean askToBackToEnterAgain() {
        System.out.print("Do you want to type again?(Y/N): ");
        String string = sc.nextLine().trim();
        return string.equalsIgnoreCase("Y") || string.equalsIgnoreCase("Yes");
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

    private String createSeatAvailable() {
        System.out.print("Seat available: ");
        return sc.nextLine().trim();
    }

    public void addNewFlight() {
        listFlight.add(new Flight(createFlightNumber(), createDepartureCity(), createDestinationCity(), createDepartureTime(), createArrivalTime(), createSeatAvailable()));
    }

    private void showList(List<Flight> list) {
        for (Flight flight : list) {
            System.out.println(flight.toString());
        }
    }

    public List availableFlightBaseOnDepartureAndArrival() {
        List<Flight> listAvailableFlight = new ArrayList<>();
        String destinationCity = createDestinationCity();
        String startTime = createDepartureTime();
        String endTime = createArrivalTime();
        for (Flight flight : listFlight) {
            if (flight.getDestinationCity().equalsIgnoreCase(destinationCity) && flight.getDepartureTime().equalsIgnoreCase(startTime) && flight.getArrivalTime().equalsIgnoreCase(endTime)) {
                listAvailableFlight.add(flight);
            }
        }
        return listAvailableFlight;
    }

    public boolean makeReservation(String passengerName, String contactDetails, String flightNumberToFound) {
//        System.out.print("Enter Your Name: ");
//        String passengerName = sc.nextLine().trim();
//        System.out.print("Enter Contact Details: ");
//        String contactDetails = sc.nextLine().trim();
//        System.out.print("Enter Flight Number to Reserve: ");
//        String flightNumber = sc.nextLine().trim();
        showList(availableFlightBaseOnDepartureAndArrival());
        for (Flight flight : listFlight) {
            if (Reservations.countReservationAndSeat < limitSeat(400)) {
                if (flight.getFlightNumber().equalsIgnoreCase(flightNumberToFound)) {
                    Passenger passenger = new Passenger(passengerName, contactDetails);
                    String reservationID = "VN" + Reservations.incReservationID++;
                    Reservations r = new Reservations(reservationID, passenger, flight);
                    listReservations.add(r);
                    System.out.println("Reservation Successful!");
                    System.out.println("Reservation ID: " + reservationID);
                    return true;
                }
            } else {
                System.out.println("The flight was full of seats.");
            }
        }
        return false;
    }

    private Reservations findReservationByID(String reservationString) {
        for (Reservations reservation : listReservations) {
            if (reservation.getReservationID().equalsIgnoreCase(reservationString)) {
                return reservation;
            }
        }
        return null;
    }

    public boolean checkInAndSeatAllocation(String providingID) {
        Reservations reservationByID = findReservationByID(providingID);
        if (reservationByID != null) {
            Passenger passenger = reservationByID.getPassenger();
            Flight flight = reservationByID.getFlight();
            reservationByID.setIsCheckIn(true);
            System.out.println("Boarding Pass:");
            System.out.println("Passenger Name: " + passenger.getName());
            System.out.println("Contact Details: " + passenger.getContactDetails());
            System.out.print("Flight Details:");
            System.out.println(flight.toString());
            displayAllSeat(flight);

            Map<Integer, Boolean> seatMap = flight.getSeatMap();
            String choose;
            System.out.print("You want to choose seat(Y/N): ");
            choose = sc.nextLine().trim();
            if (choose.equalsIgnoreCase("Y") || choose.equalsIgnoreCase("yes")) {
                while (true) {
                    System.out.print("Enter seat number you want to seat: ");
                    int seatNumber = Integer.parseInt(sc.nextLine());
                    if (selectSeatIfSeatAvailable(flight, seatNumber)) {
                        reservationByID.setSeatNumber(seatNumber);
                        for (Map.Entry<Integer, Boolean> entry : seatMap.entrySet()) {
                            Integer key = entry.getKey();
                            if (key == seatNumber) {
                                entry.setValue(true);
                                break;
                            }
                        }
                        break;
                    } else {
                        System.out.println("Seat is not available. Enter again or not");
                        if (!askToBackToEnterAgain()) {
                            break;
                        }
                    }
                }
            } else {
                for (Map.Entry<Integer, Boolean> entry : seatMap.entrySet()) {
                    Integer key = entry.getKey();
                    boolean isAvai = entry.getValue();
                    if (!isAvai) {
                        entry.setValue(true);
                        reservationByID.setSeatNumber(key);
                    }
                }
            }
            System.out.println("Check-in Successful!");
            return true;
        }
        return false;
    }

//    private void limitSeat(int limit, Flight flight) {
//        Map<Integer, Boolean> seatMap = flight.getSeatMap();
//        int i = 1;
//        for (Map.Entry<Integer, Boolean> entry : seatMap.entrySet()) {
//            if (Flight.countSeatAvailable < limit) {
//                Integer key = entry.getKey();
//                Boolean value = entry.getValue();
//                if (i <= limit) {
//                    key = i;
//                    i++;
//                }
//            } else {
//                System.out.println("Out of limit");
//            }
//        }
//        flight.setSeatMap(seatMap);
//    }

    /*
    public void setAllSeatToAvailable(Flight flight) {
        limitSeat(40, flight);
        Map<Integer, Boolean> seatMap = flight.getSeatMap();
        for (Map.Entry<Integer, Boolean> entry : seatMap.entrySet()) {
            entry.setValue(false);
        }
        flight.setSeatMap(seatMap);
    }
     */
    private int limitSeat(int limit) {
        return limit;
    }

    public void displayAllSeat(Flight flight) {
        System.out.println("All Seat for Flight " + flight.getFlightNumber() + ":");
        Map<Integer, Boolean> seatMap = flight.getSeatMap();
        for (Map.Entry<Integer, Boolean> entry : seatMap.entrySet()) {
            int seatNumber = entry.getKey();
            boolean isAvailable = entry.getValue();
            if (isAvailable) {
                System.out.println("Seat " + seatNumber + " is available.");
            } else {
                System.out.println("Seat " + seatNumber + " is not available.");
            }
        }
    }

    public boolean selectSeatIfSeatAvailable(Flight flight, int seatNumber) {
        Map<Integer, Boolean> seatMap = flight.getSeatMap();
        for (Map.Entry<Integer, Boolean> entry : seatMap.entrySet()) {
            Integer seat = entry.getKey();
            Boolean isAvailable = entry.getValue();
            if (seatNumber == seat && isAvailable) {
                return true;
            }
        }
        return false;
    }

    public boolean isSeatAvailable(Flight flight, int seatNumber) {
        Map<Integer, Boolean> seatMap = flight.getSeatMap();
        return seatMap.getOrDefault(seatNumber, false);
    }

}

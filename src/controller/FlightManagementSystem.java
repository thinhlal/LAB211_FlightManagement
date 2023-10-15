package controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import model.Flight;
import model.Passenger;
import model.Reservations;
import model.Utils;
import model.Crew;

public class FlightManagementSystem<E> {

    private final String FILE_FLIGHTS = "flights.txt";
    private final String FILE_PASSENGERS = "passengers.txt";
    private final String FILE_RESERVATIONS = "reservations.txt";
    private final String FILE_CREWS = "crewass.txt";

    Scanner sc = new Scanner(System.in);
    List<Flight> listFlight = new ArrayList<>();
    List<Reservations> listReservations = new ArrayList<>();
    List<Passenger> listPassenger = new ArrayList<>();
    List<Crew> listCrews = new ArrayList<>();

    private boolean askToBackToEnterAgain() {
        System.out.print("Do you want to enter again?(Y/N): ");
        String string = sc.nextLine().trim();
        return string.equalsIgnoreCase("Y") || string.equalsIgnoreCase("yes") || string.equalsIgnoreCase("ye");
    }

    public boolean askToBackToMenu() {
        System.out.print("Do you want to back to Main Menu(Y/N): ");
        String string = sc.nextLine().trim();
        return string.equalsIgnoreCase("Y") || string.equalsIgnoreCase("yes") || string.equalsIgnoreCase("ye");
    }

    public boolean chooseYesNo(String string) {
        return string.equalsIgnoreCase("Y") || string.equalsIgnoreCase("yes") || string.equalsIgnoreCase("ye");
    }

    private String createFlightNumber() {
        while (true) {
            System.out.print("Flight number: ");
            String flightNumber = sc.nextLine().trim();
            if (flightNumber.matches("^F\\d{4}$")) {
                return flightNumber;
            } else {
                System.out.println("Must be follow as: Fxyzt, with xyzt is a number and no spaces");
            }
        }
    }

    public String createDepartureCity() {
        System.out.print("Departure city: ");
        return sc.nextLine().trim();
    }

    public String createDestinationCity() {
        System.out.print("Destination city: ");
        return sc.nextLine().trim();
    }

    private boolean isValidTime(String time) {
        if (time.matches("^\\d\\d\\d\\d-(0?[1-9]|1[0-2])-(0?[1-9]|[12][0-9]|3[01]) (00|[0-9]|1[0-9]|2[0-3]):([0-9]|[0-5][0-9]):([0-9]|[0-5][0-9])$")) {
            return true;
        }
        return false;
    }

    private boolean isValidDate(String date) {
        if (date.matches("^\\d\\d\\d\\d-(0?[1-9]|1[0-2])-(0?[1-9]|[12][0-9]|3[01])$")) {
            return true;
        }
        return false;
    }

    public String createDepartureTime() {
        String time;
        while (true) {
            System.out.print("Departure time(yyyy-MM-dd HH:mm:ss): ");
            time = sc.nextLine().trim();
            if (isValidTime(time)) {
                break;
            } else {
                System.out.println("Not valid time. Do again");
            }
        }
        return time;
    }

    public boolean isArrivalAfterDeparture(String departureTime, String arrivalTime) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (departureTime == null) {
            return true;
        }
        try {
            Date departureDate = dateFormat.parse(departureTime);
            Date arrivalDate = dateFormat.parse(arrivalTime);
            return departureDate.before(arrivalDate);
        } catch (Exception e) {
            return false;
        }
    }

    public String createArrivalTime(String departureTime) {
        String time;
        while (true) {
            System.out.print("Arrival time(yyyy-MM-dd HH:mm:ss): ");
            time = sc.nextLine().trim();
            if (isValidTime(time) && isArrivalAfterDeparture(departureTime, time)) {
                break;
            } else {
                System.out.println("Not valid time. Do again");
            }
        }
        return time;
    }

    private int createMaxSeat() {
        int maxSeat = Utils.getInt("Max seat of flight: ");
        return maxSeat;
    }

    public void addNewFlight() {
        String createFlightNumber = createFlightNumber();
        String createDepatureCity = createDepartureCity();
        String createDestinationCity = createDestinationCity();
        String departureTime = createDepartureTime();
        String arrivalTime = createArrivalTime(departureTime);
        listFlight.add(new Flight(createFlightNumber, createDepatureCity, createDestinationCity, departureTime, arrivalTime, createMaxSeat()));
    }

    public String createValidDate() {
        while (true) {
            System.out.print("Start day(yyyy-MM-dd): ");
            String startDay = sc.nextLine().trim();
            if (!isValidDate(startDay)) {
                System.out.println("You input wrong type date. Input again");
            } else {
                return startDay;
            }
        }
    }

    private String getStartDayOfFlight(Flight flight) {
        String day[] = flight.getDepartureTime().split("\\s+");
        return day[0];
    }

    public List<Flight> availableFlightBaseOnDepartureAndArrival(String departureCity, String destinationCity, String startDay) {
        List<Flight> listAvailableFlight = new ArrayList<>();
        for (Flight flight : listFlight) {
            if (flight.getDestinationCity().equalsIgnoreCase(destinationCity)
                    && flight.getDepartureCity().equalsIgnoreCase(departureCity)
                    && startDay.equalsIgnoreCase(getStartDayOfFlight(flight))) {
                listAvailableFlight.add(flight);
            }
        }
        return listAvailableFlight;
    }

    public void showList(List<Flight> list) {
        for (Flight flight : list) {
            flight.getInforOfFlightForPas();
        }
    }

    public boolean makeReservation(String passengerName, String contactDetails, String flightNumberToFound) {
        for (Flight flight : listFlight) {
            if (flight.getAvailableSeats() > 0) {
                if (flight.getFlightNumber().equalsIgnoreCase(flightNumberToFound)) {
                    int incCntSeat = flight.getCountSeat();
                    flight.setCountSeat(++incCntSeat);
                    int fightAvailableDecs = flight.getAvailableSeats();
                    flight.setAvailableSeats(--fightAvailableDecs);
                    Passenger passenger = new Passenger(passengerName, contactDetails);
                    listPassenger.add(passenger);
                    String reservationID = "VN" + Reservations.incReservationID++;
                    Reservations r = new Reservations(reservationID, passenger, flight);
                    listReservations.add(r);
                    System.out.println("Reservation ID: " + reservationID);
                    return true;
                }
            } else {
                return false;
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

    public void setAllSeatNumberToDefault(Flight flight) {
        Map<Integer, Boolean> seatMap = flight.getSeatMap();
        for (Integer i = 0; i <= flight.getMaxSeat(); i++) {
            seatMap.put(i, Boolean.TRUE);
        }
        flight.setSeatMap(seatMap);
    }

    public boolean checkInAndSeatAllocation(String providingID) {
        if (findReservationByID(providingID) == null) {
            return false;
        }
        Reservations reservationByID = findReservationByID(providingID);
        if (reservationByID.isIsCheckIn()) {
            return false;
        }
        Passenger passenger = reservationByID.getPassenger();
        Flight flight = reservationByID.getFlight();
        if (!flight.isIsAllocateAllSeat()) {
            setAllSeatNumberToDefault(flight);
            flight.setIsAllocateAllSeat(true);
        }
        reservationByID.setIsCheckIn(true);
        System.out.println("Boarding Pass:");
        System.out.println("Passenger Name: " + passenger.getName());
        System.out.println("Contact Details: " + passenger.getContactDetails());
        System.out.println("Flight Details:");
        flight.getInforOfFlightForPas();
        displayAllSeat(flight);
        Map<Integer, Boolean> seatMap = flight.getSeatMap();
        String choose;
        System.out.print("You want to choose seat(Y/N): ");
        choose = sc.nextLine().trim();
        if (chooseYesNo(choose)) {
            while (true) {
                System.out.print("Enter seat number you want to seat: ");
                int selectSeatNumber = Integer.parseInt(sc.nextLine());
                if (selectSeatIfSeatAvailable(flight, selectSeatNumber)) {
                    reservationByID.setSeatNumber(selectSeatNumber);
                    passenger.setSeatNumber(selectSeatNumber);
                    for (Map.Entry<Integer, Boolean> entry : seatMap.entrySet()) {
                        Integer key = entry.getKey();
                        if (key == selectSeatNumber) {
                            entry.setValue(false);
                            return true;
                        }
                    }
                } else {
                    System.out.println("Seat is not available. Choose another seat or random");
                    if (!askToBackToEnterAgain()) {
                        break;
                    }
                }
            }
        } else {
            for (Map.Entry<Integer, Boolean> entry : seatMap.entrySet()) {
                Integer key = entry.getKey();
                boolean isAvai = entry.getValue();
                if (isAvai) {
                    entry.setValue(false);
                    reservationByID.setSeatNumber(key);
                    passenger.setSeatNumber(key);
                    reservationByID.setIsCheckIn(true);
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    public int saveToFileAfterIndex(String fileName, List list) {
        int index = -1;
        try {
            FileReader file = new FileReader(fileName);
            BufferedReader br = new BufferedReader(file);
            String line;
            while (true) {
                line = br.readLine();
                if (line == null) {
                    break;
                }
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).toString().equals(line)) {
                        index = i + 1;
                    }
                }
            }
            br.close();
            file.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return index;
    }

    public void saveFlightsToFile() {
        try {
            FileWriter fw = new FileWriter(FILE_FLIGHTS, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);
            for (int i = 0; i < listFlight.size(); i++) {
                if (i >= saveToFileAfterIndex(FILE_FLIGHTS, listFlight)) {
                    pw.println(listFlight.get(i).writeToFile());
                }
            }
            pw.flush();
            pw.close();
            bw.close();
            fw.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void savePassengersToFile() {
        try {
            FileWriter fw = new FileWriter(FILE_PASSENGERS, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);
            for (int i = 0; i < listPassenger.size(); i++) {
                if (i >= saveToFileAfterIndex(FILE_PASSENGERS, listPassenger)) {
                    pw.println(listPassenger.get(i).writeToFile());
                }
            }
            pw.flush();
            pw.close();
            bw.close();
            fw.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void saveRerservationsToFile() {
        try {
            FileWriter fw = new FileWriter(FILE_RESERVATIONS, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);
            for (int i = 0; i < listReservations.size(); i++) {
                if (i >= saveToFileAfterIndex(FILE_RESERVATIONS, listReservations)) {
                    pw.println(listReservations.get(i).writeToFile());
                }
            }
            pw.flush();
            pw.close();
            bw.close();
            fw.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void saveCrewsToFile() {
        try {
            FileWriter fw = new FileWriter(FILE_CREWS, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);
            for (int i = 0; i < listCrews.size(); i++) {
                if (i >= saveToFileAfterIndex(FILE_CREWS, listCrews)) {
                    pw.println(listCrews.get(i).writeToFile());
                }
            }
            pw.flush();
            pw.close();
            bw.close();
            fw.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private List addElementToListFromFile(String fileName, List<E> list, E e) {
        try {
            FileReader fr = new FileReader(fileName);
            BufferedReader br = new BufferedReader(fr);
            String line;
            while (true) {
                line = br.readLine().trim();
                if (line == null) {
                    break;
                }
                String words[] = line.split("_");

                if (e instanceof Flight && fileName == FILE_FLIGHTS) {
                    Flight flight = new Flight(words[0], words[1], words[2], words[3], words[4], Integer.parseInt(words[5]));
                    list.add((E)flight);
                } else if (e instanceof Passenger && fileName == FILE_PASSENGERS) {
                    Passenger passenger = new Passenger(words[0], words[1], Integer.parseInt(words[3]));
                    list.add((E)passenger);
                } else if (e instanceof Reservations && fileName == FILE_RESERVATIONS) {
                    Passenger p = new Passenger(line, FILE_CREWS)
                    Reservations flight = new Reservations(words[0], words[1], words[2]);
                    list.add((E)(Flight) flight);
                } else if (e instanceof Crew && fileName == FILE_CREWS) {
                }

            }
            br.close();
            fr.close();
        } catch (Exception a) {
        }
        return list;
    }

    public void printAllListFlightsFromFile() {
        List<Flight> list = new ArrayList<>();

    }
}

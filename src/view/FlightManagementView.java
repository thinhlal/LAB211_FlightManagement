package view;

import controller.FlightManagementSystem;
import java.util.List;
import java.util.Scanner;
import model.Flight;

public class FlightManagementView {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        FlightManagementSystem f = new FlightManagementSystem();
        int check = 0;
        while (true) {
            try {
                System.out.println("\n1. Add New Flight\n"
                        + "2. Search available flight on departure and arrival city and time.\n"
                        + "3. Make reservation\n"
                        + "4. Check-in and allocate seat for passenger:\n"
                        + "5. Delete Product.\n"
                        + "6. Save Products to file.\n"
                        + "7. Print list Products from the file.\n"
                        + "Others- Quit");
                System.out.print("Your choice: ");
                check = Integer.parseInt(sc.nextLine());
                if (check == 1) {
                    while (true) {
                        f.addNewFlight();
                        if (f.askToBackToMenu()) {
                            break;
                        }
                    }
                } else if (check == 2) {
                    while (true) {
                        String departureCity = f.createDepartureCity();
                        String destinationCity = f.createDestinationCity();
                        String startFlying = f.createValidDate();
                        f.showList(f.availableFlightBaseOnDepartureAndArrival(departureCity, destinationCity, startFlying));
                        if (f.askToBackToMenu()) {
                            break;
                        }
                    }
                } else if (check == 3) {
                    System.out.print("Enter your name: ");
                    String passengerName = sc.nextLine().trim();
                    System.out.print("Enter contact details: ");
                    String contactDetails = sc.nextLine().trim();
                    System.out.print("Enter flight number to reserve: ");
                    String flightNumberToFound = sc.nextLine().trim();
                    f.makeReservation(passengerName, contactDetails, flightNumberToFound);
                    while (true) {
                        if (f.askToBackToMenu()) {
                            break;
                        }
                    }
                } else if (check == 4) {
                    while (true) {
                        System.out.print("Reservation ID to allocate seat: ");
                        String reservationIDToAllocateSeat = sc.nextLine().trim();
                        f.checkInAndSeatAllocation(reservationIDToAllocateSeat);
                        if (f.askToBackToMenu()) {
                            break;
                        }
                    }
                } else if (check == 5) {
                    while (true) {

                        if (f.askToBackToMenu()) {
                            break;
                        }
                    }
                } else if (check == 6) {
                    while (true) {

                        if (f.askToBackToMenu()) {
                            break;
                        }
                    }
                } else if (check == 7) {
                    while (true) {

                        if (f.askToBackToMenu()) {
                            break;
                        }
                    }
                } else {

                    break;
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}

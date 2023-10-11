
package view;

import controller.FlightManagementSystem;
import java.util.List;
import java.util.Scanner;
import model.Flight;

public class FlightManagementView {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        FlightManagementSystem f = new FlightManagementSystem();
        f.addNewFlight();
        f.showList(f.availableFlightBaseOnDepartureAndArrival());
        System.out.println("ProvideIDTocheckIn: ");
        String provideID = sc.nextLine();
        f.checkInAndSeatAllocation(provideID);
    }
}

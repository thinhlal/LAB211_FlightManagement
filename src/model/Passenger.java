package model;

public class Passenger {
    private int seatNumber;
    private String name;
    private String contactDetails;

    public Passenger(String name, String contactDetails) {
        this.name = name;
        this.contactDetails = contactDetails;
    }

    public Passenger(String name, String contactDetails, int seatNumber) {
        this.seatNumber = seatNumber;
        this.name = name;
        this.contactDetails = contactDetails;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactDetails() {
        return contactDetails;
    }

    public void setContactDetails(String contactDetails) {
        this.contactDetails = contactDetails;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }
    public String writeToFile(){
        return "-Passenger: " + name + "_" + contactDetails + seatNumber;
    }
    
    @Override
    public String toString() {
        return name + "_" + contactDetails;
    }

}

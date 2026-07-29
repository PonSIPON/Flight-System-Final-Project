/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MyLib;
/**
 *
 * @author GROUP6
 */
import java.util.ArrayList;

public class FlightSystem {
    private static FlightSystem instance;
    private ArrayList<Flight> flights;

    private FlightSystem() {
        flights = new ArrayList<>();
        flights.add(new Flight("FL001", "Tokyo",     "12:00 AM"));
        flights.add(new Flight("FL002", "London",    "4:20 PM"));
        flights.add(new Flight("FL003", "Singapore", "10:35 PM"));
        flights.add(new Flight("FL004", "Paris",     "7:10 AM"));
        flights.add(new Flight("FL005", "Monaco",    "5:30 PM"));
    }

    public static FlightSystem getInstance() {
        if (instance == null) {
            instance = new FlightSystem();
        }
        return instance;
    }

    public ArrayList<Flight> getFlights() {
        return flights;
    }

    public Flight getFlight(String flightNumber) {
        for (Flight f : flights) {
            if (f.getFlightNumber().equals(flightNumber)) {
                return f;
            }
        }
        return null;
    }

    public void addFlight(String number, String dest, String time) {  
        flights.add(new Flight(number, dest, time));
    }

    public String generateReport() {
        StringBuilder report = new StringBuilder();
        report.append("=".repeat(30)).append("\n");
        report.append("FLIGHT BOOKING SYSTEM REPORT\n");
        report.append("=".repeat(30)).append("\n\n");

        for (Flight f : flights) {  
            report.append(String.format("Flight %s to %s at %s\n",
                f.getFlightNumber(), f.getDestination(), f.getDepartureTime()));
            report.append(String.format("%-10s %-12s %-10s %-15s %-20s\n",
                "Seat No", "Class", "Price", "Status", "Passenger"));

            for (Seat s : f.getAllSeats()) {  
                report.append(String.format("%-10s %-12s $%-9.2f %-15s %-20s\n",
                    s.getSeatNumber(),
                    s.getSeatClass(),
                    s.getPrice(),
                    s.getStatus(),
                    s.getPassengerName().isEmpty() ? "—" : s.getPassengerName()));
            }
            report.append("\n");
        }

        return report.toString();  
    }
}

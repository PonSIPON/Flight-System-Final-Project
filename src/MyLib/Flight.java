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
public class Flight {
    private String flightNumber;
    private String destination;
    private String departureTime;
    private ArrayList<Seat> seats;
    public Flight(String flightNumber, String destination, String departureTime) {
        this.flightNumber = flightNumber;
        this.destination = destination;
        this.departureTime = departureTime;
        seats = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            seats.add(Seat.createSeat("S" + i, "Economy", 150));
        }
    }
    public static Flight createFlight(String number, String dest, String time) {
        return new Flight(number, dest, time);
    }
    public String getFlightNumber() {
        return flightNumber;
    }
    public String getDestination() {
        return destination;
    }
    public String getDepartureTime() {
        return departureTime;
    }
    public ArrayList<Seat> getAvailableSeats(String seatClass) {
        ArrayList<Seat> available = new ArrayList<>();
        for (Seat s : seats) {
            if (s.getSeatClass().equalsIgnoreCase(seatClass)
                    && s.getStatus() == Seat.Status.AVAILABLE) {
                available.add(s);
            }
        }
        return available;
    }
    public Seat getSeat(String seatNumber) {
        for (Seat s : seats) {
            if (s.getSeatNumber().equalsIgnoreCase(seatNumber)) {
                return s;
            }
        }
        return null;
    }
    public ArrayList<Seat> getAllSeats() {
        return seats;
    }
    public boolean isFull() {
        for (Seat s : seats) {
            if (s.getStatus() == Seat.Status.AVAILABLE) {
                return false;
            }
        }
        return true;
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MyLib;

/**
 *
 * @author GROUP6
 */
public class Seat {
    private String seatNumber;
    private String seatClass; 
    private double price;
    private Status status;
    private String passengerName;
    
    public enum Status {
        AVAILABLE, RESERVED, BOOKED
    }
    
    public Seat(String seatNumber, String seatClass, double price) {
        this.seatNumber = seatNumber;
        this.seatClass = seatClass;
        this.price = price;
        this.status = Status.AVAILABLE;
        this.passengerName = null;
    }
    
    public static Seat createSeat(String seatNumber, String seatClass, double price) {
        return new Seat(seatNumber, seatClass, price);
    }
    
    public boolean book(String passengerName) {
        if (status == Status.AVAILABLE || status == Status.RESERVED) {
            this.status = Status.BOOKED;
            this.passengerName = passengerName;
            return true;
        }
            return false;
        }
    
    public boolean reserve() {
        if (status == Status.AVAILABLE) {
            this.status = Status.RESERVED;
            return true;
        }
        return false;
        }
    
    public void release() {
        this.status = Status.AVAILABLE;
        this.passengerName = null;
        }
    
    public String getSeatNumber() { return seatNumber; }
    public String getSeatClass() { return seatClass; }
    public double getPrice() { return price; }
    public Status getStatus() { return status; }
    public String getPassengerName() { return passengerName; }

    @Override
    public String toString() {
        return "Seat " + seatNumber + " [" + seatClass + "] - " + status +
        (passengerName != null ? " (Passenger: " + passengerName + ")" : "");
        }
}
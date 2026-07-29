/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MyApp;

/**
 *
 * @author GROUP6
 */

import MyLib.FlightSystem;
import MyLib.Seat;
import MyLib.Flight;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Frontend extends JFrame {
    private FlightSystem flightSystem;

    public Frontend() {
        setTitle("Flight Booking System");
        setSize(500, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        flightSystem = FlightSystem.getInstance();
        showLoginPanel();
    }
    
    //LOGIN PANEL
    private void showLoginPanel() {
        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));

        JTextField emailField = new JTextField();
        JPasswordField passField = new JPasswordField();
        JButton loginBtn = new JButton("Login");

        panel.add(new JLabel("Email:"));
        panel.add(emailField);
        panel.add(new JLabel("Password:"));
        panel.add(passField);
        panel.add(new JLabel());
        panel.add(loginBtn);

        setContentPane(panel);
        revalidate();

        loginBtn.addActionListener(e -> {
            String email = emailField.getText();
            String pass = new String(passField.getPassword());

            if (email.equals("passenger@flight.com") && pass.equals("1234")) {
                showMenuPanel();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid login.");
            }
        });
    }
    
    //MENU PANEL
    private void showMenuPanel() {
        JPanel panel = new JPanel(new GridLayout(6, 1, 10, 10));

        JButton bookBtn = new JButton("Book a Seat");
        JButton reserveBtn = new JButton("Reserve a Seat");
        JButton releaseBtn = new JButton("Release a Seat");
        JButton viewFlightsBtn = new JButton("View Available Flights");
        JButton reportBtn = new JButton("Generate Report");
        JButton exitBtn = new JButton("Exit");

        panel.add(bookBtn);
        panel.add(reserveBtn);
        panel.add(releaseBtn);
        panel.add(viewFlightsBtn);
        panel.add(reportBtn);
        panel.add(exitBtn);

        setContentPane(panel);
        revalidate();

        bookBtn.addActionListener(e -> showBookDialog());
        reserveBtn.addActionListener(e -> showReserveDialog());
        releaseBtn.addActionListener(e -> showReleaseDialog());
        viewFlightsBtn.addActionListener(e -> showAvailableFlightsDialog());
        reportBtn.addActionListener(e -> showReportDialog());
        exitBtn.addActionListener(e -> System.exit(0));
    }
    
    //BOOKING A SEAT
     private void showBookDialog() {
        // Flight selection
        ArrayList<Flight> flights = flightSystem.getFlights();
        JComboBox<String> flightBox = new JComboBox<>();
        for (Flight f : flights) {
            flightBox.addItem(f.getFlightNumber() + " - " + f.getDestination() + " @ " + f.getDepartureTime());
        }

        JTextField passengerField = new JTextField();
        JTextField seatField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(3, 2, 5, 5));
        panel.add(new JLabel("Select Flight:"));
        panel.add(flightBox);
        panel.add(new JLabel("Passenger Name:"));
        panel.add(passengerField);
        panel.add(new JLabel("Seat No (Ex: S1):"));
        panel.add(seatField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Book a Seat",
                JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            String passengerName = passengerField.getText().trim();
            String seatNumber = seatField.getText().trim();
            
            //VALIDATION
            if (passengerName.isEmpty() || seatNumber.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields are required.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int flightIndex = flightBox.getSelectedIndex();
            Flight selectedFlight = flights.get(flightIndex);

            Seat seat = selectedFlight.getSeat(seatNumber);
            if (seat == null) {
                JOptionPane.showMessageDialog(this, "Seat " + seatNumber + " does not exist on this flight.",
                        "Booking Failed", JOptionPane.WARNING_MESSAGE);
                return;
            }

            boolean booked = seat.book(passengerName);
            if (booked) {
                JOptionPane.showMessageDialog(this,
                        "Seat " + seatNumber + " booked successfully for " + passengerName + "!\n"
                        + "Flight: " + selectedFlight.getFlightNumber() + " to " + selectedFlight.getDestination()
                        + "\nPrice: $" + String.format("%.2f", seat.getPrice()));
            } else {
                JOptionPane.showMessageDialog(this, "Seat " + seatNumber + " is not available for booking.",
                        "Booking Failed", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    //RESERVE SEAT
    private void showReserveDialog() {
        ArrayList<Flight> flights = flightSystem.getFlights();
        JComboBox<String> flightBox = new JComboBox<>();
        for (Flight f : flights) {
            flightBox.addItem(f.getFlightNumber() + " - " + f.getDestination() + " @ " + f.getDepartureTime());
        }

        JTextField seatField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(2, 2, 5, 5));
        panel.add(new JLabel("Select Flight:"));
        panel.add(flightBox);
        panel.add(new JLabel("Seat No (Ex: S1):"));
        panel.add(seatField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Reserve a Seat",
                JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            String seatNumber = seatField.getText().trim();

            if (seatNumber.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Seat number is required.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int flightIndex = flightBox.getSelectedIndex();
            Flight selectedFlight = flights.get(flightIndex);

            Seat seat = selectedFlight.getSeat(seatNumber);
            if (seat == null) {
                JOptionPane.showMessageDialog(this, "Seat " + seatNumber + " does not exist on this flight.",
                        "Reservation Failed", JOptionPane.WARNING_MESSAGE);
                return;
            }

            boolean reserved = seat.reserve();
            if (reserved) {
                JOptionPane.showMessageDialog(this,
                        "Seat " + seatNumber + " reserved successfully!\n"
                        + "Flight: " + selectedFlight.getFlightNumber() + " to " + selectedFlight.getDestination());
            } else {
                JOptionPane.showMessageDialog(this, "Seat " + seatNumber + " is not available for reservation.",
                        "Reservation Failed", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    //RELEASE A SEAT
    private void showReleaseDialog() {
        ArrayList<Flight> flights = flightSystem.getFlights();
        JComboBox<String> flightBox = new JComboBox<>();
        for (Flight f : flights) {
            flightBox.addItem(f.getFlightNumber() + " - " + f.getDestination() + " @ " + f.getDepartureTime());
        }

        JTextField seatField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(2, 2, 5, 5));
        panel.add(new JLabel("Select Flight:"));
        panel.add(flightBox);
        panel.add(new JLabel("Seat No (Ex: S1):"));
        panel.add(seatField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Release a Seat",
                JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            String seatNumber = seatField.getText().trim();

            if (seatNumber.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Seat number is required.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int flightIndex = flightBox.getSelectedIndex();
            Flight selectedFlight = flights.get(flightIndex);

            Seat seat = selectedFlight.getSeat(seatNumber);
            if (seat == null) {
                JOptionPane.showMessageDialog(this, "Seat " + seatNumber + " does not exist on this flight.",
                        "Release Failed", JOptionPane.WARNING_MESSAGE);
                return;
            }

            seat.release();
            JOptionPane.showMessageDialog(this,
                    "Seat " + seatNumber + " has been released and is now available.\n"
                    + "Flight: " + selectedFlight.getFlightNumber() + " to " + selectedFlight.getDestination());
        }
    }

    //VIEW AVAILABLE FLIGHTS
    private void showAvailableFlightsDialog() {
        StringBuilder sb = new StringBuilder();
        for (Flight f : flightSystem.getFlights()) {
            long availableCount = f.getAllSeats().stream()
                    .filter(s -> s.getStatus() == Seat.Status.AVAILABLE)
                    .count();
            sb.append(f.getFlightNumber())
              .append(" | Destination: ").append(f.getDestination())
              .append(" | Departure: ").append(f.getDepartureTime())
              .append(" | Available Seats: ").append(availableCount)
              .append(f.isFull() ? " [FULL]" : "")
              .append("\n");
        }
        JOptionPane.showMessageDialog(this, sb.toString(), "Available Flights", JOptionPane.INFORMATION_MESSAGE);
    }

    // =============== GENERATE REPORT ===============
    private void showReportDialog() {
        String report = flightSystem.generateReport();
        JTextArea textArea = new JTextArea(report);
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(600, 400));
        JOptionPane.showMessageDialog(this, scrollPane, "Flight Booking Report", JOptionPane.INFORMATION_MESSAGE);
    }
    
    //MAIN
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Frontend frame = new Frontend();
            frame.setVisible(true);
        });
    }
    
}

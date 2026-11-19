import java.util.ArrayList;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Booking {

    String bookingId;
    String customerName;
    String movieName;
    ArrayList<String> seatNumbers;

    double baseTicketAmount;
    double seatAmount;
    double convenienceFee;
    double taxAmount;
    double amount;

    LocalDateTime showDateTime;
    LocalDateTime bookingCreatedAt;

    String bookingStatus;
    String paymentStatus;
    String refundStatus;
    String refundMethod;

    double refundAmount;
    Show selectedShow;
    String userId;
    String paymentMethod;
    String transactionId;

    // Existing constructor — keeps compatibility with Main.java
    Booking(String bookingId, String customerName,
            String movieName, ArrayList<String> seatNumbers,
            double amount) {

        this.bookingId = bookingId;
        this.bookingCreatedAt = LocalDateTime.now();
        this.customerName = customerName;
        this.movieName = movieName;
        this.seatNumbers = new ArrayList<>(seatNumbers);
        this.amount = amount;

        this.baseTicketAmount = 0;
        this.seatAmount = 0;
        this.convenienceFee = 0;
        this.taxAmount = 0;

        this.bookingStatus = "CONFIRMED";
        this.paymentStatus = "PAID";
        this.refundStatus = "NOT INITIATED";
        this.refundMethod = "NOT SELECTED";

        this.refundAmount = 0;
        this.paymentMethod = "NOT SELECTED";
        this.transactionId = "N/A";
    }

    // New constructor for detailed booking
    Booking(String bookingId, String customerName, String movieName,
            ArrayList<String> seatNumbers, double baseTicketAmount,
            double seatAmount, double convenienceFee, double taxAmount,
            double amount, LocalDateTime showDateTime,Show selectedShow,String userId) {

        this.bookingId = bookingId;
        this.bookingCreatedAt = LocalDateTime.now();
        this.customerName = customerName;
        this.movieName = movieName;
        this.seatNumbers = seatNumbers;

        this.baseTicketAmount = baseTicketAmount;
        this.seatAmount = seatAmount;
        this.convenienceFee = convenienceFee;
        this.taxAmount = taxAmount;
        this.amount = amount;

        this.showDateTime = showDateTime;
        this.selectedShow=selectedShow;
        this.userId=userId;

        this.bookingStatus = "CONFIRMED";
        this.paymentStatus = "PAID";
        this.refundStatus = "NOT INITIATED";
        this.refundMethod = "NOT SELECTED";
        this.refundAmount = 0;
        this.paymentMethod = "NOT SELECTED";
        this.transactionId = "N/A";
    }

    // Display E-Ticket
    void displayBooking() {

        System.out.println("\n==============================================");
        System.out.println("                 SMART CINEMA");
        System.out.println("                    E-TICKET");
        System.out.println("==============================================");

        System.out.println("Booking ID    : " + bookingId);
        if (bookingCreatedAt != null) {
            DateTimeFormatter bookingFormatter =
                    DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm a");

            System.out.println("Booking Date  : " +
                    bookingCreatedAt.format(bookingFormatter));
        }
        System.out.println("Customer Name : " + customerName);
        System.out.println("Movie Name    : " + movieName);
        System.out.println("Booking Status: " + bookingStatus);
        System.out.println("Payment Status: " + paymentStatus);
        System.out.println("Payment Method: " + paymentMethod);
        System.out.println("Transaction ID: " + transactionId);

        if (showDateTime != null) {
            DateTimeFormatter formatter =
                    DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm a");

            System.out.println("Show Date     : " + showDateTime.format(formatter));
        }

        System.out.println("----------------------------------------------");

        System.out.println("Total Seats   : " + seatNumbers.size());
        System.out.println("Seat Numbers  : " + seatNumbers);

        System.out.println("----------------------------------------------");

        System.out.printf("Ticket Amount : Rs. %.2f%n", baseTicketAmount);
        System.out.printf("Seat Charges  : Rs. %.2f%n", seatAmount);
        System.out.printf("Convenience   : Rs. %.2f%n", convenienceFee);
        System.out.printf("Tax           : Rs. %.2f%n", taxAmount);

        System.out.println("----------------------------------------------");

        System.out.printf("Total Amount  : Rs. %.2f%n", amount);

        System.out.println("Booking Status: " + bookingStatus);
        System.out.println("Payment Status: " + paymentStatus);

        System.out.println("----------------------------------------------");
        System.out.println("             BOOKING CONFIRMED");
        System.out.println("       THANK YOU FOR BOOKING!");
        System.out.println("==============================================");
    }
}
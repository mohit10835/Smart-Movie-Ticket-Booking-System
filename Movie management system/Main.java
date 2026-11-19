import java.util.ArrayList;
import java.util.Scanner;
import java.util.Comparator;
import java.util.HashMap;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.YearMonth;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        ArrayList<Movie> movies = new ArrayList<>();
        ArrayList<Show> shows = new ArrayList<>();

        Theatre theatre = new Theatre();

        HashMap<String, Booking> bookings = new HashMap<>();
        HashMap<String, Integer> cancellationCount = new HashMap<>();
        HashMap<String, User> users = new HashMap<>();
        User loggedInUser = null;
        int nextUserId = 1001;

        // Default Movies
        movies.add(new Movie(101, "Avengers", "Action", 9.0, 250));
        movies.add(new Movie(102, "Inception", "Sci-Fi", 8.8, 200));
        movies.add(new Movie(103, "Dangal", "Drama", 8.5, 180));
        movies.add(new Movie(104, "Jawan", "Action", 7.8, 220));

        // Available Show Timings
        LocalTime[] timings = {
                LocalTime.of(9, 0),
                LocalTime.of(12, 30),
                LocalTime.of(16, 0),
                LocalTime.of(19, 30)
        };

        // Generate Shows for Next 3 Days
        int showId = 1001;

        for (Movie movie : movies) {

            for (int day = 1; day <= 3; day++) {

                LocalDate date = LocalDate.now().plusDays(day);

                for (LocalTime time : timings) {

                    shows.add(new Show(
                            showId++,
                            movie.movieId,
                            movie.movieName,
                            date,
                            time,
                            "SMART CINEMA"
                    ));
                }
            }
        }

        int choice=0;

        do {
            System.out.println("\n================================");
            System.out.println("   SMART MOVIE TICKET BOOKING");
            System.out.println("================================");

            System.out.println("1. View Movies");
            System.out.println("2. Search Movie");
            System.out.println("3. Sort Movies by Rating");
            System.out.println("4. View Available Seats");
            System.out.println("5. Manage Seats");
            System.out.println("6. Cancel Seat");
            System.out.println("7. Exit");
            System.out.println("8. Book Movie Ticket");
            System.out.println("9. Search Booking");
            System.out.println("10. Register User");
            System.out.println("11. View User Details");
            System.out.println("12. View Wallet Balance");
            System.out.println("13. Add Money to Wallet");
            System.out.println("14. View Transaction History");
            System.out.println("15. My Bookings / Booking History");
            System.out.println("16. Admin Login");
            System.out.println("17. User Login");
            System.out.println("18. User Logout");

            System.out.print("Enter Your Choice: ");

            if (sc.hasNextInt()) {

                choice = sc.nextInt();
                sc.nextLine();

            } else {

                System.out.println("Invalid Input! Please Enter a Number.");

                sc.nextLine();

                continue;
            }
            switch (choice) {

                // View Movies
                case 1:

                    System.out.println("\nAVAILABLE MOVIES");

                    for (Movie m : movies) {
                        m.displayMovie();
                        System.out.println("-------------------");
                    }

                    break;

                // Search Movie
                case 2:

                    System.out.print("Enter Movie Name: ");

                    String searchName = sc.nextLine();

                    boolean found = false;

                    for (Movie m : movies) {

                        if (m.movieName.equalsIgnoreCase(searchName)) {

                            m.displayMovie();
                            found = true;
                            break;
                        }
                    }

                    if (!found) {
                        System.out.println("Movie Not Found!");
                    }

                    break;

                // Sort Movies
                case 3:

                    movies.sort(
                            Comparator.comparingDouble(
                                    (Movie m) -> m.rating
                            ).reversed()
                    );

                    System.out.println("\nTOP RATED MOVIES");

                    for (Movie m : movies) {

                        System.out.println(
                                m.movieName + " - " + m.rating
                        );
                    }

                    break;

                // View All Seats
                case 4:

                    theatre.displaySeats();

                    break;

                // Manage Seats
                case 5:

                    int seatChoice = 0;

                    do {

                        System.out.println("\n========== MANAGE SEATS ==========");
                        System.out.println("1. View Regular Seats");
                        System.out.println("2. View Premium Seats");
                        System.out.println("3. View VIP Seats");
                        System.out.println("4. View All Seats");
                        System.out.println("5. View Seat Pricing");
                        System.out.println("6. Back to Main Menu");

                        System.out.print("Enter Your Choice: ");

                        try {
                            seatChoice = Integer.parseInt(sc.nextLine().trim());

                        } catch (NumberFormatException e) {
                            System.out.println("Invalid Input! Please Enter a Number.");
                            continue;
                        }

                        switch (seatChoice) {

                            case 1:
                                theatre.displayCategorySeats("REGULAR");
                                break;

                            case 2:
                                theatre.displayCategorySeats("PREMIUM");
                                break;

                            case 3:
                                theatre.displayCategorySeats("VIP");
                                break;

                            case 4:
                                theatre.displaySeats();
                                break;

                            case 5:

                                System.out.println("\n========== SEAT PRICING ==========");

                                System.out.println("Regular A : Rs. 100");
                                System.out.println("Regular B : Rs. 120");
                                System.out.println("Regular C : Rs. 150");

                                System.out.println("Premium D : Rs. 180 + Rs. 100");
                                System.out.println("VIP E     : Rs. 220 + Rs. 200");

                                System.out.println("----------------------------------");
                                System.out.println("Premium Add-on : Rs. 100");
                                System.out.println("VIP Add-on     : Rs. 200");

                                break;

                            case 6:
                                System.out.println("Returning to Main Menu...");
                                break;

                            default:
                                System.out.println("Invalid Choice! Please select 1-6.");
                        }

                    } while (seatChoice != 6);

                    break;
                // Cancel Individual Seat
                case 6:

                    // Login Authentication
                    if (loggedInUser == null) {
                        System.out.println("Please Login First!");
                        break;
                    }

                    // Step 1: Enter Booking ID
                    System.out.print("Enter Booking ID: ");

                    String cancelId = sc.nextLine().trim().toUpperCase();

                    // Step 2: Check Booking
                    if (!bookings.containsKey(cancelId)) {

                        System.out.println("Booking Not Found!");
                        break;
                    }

                    Booking cancelBooking = bookings.get(cancelId);
                    // Check Booking Ownership
                    if (!loggedInUser.userId.equals(cancelBooking.userId)) {
                        System.out.println("Access Denied!");
                        System.out.println("You Cannot Cancel Another User's Booking.");
                        break;
                    }

                    // Step 3: Check Booking Status
                    if (cancelBooking.bookingStatus.equals("CANCELLED")) {

                        System.out.println("Booking Already Cancelled!");
                        break;
                    }

                    // Step 4: Display Booking
                    cancelBooking.displayBooking();

                    // Step 5: Calculate Time Difference
                    LocalDateTime now = LocalDateTime.now();

                    LocalDateTime showTime = cancelBooking.showDateTime;

                    long minutesLeft = java.time.Duration.between(
                            now, showTime
                    ).toMinutes();

                    // Step 6: Check Cancellation Eligibility
                    if (minutesLeft < 20) {

                        System.out.println("Cancellation Not Allowed!");
                        System.out.println("Less than 20 minutes remaining.");
                        break;
                    }

                    // Step 7: Calculate Refund
                    double refundableAmount =
                            cancelBooking.baseTicketAmount + cancelBooking.seatAmount;

                    double refundPercentage;

                    if (minutesLeft >= 120) {

                        refundPercentage = 0.75;

                    } else {

                        refundPercentage = 0.50;
                    }

                    double refundAmount = refundableAmount * refundPercentage;

                    // Step 8: Display Refund Details
                    System.out.println("\n========== REFUND SUMMARY ==========");

                    System.out.println("Refundable Amount : Rs. " + refundableAmount);
                    System.out.println("Refund Percentage : " + (refundPercentage * 100) + "%");
                    System.out.println("Refund Amount     : Rs. " + refundAmount);

                    System.out.println("Convenience Fee   : Non-refundable");
                    System.out.println("Tax               : Non-refundable");

                    // Step 9: Confirm Cancellation
                    System.out.print("\nConfirm Cancellation? (yes/no): ");

                    String cancelConfirmation = sc.nextLine().trim();

                    if (!cancelConfirmation.equalsIgnoreCase("yes")) {

                        System.out.println("Cancellation Aborted!");
                        break;
                    }

                    YearMonth currentMonth = YearMonth.now();

                    String cancellationKey = cancelBooking.userId + "_" + currentMonth;

                    int count = cancellationCount.getOrDefault(cancellationKey, 0);

                    if (count >= 3) {

                        System.out.println("Cancellation Limit Exceeded!");
                        System.out.println("Maximum 3 cancellations allowed per month.");
                        break;
                    }
                    // Step 11: Select Refund Method

                    System.out.println("\n========== REFUND METHOD ==========");
                    System.out.println("1. Wallet (Instant Refund)");
                    System.out.println("2. Original Payment Method");

                    System.out.print("Select Refund Method: ");

                    int refundChoice;

                    try {
                        refundChoice = Integer.parseInt(sc.nextLine().trim());

                    } catch (NumberFormatException e) {
                        System.out.println("Invalid Input! Please Enter a Valid Refund Option.");
                        break;
                    }

                    if (refundChoice == 1) {

                        cancelBooking.refundMethod = "WALLET";

                        User refundUser = users.get(cancelBooking.userId);

                        if (refundUser != null) {
                            refundUser.creditRefund(refundAmount);
                        }

                        cancelBooking.refundStatus = "REFUNDED";

                    } else if (refundChoice == 2) {

                        cancelBooking.refundMethod = "ORIGINAL PAYMENT METHOD";
                        cancelBooking.refundStatus = "PROCESSING";

                    } else {

                        System.out.println("Invalid Refund Option!");
                        break;
                    }
                    // Release Seats
                    for (String seat : cancelBooking.seatNumbers) {

                        int row = seat.charAt(0) - 'A';
                        int col = seat.charAt(1) - '1';

                        cancelBooking.selectedShow.theatre.cancelSeat(row, col);
                    }

                    // Update Booking
                    cancelBooking.bookingStatus = "CANCELLED";
                    cancelBooking.refundAmount = refundAmount;

                    // Update Cancellation Count
                    cancellationCount.put(cancellationKey, count + 1);
                    // Step 12: Display Final Result
                    System.out.println("\n========== CANCELLATION SUCCESSFUL ==========");

                    System.out.println("Booking ID    : " + cancelBooking.bookingId);
                    System.out.println("Booking Status: " + cancelBooking.bookingStatus);
                    System.out.println("Refund Status : " + cancelBooking.refundStatus);
                    System.out.println("Refund Amount : Rs. " + cancelBooking.refundAmount);

                    System.out.println("Your Seats Have Been Released!");

                    break;

                // Exit
                case 7:

                    System.out.println("Thank You!");

                    break;

                // Book Movie Ticket
                case 8:

                    // Login Authentication
                    if (loggedInUser == null) {
                        System.out.println("Please Login First!");
                        break;
                    }
                    // Step 1: Display Movies
                    System.out.println("\n========== AVAILABLE MOVIES ==========");

                    for (int i = 0; i < movies.size(); i++) {

                        System.out.println(
                                (i + 1) + ". " + movies.get(i).movieName
                        );
                    }

                    // Step 2: Select Movie
                    System.out.print("Select Movie Number: ");

                    int movieChoice;

                    try {
                        movieChoice = Integer.parseInt(sc.nextLine().trim());
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid Input! Please Enter a Valid Movie Number.");
                        break;
                    }

                    if (movieChoice < 1 || movieChoice > movies.size()) {

                        System.out.println("Invalid Movie Selection!");
                        break;
                    }

                    Movie selectedMovie = movies.get(movieChoice - 1);

                    // Step 3: Display Available Dates
                    ArrayList<LocalDate> availableDates = new ArrayList<>();

                    for (Show s : shows) {

                        if (s.movieId == selectedMovie.movieId &&
                                !availableDates.contains(s.showDate)) {

                            availableDates.add(s.showDate);
                        }
                    }

                    System.out.println("\n========== AVAILABLE DATES ==========");

                    for (int i = 0; i < availableDates.size(); i++) {

                        System.out.println(
                                (i + 1) + ". " + availableDates.get(i)
                        );
                    }

                    // Step 4: Select Date
                    System.out.print("Select Date: ");

                    int dateChoice;

                    try {
                        dateChoice = Integer.parseInt(sc.nextLine().trim());
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid Input! Please Enter a Valid Date Number.");
                        break;
                    }


                    if (dateChoice < 1 ||
                            dateChoice > availableDates.size()) {

                        System.out.println("Invalid Date Selection!");
                        break;
                    }

                    LocalDate selectedDate =
                            availableDates.get(dateChoice - 1);

                    // Step 5: Display Available Shows
                    ArrayList<Show> availableShows = new ArrayList<>();

                    System.out.println("\n========== AVAILABLE SHOWS ==========");

                    DateTimeFormatter timeFormatter =
                            DateTimeFormatter.ofPattern("hh:mm a");

                    for (Show s : shows) {

                        if (s.movieId == selectedMovie.movieId &&
                                s.showDate.equals(selectedDate)) {

                            availableShows.add(s);

                            System.out.println(
                                    availableShows.size() + ". " +
                                            s.showTime.format(timeFormatter)
                            );
                        }
                    }

                    // Step 6: Select Show
                    System.out.print("Select Show: ");

                    int showChoice;

                    try {
                        showChoice = Integer.parseInt(sc.nextLine().trim());
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid Input! Please Enter a Valid Show Number.");
                        break;
                    }

                    if (showChoice < 1 ||
                            showChoice > availableShows.size()) {

                        System.out.println("Invalid Show Selection!");
                        break;
                    }

                    Show selectedShow =
                            availableShows.get(showChoice - 1);

                    LocalDateTime showDateTime =
                            LocalDateTime.of(
                                    selectedShow.showDate,
                                    selectedShow.showTime
                            );

                    System.out.println("\n========== SELECTED SHOW ==========");

                    System.out.println("Movie   : " + selectedShow.movieName);
                    System.out.println("Date    : " + selectedShow.showDate);
                    System.out.println(
                            "Time    : " +
                                    selectedShow.showTime.format(timeFormatter)
                    );
                    System.out.println("Theatre : " + selectedShow.theatreName);

                    // Step 7: Display Seats (Show-wise)
                    selectedShow.theatre.displaySeats();

                    // Step 8: Number of Seats
                    System.out.print("How many seats do you want to book? ");


                    int totalSeats;

                    try {
                        totalSeats = Integer.parseInt(sc.nextLine().trim());

                    } catch (NumberFormatException e) {
                        System.out.println("Invalid Input! Please Enter a Valid Number.");
                        break;
                    }
                    if (totalSeats < 1 || totalSeats > 25) {

                        System.out.println("Invalid Number of Seats!");
                        break;
                    }

                    ArrayList<String> selectedSeats = new ArrayList<>();

                    double seatCharges = 0;

                    boolean validBooking = true;

                    // Step 9: Select Multiple Seats
                    for (int i = 1; i <= totalSeats; i++) {

                        System.out.print(
                                "Select Seat " + i + " (Example: C2): "
                        );

                        String seat = sc.nextLine().trim().toUpperCase();

                        // Validate Seat Format
                        if (seat.length() != 2 ||
                                seat.charAt(0) < 'A' ||
                                seat.charAt(0) > 'E' ||
                                seat.charAt(1) < '1' ||
                                seat.charAt(1) > '5') {

                            System.out.println("Invalid Seat Number!");

                            validBooking = false;
                            break;
                        }

                        int rowIndex = seat.charAt(0) - 'A';
                        int colIndex = seat.charAt(1) - '1';

                        // Check Duplicate Seat
                        if (selectedSeats.contains(seat)) {

                            System.out.println(
                                    "You Already Selected This Seat!"
                            );

                            validBooking = false;
                            break;
                        }

                        // Check Availability (Show-wise)
                        if (!selectedShow.theatre.isAvailable(rowIndex, colIndex)) {

                            System.out.println(
                                    "Seat " + seat + " Already Booked!"
                            );

                            validBooking = false;
                            break;
                        }

                        selectedSeats.add(seat);

                        seatCharges +=
                                selectedShow.theatre.getFinalSeatPrice(rowIndex);
                    }

                    if (!validBooking) {

                        System.out.println(
                                "Booking Failed! Please Try Again."
                        );

                        break;
                    }

                    // Step 10: Calculate Price
                    double ticketAmount =
                            selectedMovie.ticketPrice * totalSeats;

                    double subtotal = ticketAmount + seatCharges;

                    double convenienceFee = 20;

                    double taxAmount = 0;

                    double finalAmount =
                            subtotal + convenienceFee + taxAmount;

                    // Step 11: Booking Summary
                    System.out.println(
                            "\n============== BOOKING SUMMARY =============="
                    );

                    System.out.println(
                            "Movie         : " + selectedMovie.movieName
                    );

                    System.out.println("Date          : " + selectedDate);

                    System.out.println(
                            "Show Time     : " +
                                    selectedShow.showTime.format(timeFormatter)
                    );

                    System.out.println("Seats         : " + selectedSeats);

                    System.out.println("Total Seats   : " + totalSeats);

                    System.out.printf(
                            "Ticket Price  : Rs. %.2f%n", ticketAmount
                    );

                    System.out.printf(
                            "Seat Charges  : Rs. %.2f%n", seatCharges
                    );

                    System.out.printf(
                            "Subtotal      : Rs. %.2f%n", subtotal
                    );

                    System.out.printf(
                            "Convenience   : Rs. %.2f%n", convenienceFee
                    );

                    System.out.printf(
                            "Tax           : Rs. %.2f%n", taxAmount
                    );

                    System.out.printf(
                            "Total Amount  : Rs. %.2f%n", finalAmount
                    );

                    System.out.println(
                            "=============================================="
                    );

                    // Step 12: User Details
                    // Step 12: User Details
                    String UserId = loggedInUser.userId;

                    User currentUser = loggedInUser;

                    String customerName = currentUser.userName;

                    if (customerName.isEmpty()) {

                        System.out.println(
                                "Customer Name Cannot Be Empty!"
                        );

                        break;
                    }

                    // Step 13: Confirm Booking

                    while (true) {

                        System.out.print("Confirm Booking? (yes/no): ");

                        String confirmation = sc.nextLine().trim();

                        if (confirmation.equalsIgnoreCase("yes")) {

                            System.out.println("Proceeding to Payment...");
                            break;
                        }

                        else if (confirmation.equalsIgnoreCase("no")) {

                            System.out.println("Booking Cancelled!");

                            validBooking = false;
                            break;
                        }

                        else {

                            System.out.println("Invalid Input! Please Enter Yes or No.");
                        }
                    }

                    if (!validBooking) {
                        break;
                    }


                    // Step 14: Select Payment Method

                    System.out.println("\n========== PAYMENT METHOD ==========");
                    System.out.println("1. Wallet Payment");
                    System.out.println("2. UPI Payment");
                    System.out.println("3. Cash Payment");

                    System.out.print("Select Payment Method: ");

                    int paymentChoice;

                    try {
                        paymentChoice = Integer.parseInt(sc.nextLine().trim());

                    } catch (NumberFormatException e) {
                        System.out.println("Invalid Input! Please Enter a Valid Payment Option.");
                        break;
                    }

                    boolean paymentSuccessful = false;

                    String transactionId = "N/A";
                    String paymentMethod = "UNKNOWN";


                    // Step 15: Process Payment

                    switch (paymentChoice) {

                        case 1:
                            paymentMethod = "WALLET";
                            paymentSuccessful = currentUser.deductMoney(finalAmount);

                            if (paymentSuccessful) {
                                transactionId = "WALLET-" + (1001 + bookings.size());
                            }

                            break;


                        case 2:
                            paymentMethod="UPI";
                            System.out.print("Enter UPI Transaction ID: ");

                            transactionId = sc.nextLine().trim();

                            if (!transactionId.isEmpty()) {

                                System.out.println("UPI Payment Successful!");

                                paymentSuccessful = true;

                            } else {

                                System.out.println("Invalid Transaction ID!");
                            }

                            break;


                        case 3:
                            paymentMethod="CASH";
                            System.out.println("Cash Payment Selected.");
                            System.out.println("Cash Payment Successful!");

                            paymentSuccessful = true;

                            transactionId = "CASH-" + (1001 + bookings.size());

                            break;


                        default:

                            System.out.println("Invalid Payment Option!");
                    }


                        // Step 16: Payment Validation

                    if (!paymentSuccessful) {

                        System.out.println("Payment Failed! Booking Not Completed.");

                        break;
                    }


                    // Step 17: Generate Booking ID

                    String bookingId = "SM" + (1001 + bookings.size());


                    // Step 18: Create Booking

                    Booking b = new Booking(
                            bookingId,
                            customerName,
                            selectedMovie.movieName,
                            selectedSeats,
                            ticketAmount,
                            seatCharges,
                            convenienceFee,
                            taxAmount,
                            finalAmount,
                            showDateTime,
                            selectedShow,
                            UserId

                    );
                    b.paymentMethod = paymentMethod;
                    b.transactionId = transactionId;


                    // Step 19: Book All Seats

                    for (String seat : selectedSeats) {

                        int rows = seat.charAt(0) - 'A';
                        int cols = seat.charAt(1) - '1';

                        selectedShow.theatre.bookSeat(rows, cols);
                    }


                    // Step 20: Save Booking

                    bookings.put(bookingId, b);


                    // Step 21: Display E-Ticket

                    b.displayBooking();

                    break;

                // Search Booking
                case 9:

                    System.out.println("\n========== SEARCH BOOKING ==========");

                    // Login Check
                    if (loggedInUser == null) {
                        System.out.println("Please Login First!");
                        break;
                    }

                    String searchUserId = loggedInUser.userId;

                    System.out.print("Enter Booking ID: ");
                    String id = sc.nextLine().trim().toUpperCase();

                    if (id.isEmpty()) {
                        System.out.println("Booking ID Cannot Be Empty!");
                        break;
                    }

                    if (bookings.containsKey(id)) {

                        Booking searchedBooking = bookings.get(id);

                        if (searchUserId.equals(searchedBooking.userId)) {
                            searchedBooking.displayBooking();
                        } else {
                            System.out.println("Access Denied!");
                            System.out.println("This Booking Does Not Belong To You.");
                        }

                    } else {
                        System.out.println("Booking Not Found!");
                    }

                    break;

                case 10:

                    System.out.println("\n========== USER REGISTRATION ==========");

                    System.out.print("Enter Your Name: ");
                    String userName = sc.nextLine().trim();

                    System.out.print("Enter Email: ");
                    String email = sc.nextLine().trim();

                    System.out.print("Create Password: ");
                    String password = sc.nextLine();

                    if (userName.isEmpty() || email.isEmpty() || password.trim().isEmpty()) {
                        System.out.println("All Fields Are Required!");
                        break;
                    }

                    if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
                        System.out.println("Invalid Email Format!");
                        break;
                    }

                    if (password.length() < 6) {
                        System.out.println("Password Must Be At Least 6 Characters!");
                        break;
                    }

                    String userId = "USER" + nextUserId++;

                    User newUser = new User(
                            userId,
                            userName,
                            email,
                            password
                    );

                    users.put(userId, newUser);

                    System.out.println("\nRegistration Successful!");
                    System.out.println("Your User ID: " + userId);
                    System.out.println("Please Save Your User ID.");

                    break;

                case 11:

                    System.out.println("\n========== USER DETAILS ==========");

                    if (loggedInUser == null) {
                        System.out.println("Please Login First!");
                        break;
                    }

                    loggedInUser.displayUser();

                    break;


                case 12:

                    System.out.println("\n========== WALLET BALANCE ==========");

                    if (loggedInUser == null) {
                        System.out.println("Please Login First!");
                        break;
                    }

                    loggedInUser.displayWallet();

                    break;



                case 13:

                    System.out.println("\n========== ADD MONEY TO WALLET ==========");

                    // Login Authentication
                    if (loggedInUser == null) {
                        System.out.println("Please Login First!");
                        break;
                    }

                    System.out.print("Enter Amount: ");

                    double amount;

                    try {
                        amount = Double.parseDouble(sc.nextLine().trim());

                    } catch (NumberFormatException e) {
                        System.out.println("Invalid Amount! Please Enter a Number.");
                        break;
                    }

                    // Amount Validation
                    if (!Double.isFinite(amount) || amount <= 0) {
                        System.out.println("Amount Must Be Greater Than Zero!");
                        break;
                    }

                    loggedInUser.addMoney(amount);

                    break;

                case 14:

                    System.out.println("\n========== TRANSACTION HISTORY ==========");

                    // Login Authentication
                    if (loggedInUser == null) {
                        System.out.println("Please Login First!");
                        break;
                    }

                    loggedInUser.displayTransactions();

                    break;

                // Case 15: My Bookings

                case 15:

                    System.out.println("\n========== MY BOOKINGS ==========");
                    if (loggedInUser == null) {
                        System.out.println("Please Login First!");
                        break;
                    }

                    String bookingUserId = loggedInUser.userId;

                    // Store User Bookings
                    ArrayList<Booking> userBookings = new ArrayList<>();

                    for (Booking booking : bookings.values()) {

                        if (bookingUserId.equals(booking.userId)) {
                            userBookings.add(booking);
                        }
                    }

                    if (userBookings.isEmpty()) {
                        System.out.println("No Bookings Found!");
                        break;
                    }

                    // Sort Latest Booking First
                    userBookings.sort((b1, b2) ->
                            b2.bookingCreatedAt.compareTo(b1.bookingCreatedAt));

                    // Display Bookings
                    for (Booking booking : userBookings) {

                        System.out.println("\n--------------------------------");

                        booking.displayBooking();
                    }
                    // Booking Statistics

                    int totalBookings = userBookings.size();

                    int confirmedBookings = 0;
                    int cancelledBookings = 0;

                    double totalMoneySpent = 0;
                    double totalRefundReceived = 0;

                    for (Booking booking : userBookings) {

                        if (booking.bookingStatus.equals("CONFIRMED")) {
                            confirmedBookings++;
                        }

                        else if (booking.bookingStatus.equals("CANCELLED")) {
                            cancelledBookings++;
                        }

                        if (booking.paymentStatus.equals("PAID")) {
                            totalMoneySpent += booking.amount;
                        }

                        totalRefundReceived += booking.refundAmount;
                    }

                    double netMoneySpent = totalMoneySpent - totalRefundReceived;

                    System.out.println("\n========== BOOKING STATISTICS ==========");

                    System.out.println("Total Bookings       : " + totalBookings);
                    System.out.println("Confirmed Bookings   : " + confirmedBookings);
                    System.out.println("Cancelled Bookings   : " + cancelledBookings);

                    System.out.printf("Total Money Paid     : Rs. %.2f%n", totalMoneySpent);

                    System.out.printf("Total Refund Received: Rs. %.2f%n", totalRefundReceived);

                    System.out.printf("Net Money Spent      : Rs. %.2f%n", netMoneySpent);

                    System.out.println("========================================");

                    System.out.println("\n================================");

                    break;

                // Case 16: Admin Login

                case 16: {

                    System.out.println("\n========== ADMIN LOGIN ==========");

                    System.out.print("Enter Admin Username: ");
                    String adminUsername = sc.nextLine().trim();

                    System.out.print("Enter Admin Password: ");
                    String adminPassword = sc.nextLine().trim();

                    // Admin Authentication

                    if (adminUsername.equals("admin") &&
                            adminPassword.equals("admin123")) {

                        System.out.println("\nAdmin Login Successful!");

                        int adminChoice=0;

                        do {

                            System.out.println("1. View All Movies");
                            System.out.println("2. View All Bookings");
                            System.out.println("3. View All Registered Users");
                            System.out.println("4. Add New Movie");
                            System.out.println("5. Remove Movie");
                            System.out.println("6. Add New Show");
                            System.out.println("7. View All Shows");
                            System.out.println("8. Delete Show");
                            System.out.println("9. Revenue Dashboard");
                            System.out.println("10. Booking Management");
                            System.out.println("11. Logout");

                            System.out.print("Enter Choice: ");


                            if (sc.hasNextInt()) {
                                adminChoice = sc.nextInt();
                                sc.nextLine();
                            } else {
                                System.out.println("Invalid Input! Please Enter a Number.");
                                sc.nextLine();
                                continue;
                            }

                            switch (adminChoice) {

                                case 1:

                                    System.out.println("\n========== ALL MOVIES ==========");

                                    for (Movie movie : movies) {
                                        movie.displayMovie();
                                    }

                                    break;


                                case 2:

                                    System.out.println("\n========== ALL BOOKINGS ==========");

                                    if (bookings.isEmpty()) {

                                        System.out.println("No Bookings Found!");

                                    } else {

                                        for (Booking booking : bookings.values()) {
                                            booking.displayBooking();
                                        }
                                    }

                                    break;


                                case 3:

                                    System.out.println("\n========== REGISTERED USERS ==========");

                                    if (users.isEmpty()) {

                                        System.out.println("No Users Registered!");

                                    } else {

                                        for (User user : users.values()) {
                                            user.displayUser();
                                        }
                                    }

                                    break;

                                case 4:

                                    System.out.println("\n========== ADD NEW MOVIE ==========");

                                    int movieId;
                                    double rating;
                                    double ticketPrice;

                                    try {
                                        System.out.print("Enter Movie ID: ");
                                        movieId = Integer.parseInt(sc.nextLine().trim());

                                        System.out.print("Enter Movie Name: ");
                                        String movieName = sc.nextLine().trim();

                                        System.out.print("Enter Genre: ");
                                        String genre = sc.nextLine().trim();

                                        System.out.print("Enter Rating: ");
                                        rating = Double.parseDouble(sc.nextLine().trim());

                                        System.out.print("Enter Ticket Price: ");
                                        ticketPrice = Double.parseDouble(sc.nextLine().trim());

                                        if (movieId <= 0 ||
                                                movieName.isEmpty() ||
                                                genre.isEmpty() ||
                                                !Double.isFinite(rating) ||
                                                !Double.isFinite(ticketPrice) ||
                                                rating < 0 || rating > 10 ||
                                                ticketPrice <= 0) {

                                            System.out.println("Invalid Movie Details!");
                                            break;
                                        }

                                        boolean movieExists = false;

                                        for (Movie movie : movies) {
                                            if (movie.movieId == movieId) {
                                                movieExists = true;
                                                break;
                                            }
                                        }

                                        if (movieExists) {
                                            System.out.println("Movie ID Already Exists!");
                                            break;
                                        }

                                        Movie newMovie = new Movie(
                                                movieId,
                                                movieName,
                                                genre,
                                                rating,
                                                ticketPrice
                                        );

                                        movies.add(newMovie);

                                        System.out.println("Movie Added Successfully!");

                                    } catch (NumberFormatException e) {
                                        System.out.println("Invalid Input! Please Enter Valid Numbers.");
                                    }

                                    break;

                                case 5:

                                    System.out.println("\n========== REMOVE MOVIE ==========");

                                    if (movies.isEmpty()) {
                                        System.out.println("No Movies Available!");
                                        break;
                                    }

                                    int removeId;

                                    try {
                                        System.out.print("Enter Movie ID to Remove: ");
                                        removeId = Integer.parseInt(sc.nextLine().trim());

                                    } catch (NumberFormatException e) {
                                        System.out.println("Invalid Input! Please Enter a Valid Movie ID.");
                                        break;
                                    }

                                    if (removeId <= 0) {
                                        System.out.println("Movie ID Must Be Positive!");
                                        break;
                                    }

                                    Movie movieToRemove = null;

                                    // Find Movie
                                    for (Movie movie : movies) {
                                        if (movie.movieId == removeId) {
                                            movieToRemove = movie;
                                            break;
                                        }
                                    }

                                    if (movieToRemove == null) {
                                        System.out.println("Movie Not Found!");
                                        break;
                                    }

                                    System.out.println("Movie Found: " + movieToRemove.movieName);

                                    // Check Confirmed Bookings
                                    boolean hasNewBooking = false;

                                    for (Booking booking : bookings.values()) {

                                        if (booking.selectedShow != null &&
                                                booking.selectedShow.movieId == removeId &&
                                                booking.bookingStatus.equals("CONFIRMED")) {

                                            hasNewBooking = true;
                                            break;
                                        }
                                    }

                                    if (hasNewBooking) {
                                        System.out.println("Cannot Remove Movie!");
                                        System.out.println("This Movie Has Confirmed Bookings.");
                                        break;
                                    }

                                    // Delete Confirmation
                                    System.out.print("Are You Sure You Want to Remove This Movie? (Y/N): ");
                                    String confirmation = sc.nextLine().trim();

                                    if (!confirmation.equalsIgnoreCase("Y")) {
                                        System.out.println("Movie Removal Cancelled!");
                                        break;
                                    }

                                    // Remove Associated Shows
                                    shows.removeIf(show -> show.movieId == removeId);

                                    // Remove Movie
                                    movies.remove(movieToRemove);

                                    System.out.println("Movie Removed Successfully!");
                                    System.out.println("Associated Shows Removed Successfully!");

                                    break;


                                case 6:

                                    System.out.println("\n========== ADD NEW SHOW ==========");

                                    if (movies.isEmpty()) {
                                        System.out.println("No Movies Available!");
                                        break;
                                    }

                                    // Display Movies
                                    for (Movie movie : movies) {
                                        System.out.println(movie.movieId + ". " + movie.movieName);
                                    }

                                    int selectedMovieId;

                                    try {
                                        System.out.print("Enter Movie ID: ");
                                        selectedMovieId = Integer.parseInt(sc.nextLine().trim());

                                    } catch (NumberFormatException e) {
                                        System.out.println("Invalid Input! Please Enter a Valid Movie ID.");
                                        break;
                                    }

                                    if (selectedMovieId <= 0) {
                                        System.out.println("Movie ID Must Be Positive!");
                                        break;
                                    }
                                    Movie showMovie = null;

                                    for (Movie movie : movies) {
                                        if (movie.movieId == selectedMovieId) {
                                            showMovie = movie;
                                            break;
                                        }
                                    }

                                    if (showMovie == null) {
                                        System.out.println("Movie Not Found!");
                                        break;
                                    }

                                    int newShowId = 1001;

                                    for (Show s : shows) {
                                        if (s.showId >= newShowId) {
                                            newShowId = s.showId + 1;
                                        }
                                    }

                                    System.out.println("Generated Show ID: " + newShowId);

                                    LocalDate showDate;
                                    LocalTime newShowTime;

                                    try {
                                        System.out.print("Enter Show Date (YYYY-MM-DD): ");
                                        showDate = LocalDate.parse(sc.nextLine());

                                        System.out.print("Enter Show Time (HH:MM): ");
                                        newShowTime = LocalTime.parse(sc.nextLine());

                                    } catch (java.time.format.DateTimeParseException e) {
                                        System.out.println("Invalid Date or Time Format!");
                                        System.out.println("Please use YYYY-MM-DD and HH:MM.");
                                        break;
                                    }

                                    if (showDate.isBefore(LocalDate.now())) {
                                        System.out.println("Cannot Schedule Show in the Past!");
                                        break;
                                    }
                                    if (showDate.equals(LocalDate.now()) &&
                                            newShowTime.isBefore(LocalTime.now())) {

                                        System.out.println("Cannot Schedule Show in the Past!");
                                        break;
                                    }

                                    System.out.print("Enter Theatre Name: ");
                                    String theatreName = sc.nextLine().trim();

                                    if (theatreName.isEmpty()) {
                                        System.out.println("Theatre Name Cannot Be Empty!");
                                        break;
                                    }

                                    boolean duplicateShow = false;

                                    for (Show s : shows) {
                                        if (s.movieId == showMovie.movieId &&
                                                s.showDate.equals(showDate) &&
                                                s.showTime.equals(newShowTime) &&
                                                s.theatreName.equalsIgnoreCase(theatreName)) {

                                            duplicateShow = true;
                                            break;
                                        }
                                    }

                                    if (duplicateShow) {
                                        System.out.println("This Show Already Exists!");
                                        break;
                                    }

                                    Show newShow = new Show(
                                            newShowId,
                                            showMovie.movieId,
                                            showMovie.movieName,
                                            showDate,
                                            newShowTime,
                                            theatreName
                                    );

                                    shows.add(newShow);

                                    System.out.println("\nShow Added Successfully!");

                                    newShow.displayShow();

                                    break;
                                case 7:

                                    System.out.println("\n========== ALL SHOWS ==========");

                                    if (shows.isEmpty()) {
                                        System.out.println("No Shows Available!");
                                        break;
                                    }

                                    // Create a copy of Shows
                                    ArrayList<Show> sortedShows = new ArrayList<>(shows);

                                    // Sort by Date and Time
                                    sortedShows.sort(
                                            Comparator.comparing((Show s) -> s.showDate)
                                                    .thenComparing(s -> s.showTime)
                                    );

                                    // Display Shows
                                    for (Show show : sortedShows) {
                                        show.displayShow();
                                    }

                                    // Total Shows Count
                                    System.out.println("--------------------------------");
                                    System.out.println("Total Shows Available: " + shows.size());

                                    break;

                                case 8:

                                    System.out.println("\n========== DELETE SHOW ==========");

                                    if (shows.isEmpty()) {
                                        System.out.println("No Shows Available!");
                                        break;
                                    }

                                    // Display All Shows
                                    for (Show show : shows) {
                                        show.displayShow();
                                    }

                                    // Input Validation
                                    int deleteShowId;

                                    try {
                                        System.out.print("Enter Show ID to Delete: ");
                                        deleteShowId = Integer.parseInt(sc.nextLine().trim());

                                    } catch (NumberFormatException e) {
                                        System.out.println("Invalid Input! Please Enter a Valid Show ID.");
                                        break;
                                    }

                                    if (deleteShowId <= 0) {
                                        System.out.println("Show ID Must Be Positive!");
                                        break;
                                    }

                                    Show showToDelete = null;

                                    // Find Show
                                    for (Show show : shows) {
                                        if (show.showId == deleteShowId) {
                                            showToDelete = show;
                                            break;
                                        }
                                    }

                                    if (showToDelete == null) {
                                        System.out.println("Show Not Found!");
                                        break;
                                    }

                                    // Check Existing Bookings
                                    boolean hasBooking = false;

                                    for (Booking booking : bookings.values()) {
                                        if (booking.selectedShow != null &&
                                                booking.selectedShow.showId == deleteShowId &&
                                                booking.bookingStatus.equals("CONFIRMED")) {

                                            hasBooking = true;
                                            break;
                                        }
                                    }

                                    if (hasBooking) {
                                        System.out.println("Cannot Delete Show!");
                                        System.out.println("This Show Has Confirmed Bookings.");
                                        break;
                                    }

                                    // Delete Confirmation
                                    System.out.print("Are You Sure You Want to Delete This Show? (Y/N): ");
                                    String Confirmation = sc.nextLine().trim();

                                    if (!Confirmation.equalsIgnoreCase("Y")) {
                                        System.out.println("Show Deletion Cancelled!");
                                        break;
                                    }

                                    shows.remove(showToDelete);

                                    System.out.println("Show Deleted Successfully!");

                                    break;

                                case 9:

                                    System.out.println("\n========== REVENUE DASHBOARD ==========");

                                    if (bookings.isEmpty()) {
                                        System.out.println("No Booking Records Available!");
                                        break;
                                    }

                                    int TotalBookings = bookings.size();

                                    int ConfirmedBookings = 0;
                                    int CancelledBookings = 0;

                                    double totalRevenue = 0;
                                    double totalRefund = 0;

                                    for (Booking booking : bookings.values()) {

                                        // Count Confirmed Bookings
                                        if (booking.bookingStatus.equals("CONFIRMED")) {
                                            ConfirmedBookings++;
                                        }

                                        // Count Cancelled Bookings
                                        else if (booking.bookingStatus.equals("CANCELLED")) {
                                            CancelledBookings++;
                                        }

                                        // Calculate Total Revenue
                                        if (booking.paymentStatus.equals("PAID")) {
                                            totalRevenue += booking.amount;
                                        }

                                        // Calculate Total Refund
                                        totalRefund += booking.refundAmount;
                                    }

                                    double netRevenue = totalRevenue - totalRefund;

                                    System.out.println("\n---------- BOOKING SUMMARY ----------");

                                    System.out.println("Total Bookings      : " + TotalBookings);
                                    System.out.println("Confirmed Bookings  : " + ConfirmedBookings);
                                    System.out.println("Cancelled Bookings  : " + CancelledBookings);

                                    System.out.println("\n---------- FINANCIAL SUMMARY ----------");

                                    System.out.printf("Total Revenue       : Rs. %.2f%n", totalRevenue);
                                    System.out.printf("Total Refund Amount : Rs. %.2f%n", totalRefund);
                                    System.out.printf("Net Revenue         : Rs. %.2f%n", netRevenue);

                                    System.out.println("=======================================");

                                    break;

                                case 10: {

                                    int bookingChoice=0;

                                    do {

                                        System.out.println("\n========== BOOKING MANAGEMENT ==========");
                                        System.out.println("1. Search Booking by ID");
                                        System.out.println("2. View Confirmed Bookings");
                                        System.out.println("3. View Cancelled Bookings");
                                        System.out.println("4. Search by Customer Name");
                                        System.out.println("5. Back to Admin Panel");

                                        System.out.print("Enter Choice: ");

                                        if (sc.hasNextInt()) {
                                            bookingChoice = sc.nextInt();
                                            sc.nextLine();
                                        } else {
                                            System.out.println("Invalid Input! Please Enter a Number.");
                                            sc.nextLine();
                                            continue;
                                        }
                                        switch (bookingChoice) {

                                            // Search Booking by ID
                                            case 1:

                                                String searchBookingId =
                                                        sc.nextLine().trim().toUpperCase();

                                                if (searchBookingId.isEmpty()) {
                                                    System.out.println("Booking ID Cannot Be Empty!");
                                                    break;
                                                }

                                                if (bookings.containsKey(searchBookingId)) {

                                                    bookings.get(searchBookingId).displayBooking();

                                                } else {

                                                    System.out.println("Booking Not Found!");
                                                }

                                                break;


                                            // View Confirmed Bookings
                                            case 2:

                                                System.out.println("\n========== CONFIRMED BOOKINGS ==========");

                                                boolean confirmedFound = false;

                                                ArrayList<Booking> sortedCancelledBookings =
                                                        new ArrayList<>(bookings.values());

                                                sortedCancelledBookings.sort((b1, b2) ->
                                                        b2.bookingCreatedAt.compareTo(b1.bookingCreatedAt));

                                                for (Booking booking : sortedCancelledBookings) {

                                                    if ("CONFIRMED".equalsIgnoreCase(
                                                            booking.bookingStatus)) {

                                                        booking.displayBooking();

                                                        confirmedFound = true;
                                                    }
                                                }

                                                if (!confirmedFound) {
                                                    System.out.println("No Confirmed Bookings Found!");
                                                }

                                                break;


                                            // View Cancelled Bookings
                                            case 3:

                                                System.out.println("\n========== CANCELLED BOOKINGS ==========");

                                                boolean cancelledFound = false;

                                                ArrayList<Booking> Bookings =
                                                        new ArrayList<>(bookings.values());

                                                Bookings.sort((b1, b2) ->
                                                        b2.bookingCreatedAt.compareTo(b1.bookingCreatedAt));

                                                for (Booking booking : Bookings) {

                                                    if ("CANCELLED".equalsIgnoreCase(
                                                            booking.bookingStatus)) {

                                                        booking.displayBooking();

                                                        cancelledFound = true;
                                                    }
                                                }

                                                if (!cancelledFound) {
                                                    System.out.println("No Cancelled Bookings Found!");
                                                }

                                                break;


                                            // Search by Customer Name
                                            case 4:

                                                System.out.print("Enter Customer Name: ");

                                                String searchCustomer =
                                                        sc.nextLine().trim();

                                                if (searchCustomer.isEmpty()) {
                                                    System.out.println("Customer Name Cannot Be Empty!");
                                                    break;
                                                }

                                                boolean customerFound = false;

                                                ArrayList<Booking> sortedCustomerBookings =
                                                        new ArrayList<>(bookings.values());

                                                sortedCustomerBookings.sort((b1, b2) ->
                                                        b2.bookingCreatedAt.compareTo(b1.bookingCreatedAt));

                                                for (Booking booking : sortedCustomerBookings) {

                                                    if (booking.customerName != null &&
                                                            booking.customerName.equalsIgnoreCase(
                                                                    searchCustomer)) {

                                                        booking.displayBooking();

                                                        customerFound = true;
                                                    }
                                                }

                                                if (!customerFound) {
                                                    System.out.println("No Booking Found for This Customer!");
                                                }

                                                break;


                                            case 5:

                                                System.out.println("Returning to Admin Panel...");

                                                break;


                                            default:

                                                System.out.println("Invalid Choice!");
                                        }

                                    } while (bookingChoice != 5);

                                    break;
                                }


                                case 11:

                                    System.out.println("Admin Logged Out Successfully!");

                                    break;


                                default:

                                    System.out.println("Invalid Admin Choice!");
                            }

                        } while (adminChoice != 11);

                    } else {

                        System.out.println("Invalid Admin Username or Password!");

                    }

                    break;
                }

                case 17:
                    System.out.println("\n========== USER LOGIN ==========");

                    if (loggedInUser != null) {
                        System.out.println("Already Logged In!");
                        System.out.println("Welcome, " + loggedInUser.userName);
                        break;
                    }

                    System.out.print("Enter User ID: ");
                    String loginUserId = sc.nextLine().trim().toUpperCase();

                    System.out.print("Enter Password: ");
                    String loginPassword = sc.nextLine();

                    User loginUser = users.get(loginUserId);

                    if (loginUser == null) {
                        System.out.println("User Not Found! Please Register First.");
                    } else if (loginUser.verifyPassword(loginPassword)) {
                        loggedInUser = loginUser;
                        System.out.println("Login Successful!");
                        System.out.println("Welcome, " + loggedInUser.userName);
                    } else {
                        System.out.println("Incorrect Password! Please Try Again.");
                    }

                    break;

                case 18:
                    System.out.println("\n========== USER LOGOUT ==========");

                    if (loggedInUser == null) {
                        System.out.println("No User Is Currently Logged In!");
                    } else {
                        System.out.println("Goodbye, " + loggedInUser.userName + "!");
                        loggedInUser = null;
                        System.out.println("Logout Successful!");
                    }

                    break;


                default:

                    System.out.println("Invalid Choice!");
            }

        } while (choice != 7);

        sc.close();
    }
}
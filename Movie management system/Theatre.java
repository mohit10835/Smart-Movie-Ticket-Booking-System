public class Theatre {

    int[][] seats = new int[5][5];

    // Get Seat Category
    String getSeatCategory(int row) {

        if (row >= 0 && row <= 2) {
            return "REGULAR";
        }
        else if (row == 3) {
            return "PREMIUM";
        }
        else if (row == 4) {
            return "VIP";
        }

        return "INVALID";
    }

    // Get Base Seat Price
    double getSeatPrice(int row) {

        if (row == 0) {
            return 100;
        }
        else if (row == 1) {
            return 120;
        }
        else if (row == 2) {
            return 150;
        }
        else if (row == 3) {
            return 180;
        }
        else if (row == 4) {
            return 220;
        }

        return 0;
    }

    // Get Additional Charges
    double getAdditionalCharge(int row) {

        if (row == 3) {
            return 100;  // Premium
        }
        else if (row == 4) {
            return 200;  // VIP
        }

        return 0;
    }

    // Get Final Seat Price
    double getFinalSeatPrice(int row) {

        return getSeatPrice(row) + getAdditionalCharge(row);
    }

    // Check Seat Availability
    boolean isAvailable(int row, int col) {

        if (row < 0 || row >= 5 || col < 0 || col >= 5) {
            return false;
        }

        return seats[row][col] == 0;
    }

    // Display All Seats
    void displaySeats() {

        System.out.println("\n========== THEATRE LAYOUT ==========");
        System.out.println("                 SCREEN");
        System.out.println("------------------------------------");

        for (int i = 0; i < seats.length; i++) {

            for (int j = 0; j < seats[i].length; j++) {

                char rowName = (char) ('A' + i);

                String status = seats[i][j] == 0 ? "A" : "B";

                System.out.print(rowName + "" + (j + 1)
                        + "[" + status + "]  ");
            }

            if (i <= 2) {
                System.out.print("REGULAR");
            }
            else if (i == 3) {
                System.out.print("PREMIUM");
            }
            else {
                System.out.print("VIP");
            }

            System.out.println();
        }

        System.out.println("------------------------------------");
        System.out.println("A = Available | B = Booked");
        System.out.println("Regular : Rows A-C");
        System.out.println("Premium : Row D");
        System.out.println("VIP     : Row E");
        System.out.println("====================================");
    }

    // Display Category-wise Seats
    void displayCategorySeats(String category) {

        int startRow;
        int endRow;

        if (category.equalsIgnoreCase("REGULAR")) {
            startRow = 0;
            endRow = 2;
        }
        else if (category.equalsIgnoreCase("PREMIUM")) {
            startRow = 3;
            endRow = 3;
        }
        else if (category.equalsIgnoreCase("VIP")) {
            startRow = 4;
            endRow = 4;
        }
        else {
            System.out.println("Invalid Seat Category!");
            return;
        }

        System.out.println("\n========== " + category + " SEATS ==========");

        for (int i = startRow; i <= endRow; i++) {

            for (int j = 0; j < seats[i].length; j++) {

                char rowName = (char) ('A' + i);

                String status = seats[i][j] == 0 ? "Available" : "Booked";

                System.out.println(
                        rowName + "" + (j + 1) + " : " + status
                                + " | Price: Rs. " + getFinalSeatPrice(i)
                );
            }
        }
    }

    // Book Seat
    void bookSeat(int row, int col) {

        if (row < 0 || row >= 5 || col < 0 || col >= 5) {

            System.out.println("Invalid Seat Position!");

        }
        else if (seats[row][col] == 1) {

            System.out.println("Seat Already Booked!");

        }
        else {

            seats[row][col] = 1;

            System.out.println("Seat Booked Successfully!");
        }
    }

    // Cancel Seat
    void cancelSeat(int row, int col) {

        if (row < 0 || row >= 5 || col < 0 || col >= 5) {

            System.out.println("Invalid Seat Position!");

        }
        else if (seats[row][col] == 0) {

            System.out.println("Seat is Already Available!");

        }
        else {

            seats[row][col] = 0;

            System.out.println("Seat Cancelled Successfully!");
        }
    }

}

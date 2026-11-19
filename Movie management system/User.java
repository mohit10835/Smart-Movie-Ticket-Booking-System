import java.util.ArrayList;

public class User {

    String userId;
    String userName;
    String email;
    String password;

    double walletBalance;

    int totalCancellations;

    // Wallet Transaction History
    ArrayList<String> transactions;

    // Constructor
    User(String userId, String userName,
         String email, String password) {

        this.userId = userId;
        this.userName = userName;
        this.email = email;
        this.password = password;

        this.walletBalance = 0;
        this.totalCancellations = 0;

        this.transactions = new ArrayList<>();
    }
    // User Login Authentication
    boolean verifyPassword(String enteredPassword) {

        return password.equals(enteredPassword);
    }

    // Add Money to Wallet
    void addMoney(double amount) {

        if (amount > 0) {

            walletBalance += amount;

            transactions.add("Money Added: Rs. " + amount);

            System.out.println("Money Added Successfully!");

        } else {

            System.out.println("Invalid Amount!");
        }
    }

    // Credit Refund
    void creditRefund(double amount) {

        if (amount > 0) {

            walletBalance += amount;

            transactions.add("Refund Credited: Rs. " + amount);

            System.out.println("Refund Credited Successfully!");
        }
    }

    // Deduct Money from Wallet
    boolean deductMoney(double amount) {

        if (amount > 0 && walletBalance >= amount) {

            walletBalance -= amount;

            transactions.add("Ticket Payment: Rs. " + amount);

            System.out.println("Wallet Payment Successful!");

            return true;
        }

        System.out.println("Insufficient Wallet Balance!");

        return false;
    }

    // Display Wallet Balance
    void displayWallet() {

        System.out.println("\n========== MY WALLET ==========");

        System.out.println("User ID       : " + userId);
        System.out.println("Wallet Balance: Rs. " + walletBalance);

        System.out.println("===============================");
    }

    // Display Transaction History
    void displayTransactions() {

        System.out.println("\n====== TRANSACTION HISTORY ======");

        if (transactions.isEmpty()) {

            System.out.println("No Transactions Found!");

        } else {

            for (String transaction : transactions) {

                System.out.println(transaction);
            }
        }

        System.out.println("================================");
    }

    // Display User Details
    void displayUser() {

        System.out.println("\n========== USER DETAILS ==========");

        System.out.println("User ID       : " + userId);
        System.out.println("User Name     : " + userName);
        System.out.println("Email         : " + email);
        System.out.println("Wallet Balance: Rs. " + walletBalance);

        System.out.println("==================================");
    }
}
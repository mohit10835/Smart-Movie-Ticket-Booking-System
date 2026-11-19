public class Movie {

    int movieId;
    String movieName;
    String genre;
    double rating;
    double ticketPrice;

    // Constructor
    Movie(int movieId, String movieName, String genre,
          double rating, double ticketPrice) {

        this.movieId = movieId;
        this.movieName = movieName;
        this.genre = genre;
        this.rating = rating;
        this.ticketPrice = ticketPrice;
    }

    // Display Movie Details
    void displayMovie() {

        System.out.println("Movie ID: " + movieId);
        System.out.println("Movie Name: " + movieName);
        System.out.println("Genre: " + genre);
        System.out.println("Rating: " + rating);
        System.out.println("Ticket Price: " + ticketPrice);
    }
}
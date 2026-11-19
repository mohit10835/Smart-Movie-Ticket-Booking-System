import java.time.LocalDate;
import java.time.LocalTime;

public class Show {

    int showId;
    int movieId;
    String movieName;

    LocalDate showDate;
    LocalTime showTime;

    String theatreName;

    // Each show gets its own Theatre
    Theatre theatre;

    Show(int showId, int movieId, String movieName,
         LocalDate showDate, LocalTime showTime,
         String theatreName) {

        this.showId = showId;
        this.movieId = movieId;
        this.movieName = movieName;
        this.showDate = showDate;
        this.showTime = showTime;
        this.theatreName = theatreName;

        // Create separate seat layout for this show
        this.theatre = new Theatre();
    }

    void displayShow() {

        System.out.println("----------------------------------");
        System.out.println("Show ID     : " + showId);
        System.out.println("Movie       : " + movieName);
        System.out.println("Date        : " + showDate);
        System.out.println("Time        : " + showTime);
        System.out.println("Theatre     : " + theatreName);
        System.out.println("----------------------------------");
    }
}
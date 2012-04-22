package cinemabooking;

import java.util.Iterator;


/**
 *
 * @author charlie_r_mills
 */
public class CinemaBooking {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        Film film = new Film();
        film.length = 234;
        film.rating = "pg";
        film.title = "tidfs dsfsdf ";
        film.addNew();
    }
}


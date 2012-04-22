package cinemabooking;


/**
 *
 * @author charlie_r_mills
 */
public class CinemaBooking {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        Screen screen = new Screen(1);
        
        System.out.print(screen.rows);
        screen.rows = 3234;
        screen.updateChanges();
    }
}


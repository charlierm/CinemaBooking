package cinemabooking;

import cinemabooking.gui.MainWindow;
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
        System.setProperty("apple.laf.useScreenMenuBar", "true");
        System.setProperty("com.apple.mrj.application.apple.menu.about.name", "Cinema Booking");
        new MainWindow();
        
    }
}


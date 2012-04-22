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
        Screen screen = new Screen(1);
                
        ScreenCollection screens = new ScreenCollection();
        screens.fetchAll();
        
        FilmCollection films = new FilmCollection();
        films.fetchAll();
        
        Iterator itr = films.iterator();
        
        while(itr.hasNext()){
            Film film = (Film) itr.next();
            System.out.println(film.title);
        }
        
        Showing showing = new Showing().getShowingFromId(1);
        
        System.out.print(showing.dateTime);
        
        Booking booking = new Booking();
        booking.setShowing(showing);
        booking.addNew();
    }
}


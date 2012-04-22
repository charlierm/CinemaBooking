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
                
        ScreenCollection films = new ScreenCollection();
        films.fetchAll();
        
        Iterator itr = films.iterator();
        
        while(itr.hasNext()){
            Screen film = (Screen) itr.next();
            System.out.println("id" + film.id);
            System.out.println("rows" + film.rows);
            System.out.println("columns" + film.columns);
    }
    }
}


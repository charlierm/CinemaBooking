/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cinemabooking;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author charlie_r_mills
 */
public class ScreenCollection implements Iterable{
    /**ArrayList of Screen Objects*/
    private ArrayList screens;
    
    /**Database instance used to connect*/
    private Database database;
    
    /**
     * Creates a collection of screens
     */
    public ScreenCollection(){
        screens = new ArrayList();
    }
    /**
     * Fetches all Screen objects from the database
     */
    public void fetchAll(){
        String sql = "SELECT * FROM screens";
         try{
            database = new Database();
            ResultSet rs = database.runQuery(sql);
            while(rs.next()){
              System.out.println(rs.getInt("rows"));
              screens.add(new Screen(rs.getInt("id")));
            }
        }
        catch(Exception e){
            System.err.print(e);
        }   
    }
    
    /**
     * Returns the screen object at the specified index.
     * @param index integer of index
     * @return Film returns a film object
     */
    public Screen returnFilm(int index){
        return (Screen) screens.get(index);
    }
    
    /**
     * Adds a screen object to the collection
     * @param screen 
     */
    public void add(Screen screen){
        screens.add(screen);
    }
    
    /**
     * Removes the Screen object at the specified index
     * @param index 
     */
    public void remove(int index){
        screens.remove(index);
    }
    
    /**
     * Returns an iterator to loop through screens in the collection
     * @return Iterator
     */
    @Override
    public Iterator iterator() {
        return screens.iterator();
    }
    
    /**
     * Returns the length of the collection
     * @return 
     */
    
    
}

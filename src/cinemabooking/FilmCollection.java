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
public class FilmCollection implements Iterable{
    
    /**ArrayList of Film Objects*/
    private ArrayList films;
    
    /**Database instance used to connect*/
    private Database database;
    
    /**
     * Creates a collection of films
     */
    FilmCollection(){
        films = new ArrayList();
    }
    /**
     * Fetches all film objects from the database
     */
    public void fetchAll(){
        String sql = "SELECT * FROM films";
         try{
            database = new Database();
            ResultSet rs = database.runQuery(sql);
            while(rs.next()){
              Film film = new Film();
              film.id = rs.getInt("id");
              film.length = rs.getInt("length");
              film.rating = rs.getString("rating");
              film.title = rs.getString("title");
              films.add(film);
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
        return (Screen) films.get(index);
    }
    
    /**
     * Adds a screen object to the collection
     * @param screen 
     */
    public void add(Screen screen){
        films.add(screen);
    }
    
    /**
     * Removes the Screen object at the specified index
     * @param index 
     */
    public void remove(int index){
        films.remove(index);
    }
    
    /**
     * Returns an iterator to loop through screens in the collection
     * @return Iterator
     */
    @Override
    public Iterator iterator() {
        return films.iterator();
    }
}

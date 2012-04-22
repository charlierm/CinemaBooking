package cinemabooking;

import java.sql.ResultSet;

/**
 * This class contains methods for managing film showings.
 * @author charlie_r_mills
 */
public class Showing {
    /**Showing id*/
    public int id;
    /**Film object of the relevant film*/
    public Film film;
    /**Screen Object*/
    public Screen screen;
    /**Date and time of viewing in Unix timestamp*/
    public int dateTime;
    /**Database instance the class uses*/
    private Database database;
    
    Showing(){
        try{
            database = new Database();
        }
        catch(Exception e){
            System.err.print(e);
        }
    }
    
    public void setFilm(Film film){
        this.film = film;
    }
    
    public void setScreen(Screen screen){
        this.screen = screen;
    }
    
    public Showing getShowingFromId(int id){
        ResultSet rs;
        String sql = "SELECT * FROM showing WHERE id=" + "'" + id + "'";
        try{
            rs = database.runQuery(sql);
            while (rs.next()){
                this.id     = id;
                film        = new Film().fromId(rs.getInt("film"));
                screen      = new Screen(rs.getInt("screen"));
                dateTime    = rs.getInt("date_time");
            }
        }
        catch(Exception e){
            System.err.print("Couldnt Fetch showings");
        }
        return this;
    }
}

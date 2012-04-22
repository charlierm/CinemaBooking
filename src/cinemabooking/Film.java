package cinemabooking;

import cinemabooking.config.*;
import java.sql.ResultSet;

/**
 * Responsible for handling film objects fetched from the Database Class
 * @author charlie_r_mills
 */
public class Film {
    /**Database Id of Film*/
    public int id;
    /**Title of Film*/
    public String title;
    /**Film rating*/
    public String rating;
    /**Length of film in minutes*/
    public int length;
    //Database Instance*/
    private Database database;
    
    
    Film(){
        try{
            database = new Database();
        }
        catch(Exception e){
            System.err.print(e);
        }
    }
    
    public void fromTitle(String filmTitle){
        ResultSet rs;
        String sql = "SELECT * FROM " 
                + new Config().getDatabaseConfig().databaseName 
                + ".films WHERE title=" + "'" + filmTitle + "'";
        try{
            rs = database.runQuery(sql);
            while (rs.next()){
                id      = rs.getInt("id");
                title   = rs.getString("title");
                rating  = rs.getString("rating");
                length  = rs.getInt("length");
            }
        }
        catch(Exception e){
            System.err.print("Couldnt Fetch Films");
        }   
    }
    
    /**
     * Updates any changes made to the Class properties in the Database
     */
    public void updateChanges(){
        String sql = 
                "UPDATE films SET "
                + "title=" + "'" + this.title + "'"
                + ", rating=" + "'" + this.rating + "'"
                + ", length=" + "'" + this.length + "'"
                + " WHERE id=" + this.id;
        try{
            database.runUpdate(sql);
        }
        catch(Exception e){
            System.err.print("Couldnt run film update: " + e);
        }
    }
}

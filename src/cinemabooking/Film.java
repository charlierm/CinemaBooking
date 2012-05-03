package cinemabooking;

import cinemabooking.config.*;
import java.net.URLEncoder;
import java.sql.ResultSet;
import java.util.ArrayList;
import us.monoid.web.Resty;

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
    /**Movie image path*/
    public String imagePath;
    
    
    
    public Film(){
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
     * Fetches film object from database using id
     * @param id id of film
     */
    public Film fromId(int id){
        ResultSet rs;
        String sql = "SELECT * FROM " 
                + new Config().getDatabaseConfig().databaseName 
                + ".films WHERE id=" + "'" + id + "'";
        try{
            rs = database.runQuery(sql);
            while (rs.next()){
                this.id      = id;
                title   = rs.getString("title");
                rating  = rs.getString("rating");
                length  = rs.getInt("length");
            }
        }
        catch(Exception e){
            System.err.print("Couldnt Fetch Films");
        }
        return this;
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
    
    public void addNew(){
        String sql = 
                "INSERT INTO films(title, rating, length) "
                + "VALUES("
                + "'" + this.title + "'" + ", "
                + "'" + this.rating + "'" + ", "
                + "'" + new Utilities().timeStamp() +"'"+ ")";
        
        try{
            database.runUpdate(sql);
        }
        catch(Exception e){
            System.err.print("Couldnt run screen update: " + e);
        }
    }
    
    public ArrayList getFromFlixster(){
        Resty r = new Resty();
        ArrayList<String> filmInfo = new ArrayList<String>();
        try {
            String encodedTitle = URLEncoder.encode(title, "UTF-8");
            String url = "http://api.rottentomatoes.com/api/public/v1.0/movies.json?q="
                    + encodedTitle
                    + "&page_limit=1&page=1&apikey=arh4c7r5tyfxf5u5d53v7tqk";
            filmInfo.add(r.json(url).get("movies[0].synopsis").toString());
            filmInfo.add(r.json(url).get("movies[0].posters.detailed").toString());
        } catch (Exception e) {
            System.err.print(e);
        }
        return filmInfo;
    }
}

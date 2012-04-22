package cinemabooking;

import java.sql.ResultSet;

/**
 * Screen Class - This holds all the relevant methods regarding a screen in the 
 * cinema. A cinema can have many screens.
 * @author Charlie Mills
 */
public class Screen {
    /**The database id of the screen*/
    public int id;
    /**Number of rows of seats*/
    public int rows;
    /**Number of columns of seats */
    public int columns;
    /**Database instance the class is using*/
    private Database database;
    
    /**
     * Initializes class with data fetched from screen id
     * @param id id of the screen
     */
    Screen(int id){
        String sql = "SELECT * FROM screens WHERE id='" + id + "'";
         try{
            database = new Database();
            ResultSet rs = database.runQuery(sql);
            while(rs.next()){
                this.id = id;
                this.rows = rs.getInt("rows");
                this.columns = rs.getInt("columns");
            }
        }
        catch(Exception e){
            System.err.print(e);
        }
    }
    
    public void updateChanges(){
        String sql = 
                "UPDATE screens SET "
                + "rows=" + "'" + this.rows + "'"
                + ", columns=" + "'" + this.columns + "'"
                + " WHERE id=" + this.id;
        try{
            database.runUpdate(sql);
        }
        catch(Exception e){
            System.err.print("Couldnt run screen update: " + e);
        }
    }
}

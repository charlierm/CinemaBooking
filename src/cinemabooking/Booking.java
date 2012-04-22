package cinemabooking;

/**
 * Contains booking information
 * @author charlie_r_mills
 */
public class Booking {
    /**Database instance the class is using*/
    private Database database;
    /**Showing object the booking is for*/
    private Showing showing;
    /**Customer Reference number*/
    public String reference;
    
    /**
     * Initilizes database object
     */
    Booking(){
        try{
            database = new Database();
        }
        catch(Exception e){
            System.err.print(e);
        }
    }
    
    /**
     * Sets the showing property of the class
     * @param showing 
     */
    public void setShowing(Showing showing){
        this.showing = showing;
    }
    
    /**
     * returns the associated film
     * @return Film
     */
    public Film getFilm(){
        return showing.film;
    }
    
    /**
     * Returns the associated screen with the booking
     * @return Screen
     */
    public Screen getScreen(){
        return showing.screen;
    }
    
    /**
     * Updates any changes made the the booking
     */
    public void updateChanges(){
        String sql = 
                "UPDATE showing SET "
                + "showing_id=" + "'" + showing.id + "'"
                + ", seat_number=" + "'" + "" + "'"
                + " WHERE reference=" + this.reference;
        try{
            database.runUpdate(sql);
        }
        catch(Exception e){
            System.err.print("Couldnt run film update: " + e);
        }
    }
    
    /**
     * If the booking is new this method must be used, inserts the record into
     * the db.
     */
    public void addNew(){
        this.reference = new Utilities().randomString() + ")";
        String sql = 
                "INSERT INTO bookings(showing_id, seat_number, reference) "
                + "VALUES("
                + "'" + showing.id + "'" + ", "
                +"'"+1 +"'"+ ", "
                +"'"+ reference +"'"+ ")";
        
        try{
            database.runUpdate(sql);
        }
        catch(Exception e){
            System.err.print("Couldnt run screen update: " + e);
        }
    }
    
}

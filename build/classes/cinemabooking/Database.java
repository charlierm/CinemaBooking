package cinemabooking;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Properties;
import java.util.prefs.Preferences;

/**
 * Responsible for handling database connections (Should load settings)
 *
 * @author Charlie Mills
 */
public class Database {
    private String databaseType;
    private String url;
    private String databaseName;
    private String user;
    private String password;
    
    /**
     * Initializes class variables with settings saved in preferences API.
     */
    Database(){
        Preferences pref = Preferences.userRoot().node(this.getClass().getName());
        this.databaseType   = pref.get("databaseType", "local");
        this.url            = pref.get("url", null);
        this.databaseName   = pref.get("databaseName", null);
        this.user           = pref.get("user", "user");
        this.password       = pref.get("password", "password");
    }
    /**
     * Connects to the database using the saved settings from the Java preferences API,
     * if no preferences are found then the connection is reverted to a local db.
     * @return Connection 
     * @throws Exception 
     */
    public Connection connect() throws Exception {
        if (this.databaseType.equals("mssql")){
            try{
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                Connection con = DriverManager.getConnection("jdbc:sqlserver://" + this.url 
                        + ";databaseName=" + this.databaseName
                        + ";user=" + this.user
                        + ";password=" + this.password
                        + "dataEncryption=true");
            }
            catch(Exception e){
                throw e;
            }
        }
        else if (this.databaseType.toLowerCase().equals("mysql")){
            try{
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://" + this.url + "/" + this.databaseName,
                        this.user, this.password);
                return con;
            }
            catch(Exception e){
                throw e;
            }
        }
        else if (this.databaseType.equals("local")){
            try{
                String location = System.getProperty("user.home") 
                        + System.getProperty("file.separator")
                        + ".CinemaBooking";
                
                Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
                Connection con = DriverManager.getConnection("jdbc:derby:"
                        + location + ";create=true"
                        + ";user=" + this.user
                        + ";password=" + this.password);
                if(System.getProperty("os.name").toLowerCase().contains("windows")){
                    Runtime.getRuntime().exec("attrib +H " + location);
                           
                }
                
                return con;
                }
            catch(Exception e){
                throw e;
            }
        }
        else{
            throw new Exception("Not a valid Database Type: " + this.databaseType);
        }
    return null;
    }
    
    /**
     * Sets up the database for the Booking System to use.
     * @throws Exception 
     */
    public void install() throws Exception{
        Connection con;
        Statement statement;
        try{
            con = this.connect();
            statement = con.createStatement();
        }
        catch(Exception e){
            System.err.println("Database Exception " + e);
            throw e;
        }
        //Load installation XML from Jar
        Properties properties = new Utilities().getPropertiesFromXml("install.xml");
        statement.executeUpdate(properties.getProperty("installSql"));
    }
    
    public void resetDefaults(){
        Preferences pref = Preferences.userRoot().node(this.getClass().getName());
        password = new BigInteger(130, new SecureRandom()).toString(32); 
        pref.put("databaseType", "local");
        pref.put("url", "DB Stored Locally");
        pref.put("user", "user");
        pref.put("password", password);
    }
    
    /**
     * Used for Debugging - Prints preferences saved through pref api to the console.
     */
    public void getSetPref(){
        Preferences pref = Preferences.userRoot().node(this.getClass().getName());
        try{
            String[] keys = pref.keys();
            for(int i = 0; i < keys.length; i++){
                System.out.println(keys[i]);
                System.out.println(pref.get(keys[i], ""));
            }
        }
        catch(Exception e){
            
        }
        
    }
}

/**
 * Film class handles film items
 * 
 * @author charlie_r_mills
 */
class Film extends Database{
    /** Title/Name of film */
    private String title;
    /** Rating of film, e.g "PG" */
    private String rating;
    private String length;
    private Integer id;
            
    Film(){
    }
    
    public void getFromId(Integer filmID){
        
    }
}

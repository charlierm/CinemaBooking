package cinemabooking;

import cinemabooking.config.Config;
import cinemabooking.config.DatabaseConfig;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Responsible for handling database connections (Should load settings)
 *
 * @author Charlie Mills
 */
public class Database {
    /** The type of Database: mysql/mssql/local */
    private DatabaseConfig config;
    
    /**Full connection url*/
    private String connectionUrl;
    
    /**Derby local db folder path*/
    private String location;
    
    /**Database Connection*/
    private Connection connection;
    
    /**
     * Initializes class variables with settings saved in Config Class
     */
    Database() throws Exception{
        //Get db settings from config class
        config = new Config().getDatabaseConfig();
        this.connect();
    }
    /**
     * Connects to the database using the saved settings from Config Class
     * if no preferences are found then the connection is reverted to a local db.
     * @return Connection 
     * @throws Exception 
     */
    private void connect() throws Exception {
        //connect to MSSQL if saved in preferences
            if (this.config.databaseType.equals("mssql")){
            try{
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                this.connection = DriverManager.getConnection("jdbc:sqlserver://" + this.config.url 
                        + ";databaseName=" + this.config.databaseName
                        + ";user=" + this.config.user
                        + ";password=" + this.config.password
                        + "dataEncryption=true");
            }
            catch(Exception e){
                throw e;
            }
        }
        else if (this.config.databaseType.toLowerCase().equals("mysql")){
            //Connect to MySql if saved in preferences
            try{
                Class.forName("com.mysql.jdbc.Driver");
                this.connection = DriverManager.getConnection("jdbc:mysql://" + 
                        this.config.url + "/" + this.config.databaseName,
                        this.config.user, this.config.password);
            }
            catch(Exception e){
                throw e;
            }
        }
        else if (this.config.databaseType.equals("local")){
            //Use derby if selected or no settings saved.
            try{
                this.location = System.getProperty("user.home") 
                        + System.getProperty("file.separator")
                        + ".CinemaBooking";
                
                Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
                Connection con = DriverManager.getConnection("jdbc:derby:"
                        + this.location + ";create=true"
                        + ";user=" + this.config.user
                        + ";password=" + this.config.password);
                //If using windows hide the derby db folder
                if(System.getProperty("os.name").toLowerCase().contains("windows")){
                    Runtime.getRuntime().exec("attrib +H " + this.location);         
                }
                }
            catch(Exception e){
                throw e;
            }
        }
        else{
            throw new Exception("Not a valid Database Type: " + this.config.databaseType);
        }
    }
    
    /**
     * Runs a query using the instance Connection object, must be instanced first
     * @param sql SQL statement to return
     * @return void
     */
    public ResultSet runQuery(String sql) throws Exception{
       
        Statement statement;
        ResultSet result = null;
        
        //Check class has been initialized
        if (this.connection == null){
            throw new Exception("Database Class must be initialized before use");
        }
        try{
            statement = this.connection.createStatement();
            result = statement.executeQuery(sql);
        }
        catch (java.sql.SQLException e){
            System.out.print("Issue running sql" + e);
        }
        return result;
    }
    
    /**
     * Runs an update to the database, returns no results.
     * @param sql SQL update string
     * @throws Exception 
     */
    public void runUpdate(String sql) throws Exception{
        Statement statement;
        ResultSet result;
        
        if (this.connection == null){
            throw new Exception("Database Class must be initialized before use");
        }
        try{
            statement = this.connection.createStatement();
            statement.executeUpdate(sql);
        }
        catch (java.sql.SQLException e){
            System.out.print("Issue running update sql" + e);
        }
        
        
    }
}

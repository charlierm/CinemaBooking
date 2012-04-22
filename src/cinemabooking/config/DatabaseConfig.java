package cinemabooking.config;

import java.util.prefs.Preferences;

/**
 * This class is responsible for handling the Database configuration.
 * @author Charlie Mills
 */
public class DatabaseConfig {
    /**Preferences instance for saving*/
    private Preferences preferences;
    
    /**Whether settings have been set*/
    public boolean configSet;
    
    /**Type of database, mysql, mssql and derby currently supported*/
    public String databaseType;
    
    /**URL of database, not required for derby (local db)*/
    public String url;
    
    /**Database username, not required for derby*/
    public String user;
    
    /**Database password, not required for derby*/
    public String password;
    
    /**Name of Database on db server*/
    public String databaseName;
    
    /**
     * Loads all database preferences.
     * @param preferences 
     */
    DatabaseConfig(Preferences preferences){
        this.preferences = preferences;
        this.load();
    }
    
    /**
     * Loads everything from Preferences API
     */
    private void load(){
        configSet       = preferences.getBoolean("configSet", false);
        databaseType    = preferences.get("databaseType", "local");
        url             = preferences.get("url", "");
        user            = preferences.get("user", "user");
        password        = preferences.get("password", "");
        databaseName    = preferences.get("databaseName", "CinemaBooking");
    }
    
    /**
     * Stores all changed preferences
     */
    public void store(){
        preferences.put("databaseType", databaseType);
        preferences.put("url", url);
        preferences.put("user", user);
        preferences.put("password", password);
        preferences.put("databaseName", databaseName);
        
        //If Database isnt local and url is set then set configSet to true
        if (!databaseType.toLowerCase().equals("local") && !"".equals(url)){
        preferences.putBoolean("configSet", true);
       
    }
    }
    
}

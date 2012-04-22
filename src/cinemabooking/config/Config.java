package cinemabooking.config;

import java.util.prefs.Preferences;

/**
 * This class encapsulates all application preferences
 * @author Charlie Mills
 */
public final class Config {
    /**Instance of Preferences API*/
    private Preferences preferences;
    
    /**Holds instance of database config*/
    private DatabaseConfig databaseConfig;
    
    /**
     * Create preferences instance and load config class, changing node name
     * here will affect all config classes.
     */
    public Config(){
        preferences = Preferences.userRoot().node("CinemaBooking");
        databaseConfig = new DatabaseConfig(preferences);
    }
    
    /**
     * Returns an instance of database settings.
     * @return DatabaseConfig
     */
    public DatabaseConfig getDatabaseConfig(){
        return databaseConfig;
    } 
    
    public void removePreferences(){
        try{
            preferences.clear();
        }
        catch(Exception e){
            System.err.print(e);
        }
    }
}
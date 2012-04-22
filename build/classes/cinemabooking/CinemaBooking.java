package cinemabooking;

import java.util.Properties;

/**
 *
 * @author charlie_r_mills
 */
public class CinemaBooking {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        Properties properties = new Utilities().getPropertiesFromXml("install.xml");
        System.out.print(properties.getProperty("installSql"));
        
        Database data = new Database();
        //data.resetDefaults();
        data.getSetPref();
        data.connect();
        //data.install();
        Film test = new Film();
        test.connect();
        
    }
}

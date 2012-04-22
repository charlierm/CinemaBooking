package cinemabooking;

import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author Charlie Mills
 */
public class Utilities {
    
    public Properties getPropertiesFromXml(String xmlFileName) {
        Properties properties = new Properties();
        try{
            InputStream is = this.getClass().getResourceAsStream("install.xml");
            properties.loadFromXML(is);
        }
        catch(Exception e){
            System.err.print("Couldnt Open XML" + e);
        }
        return properties;
    }
}

package cinemabooking;

import java.io.InputStream;
import java.util.Properties;
import java.security.SecureRandom;
import java.math.BigInteger;


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
    
    public String randomString(){
        SecureRandom random = new SecureRandom();
        return new BigInteger(130, random).toString(32);
    }
}

/**
 * 
 */
package edu.neu.ccs.nuir.tempsum;
import java.util.Properties;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author mattea
 *
 */
public class Config {
	private Properties prop;
	Config(String propFileName) throws IOException {
		prop = new Properties();
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
		
		if (inputStream != null) {
			prop.load(inputStream);
		} else {
			throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
		}
	}
	
	public String get(String key) {
		return prop.getProperty(key);
	}
}

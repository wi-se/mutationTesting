package mutation.testing.helper;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import mutation.testing.exceptions.ApplicationInternalError;

public class PropertyLoader {
	private static PropertyLoader instance;
	private Properties props;
	private static final String PROPS_FILE = "/application.properties";
	private static final Log LOG = LogFactory.getLog(PropertyLoader.class);

	private PropertyLoader() {
	}

	private void loadProps() {
		props = new Properties();
		InputStream is = ClassLoader.class.getResourceAsStream(PROPS_FILE);
		if (is == null) {
			throw new ApplicationInternalError("Unable to load propeties file " + PROPS_FILE);
		} else {
			try {
				props.load(is);
			} catch (IOException e) {
				LOG.error("Some error occurs during the properties loading ", e);
			}
		}
	}

	public String getProps(String key) {
		if (props == null) {
			loadProps();
		}
		return props.getProperty(key);
	}

	public static PropertyLoader getInstance() {
		if (instance == null) {
			instance = new PropertyLoader();
		}
		return instance;
	}
}

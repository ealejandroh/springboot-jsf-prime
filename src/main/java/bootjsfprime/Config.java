package bootjsfprime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

/**
 * Load environment specific variables from properties files.
 * If -Druntime.environment=environment is not set, then loading the default settings.
 */
@WebListener
public class Config implements ServletContextListener {
	private static final Logger LOGGER = LoggerFactory.getLogger(Config.class);

	private static final String DEFAULT_ENV = "localhost";
	private static final Set<String> params = new HashSet<>();

	static {
		params.add("environment");
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		// Environment properties
		String runtimeEnv = System.getProperty("runtime.environment");

		// Project properties
		Properties props = new Properties();
		if (runtimeEnv != null) {
			props = loadProperties(runtimeEnv, props);
		} else {
			LOGGER.info("{'method':'contextInitialized', 'info':'Unable to determine runtime environment, using default environment {}'}", DEFAULT_ENV);
			props = loadProperties(DEFAULT_ENV, props);
		}

		for (String param : params) {
			if (props.containsKey(param)) {
				setInitParam(sce, param, props.getProperty(param));
			} else {
				throw new RuntimeException("Missing runtime property! Missing key=" + param);
			}
		}
	}

	private Properties loadProperties(String env, Properties props) {
		String propFile = "runtime-properties/" + env + ".properties";
		try {
			InputStream in = Config.class.getClassLoader().getResourceAsStream(propFile);
			if (in != null) {
				props.load(in);
			} else {
				throw new RuntimeException("Properties file '" + propFile + "' is null!");
			}
		} catch (Exception e) {
			LOGGER.error("{'method':'loadProperties', 'error':{'classloader':'{}'}}", Config.class.getClassLoader().toString(), e);
			throw new RuntimeException("Properties file '" + propFile + "' not found!");
		}
		return props;
	}

	private void setInitParam(ServletContextEvent sce, String key, String value) {
		LOGGER.debug("{'method':'setInitParam', 'key':'{}', 'value'='{}'}", key, value);
		sce.getServletContext().setInitParameter(key, value);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	}

}

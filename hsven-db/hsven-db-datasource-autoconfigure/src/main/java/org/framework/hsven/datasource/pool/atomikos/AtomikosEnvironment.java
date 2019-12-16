package org.framework.hsven.datasource.pool.atomikos;

import java.io.File;

/**
 * @author sven
 * @date 2019/12/2 11:38
 */
public class AtomikosEnvironment {

	public void enviroment() {
		String logdirPath = System.getProperty("log.home");
		if (logdirPath == null || logdirPath.length() == 0) {
			String appHome = System.getProperty("APP_HOME");
			if (appHome != null) {
				logdirPath = appHome + "/log";
			} else {
				logdirPath = "./log";
			}
		}

		File logDir = new File(logdirPath);
		String targetPath = logDir.getAbsolutePath();
		System.setProperty("com.atomikos.icatch.log_base_name", "atomikos");
		System.setProperty("com.atomikos.icatch.log_base_dir", targetPath + "/atomikos");
	}
}

package org.framework.hsven.mybatis.config;

import org.apache.commons.lang3.StringUtils;

/**
 * @author sven
 * @date 2019/12/2 9:57
 */
public class MybatisConfig {
	private MybatisConfig.Factor factor = new MybatisConfig.Factor();

	private MybatisConfig.Scanner scanner = new MybatisConfig.Scanner();

	public MybatisConfig.Factor getFactor() {
		return factor;
	}

	public MybatisConfig.Scanner getScanner() {
		return scanner;
	}

	public static class Factor {

		private String configLocation;

		private String mapperLocations;

		public String getConfigLocation() {
			return configLocation;
		}

		public void setConfigLocation(String configLocation) {
			this.configLocation = configLocation;
		}

		public String getMapperLocations() {
			return mapperLocations;
		}

		public void setMapperLocations(String mapperLocations) {
			this.mapperLocations = mapperLocations;
		}

		public boolean isValid() {
			return StringUtils.isNoneBlank(this.configLocation) && StringUtils.isNoneBlank(this.mapperLocations);
		}
	}

	public static class Scanner {

		private String basePackage;

		public String getBasePackage() {
			return basePackage;
		}

		public void setBasePackage(String basePackage) {
			this.basePackage = basePackage;
		}

		public boolean isValid() {
			return StringUtils.isNoneBlank(this.basePackage);
		}
	}
}

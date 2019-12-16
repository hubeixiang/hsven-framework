package org.framework.hsven.logging.events;

import org.framework.hsven.logging.AccessLoggerInfo;

public class AccessLoggerBeforeEvent {

	private AccessLoggerInfo logger;

	public AccessLoggerBeforeEvent(AccessLoggerInfo logger) {
		this.logger = logger;
	}

	public AccessLoggerInfo getLogger() {
		return logger;
	}

	public void setLogger(AccessLoggerInfo logger) {
		this.logger = logger;
	}
}

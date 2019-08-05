package org.mirrentools.gateway.enums;

/**
 * 时间单位类
 * 
 * @author <a href="http://szmirren.com">Mirren</a>
 *
 */
public enum TimeUnit {
	DAYS(86400), HOURS(3600), MINUTES(60);
	private long val;

	private TimeUnit(long val) {
		this.val = val;
	}

	public long getVal() {
		return val;
	}

}

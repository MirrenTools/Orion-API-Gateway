package org.mirrentools.gateway.http.enums;

/**
 * 游行处理器的目标
 * 
 * @author <a href="https://mirrentools.org/">Mirren</a>
 * @version 1.0.0
 */
public enum WanderDestination {
	/** 在黑名单处理器之前执行 */
	BLACKLIST(1),
	/** 在访问限制之前 */
	ACCESS_LIMIT(2),
	/** 在参数处理之前 */
	PARAMETER(3),
	/** 在权限认证之前 */
	AUTHENTICATION(4),
	/** 在前置处理器之前 */
	BEFORE(5),
	/** 在缓存处理器之前 */
	CACHE(6),
	/** 在中心处理器之前 */
	MAIN(7),
	/** 在后置处理器之前 */
	AFTER(8),
	/** 在后置处理器之后 */
	AFTER_END(9);

	private int val;

	WanderDestination(int val) {
		this.val = val;
	}

	public int val() {
		return val;
	}

}

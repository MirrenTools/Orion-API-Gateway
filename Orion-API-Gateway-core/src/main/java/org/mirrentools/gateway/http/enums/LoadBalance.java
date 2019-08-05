package org.mirrentools.gateway.http.enums;

/**
 * 负载均衡类型
 * 
 * @author <a href="http://szmirren.com">Mirren</a>
 *
 */
public enum LoadBalance {
	/**轮训分配请求地址*/
	POLLING,
	/**根据IP固定分配请求地址*/
	FIXED,
}

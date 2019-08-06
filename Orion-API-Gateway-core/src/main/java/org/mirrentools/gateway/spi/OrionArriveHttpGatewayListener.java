package org.mirrentools.gateway.spi;

import io.vertx.ext.web.RoutingContext;

/**
 * 当用户请求到达http网关时调用该监听器
 * 
 * @author <a href="https://mirrentools.org/">Mirren</a>
 * @version 1.0.0
 */
public interface OrionArriveHttpGatewayListener {
	/**
	 * 处理器
	 * 
	 * @param verticleId verticle示例的id
	 * @param rct        用户请求的上下文
	 */
	void handle(String verticleId, RoutingContext rct);
}

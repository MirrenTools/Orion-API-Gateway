package org.mirrentools.gateway.spi.listener;

import io.vertx.ext.web.RoutingContext;

/**
 * 当进入Route的failureHandler时调用该监听器,可用于做记录等<br>
 * handle参数RoutingContext.response已经被响应
 * 
 * @author <a href="http://mirrentools.org">Mirren</a>
 *
 */
@FunctionalInterface
public interface OrionArrivedGatewayListener {
	void handle(RoutingContext rct);
}

package org.mirrentools.gateway.http.spi.listener;

import io.vertx.ext.web.RoutingContext;

/**
 * 当到达OrionHttpApi.handle时调用该监听器
 * 
 * @author <a href="http://mirrentools.org">Mirren</a>
 *
 */
@FunctionalInterface
public interface OrionHandlerListener {
	void handle(RoutingContext rct);
}

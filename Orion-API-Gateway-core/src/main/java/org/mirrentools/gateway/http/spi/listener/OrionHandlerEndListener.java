package org.mirrentools.gateway.http.spi.listener;

import io.vertx.ext.web.RoutingContext;

/**
 * 当OrionHttpApi.handle处理完毕时调用该监听器<br>
 * RoutingContext已经被响应
 * 
 * @author <a href="http://mirrentools.org">Mirren</a>
 *
 */
@FunctionalInterface
public interface OrionHandlerEndListener {
	void handle(RoutingContext rct);
}

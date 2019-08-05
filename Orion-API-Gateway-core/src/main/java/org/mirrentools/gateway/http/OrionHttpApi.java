package org.mirrentools.gateway.http;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;

/**
 * Http相关的API接口
 * 
 * @author <a href="http://mirrentools.org">Mirren</a>
 *
 */
public interface OrionHttpApi extends Handler<RoutingContext> {
	/**
	 * 失败处理器
	 * 
	 * @param failureHandler
	 */
	void failureHandler(RoutingContext failureHandler);
}

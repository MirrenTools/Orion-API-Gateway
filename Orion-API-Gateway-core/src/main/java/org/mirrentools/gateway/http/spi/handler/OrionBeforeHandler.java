package org.mirrentools.gateway.http.spi.handler;

import org.mirrentools.gateway.http.OrionParameter;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;

/**
 * 前置处理器
 * 
 * @author <a href="https://mirrentools.org/">Mirren</a>
 * @version 创建时间：2019年8月2日 下午12:40:19
 *
 */
@FunctionalInterface
public interface OrionBeforeHandler {
	/**
	 * 前置处理器
	 * 
	 * @param rct
	 *          上下文
	 * @param params
	 *          用户请求的参数
	 * @param data
	 *          拓展参数
	 * @param handler
	 *          完成调用handler.complete(),<br>
	 *          异常调用handler.fail(异常)
	 */
	void handle(RoutingContext rct, OrionParameter params, Object data, Handler<AsyncResult<Object>> handler);
}

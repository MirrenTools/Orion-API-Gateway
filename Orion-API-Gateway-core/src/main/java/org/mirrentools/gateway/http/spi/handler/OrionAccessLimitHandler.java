package org.mirrentools.gateway.http.spi.handler;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;

/**
 * 访问限制处理器
 * 
 * @author <a href="https://mirrentools.org/">Mirren</a>
 * @version 创建时间：2019年8月2日 下午12:40:19
 *
 */
@FunctionalInterface
public interface OrionAccessLimitHandler {
	/**
	 * 访问限制检查
	 * 
	 * @param rct
	 *          请求的上下文
	 * @param data
	 *          拓展参数
	 * @param handler
	 *          检查通过调用handler.complete(),<br>
	 *          检查不通过调用handler.fail(new OrionProcessEndException),<br>
	 *          异常调用handler.fail(异常)
	 */
	void handle(RoutingContext rct, Object data, Handler<AsyncResult<Object>> handler);
}

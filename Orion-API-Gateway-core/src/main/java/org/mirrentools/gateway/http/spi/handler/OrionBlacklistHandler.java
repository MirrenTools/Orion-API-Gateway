package org.mirrentools.gateway.http.spi.handler;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;

/**
 * 黑名单处理器
 * 
 * @author <a href="https://mirrentools.org/">Mirren</a>
 * @version 创建时间：2019年8月2日 下午12:40:19
 *
 */
@FunctionalInterface
public interface OrionBlacklistHandler {
	/**
	 * 黑名单检查
	 * 
	 * @param rct
	 *          请求的上下文
	 * @param handler
	 *          检查通过调用handler.complete(),<br>
	 *          检查不通过调用handler.fail(new OrionProcessEndException),<br>
	 *          异常调用handler.fail(异常)
	 */
	void handle(RoutingContext rct, Handler<AsyncResult<Object>> handler);
}

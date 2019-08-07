package org.mirrentools.gateway.http.spi.handler;

import org.mirrentools.gateway.http.enums.WanderDestination;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;

/**
 * 游行处理器,该处理器可以定义它在任意处理器之前执行
 * 
 * @author <a href="https://mirrentools.org/">Mirren</a>
 * @version 创建时间：2019年8月2日 下午12:40:19
 *
 */
public interface OrionWanderHandler {
	/**
	 * 
	 * @param rct     请求的上下文
	 * @param data    拓展参数
	 * @param handler 检查通过调用handler.complete(),<br>
	 *                异常调用handler.fail(异常)
	 */
	void handle(RoutingContext rct, Object data, Handler<AsyncResult<Object>> handler);

	/**
	 * 在哪个处理器之前执行本处理器
	 * 
	 * @return
	 */
	WanderDestination getDestination();

}

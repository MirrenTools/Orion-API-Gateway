package org.mirrentools.gateway.http.spi.handler;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpClientResponse;
import io.vertx.ext.web.RoutingContext;

/**
 * 后置处理器
 * 
 * @author <a href="https://mirrentools.org/">Mirren</a>
 * @version 创建时间：2019年8月2日 下午12:40:19
 *
 */
@FunctionalInterface
public interface OrionAfterHandler {
	/**
	 * 后置处理器<br>
	 * 提示:如果有后置处理器,网关请求后端得到响应后会执行后置处理器,<br>
	 * 后置处理器可以直接调用上下文结束请求,也可以调用handler.complete(要响应给用户的数据)
	 * 
	 * @param rct          上下文
	 * @param cacheHandler 缓存处理器,用于做缓存的添加或删除
	 * @param response     官网请求后台得到响应的数据
	 * @param data         拓展参数
	 * @param handler
	 */
	void handle(RoutingContext rct, OrionCacheHandler cacheHandler, HttpClientResponse response, Object data,
			Handler<AsyncResult<Buffer>> handler);
}

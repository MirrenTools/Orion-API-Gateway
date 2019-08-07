package org.mirrentools.gateway.http.spi.handler;

import org.mirrentools.gateway.http.model.OrionParameter;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.ext.web.RoutingContext;

/**
 * 缓存处理器
 * 
 * @author <a href="https://mirrentools.org/">Mirren</a>
 * @version 创建时间：2019年8月2日 下午12:40:19
 *
 */
public interface OrionCacheHandler {
	/**
	 * 缓存处理器
	 * 
	 * @param rct     上下文
	 * @param params  用户请求的数据
	 * @param data    拓展参数
	 * @param handler 完成调用handler.complete(),<br>
	 *                异常调用handler.fail(异常)
	 */
	void handle(RoutingContext rct, OrionParameter params, Object data, Handler<AsyncResult<Object>> handler);

	/**
	 * 获取缓存数据
	 * 
	 * @param key     缓存的key
	 * @param data    拓展参数
	 * @param handler 结果处理器
	 */
	void get(String key, Object data, Handler<AsyncResult<Buffer>> handler);

	/**
	 * 添加缓存数据
	 * 
	 * @param key     缓存的key
	 * @param buffer  要缓存的数据
	 * @param data    拓展参数
	 * @param handler 处理结果
	 */
	void put(String key, Buffer buffer, Object data, Handler<AsyncResult<Object>> handler);

	/**
	 * 缓存缓存数据
	 * 
	 * @param key     缓存的key
	 * @param data    拓展参数
	 * @param handler 结果处理器
	 */
	void delete(String key, Object data, Handler<AsyncResult<Void>> handler);

}

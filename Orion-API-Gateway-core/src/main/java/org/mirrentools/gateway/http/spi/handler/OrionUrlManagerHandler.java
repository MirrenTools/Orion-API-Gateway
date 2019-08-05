package org.mirrentools.gateway.http.spi.handler;

import org.mirrentools.gateway.http.OrionUrlRecord;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;

/**
 * 请求后端URL管理器,该管理器负责生成URL与熔断等
 * 
 * @author <a href="http://mirrentools.org">Mirren</a>
 *
 */
public interface OrionUrlManagerHandler {
	/**
	 * 设置断容器
	 * 
	 * @param maxFailures
	 *          最大请求失败次数
	 * @param retryCount
	 *          最大重试次数
	 * @param retryInterval
	 *          重试连接间隔
	 * @param handler
	 */
	//void setCircuitBreaker(int maxFailures, int retryCount, long retryInterval, Handler<AsyncResult<Void>> handler);

	
	/**
	 * 获取轮训的url
	 * 
	 * @param handler
	 */
	void get(Handler<AsyncResult<OrionUrlRecord>> handler);

	/**
	 * 根据ip获取固定的url
	 * 
	 * @param ip
	 * @param handler
	 */
	void get(String ip, Handler<AsyncResult<OrionUrlRecord>> handler);

	/**
	 * 提交无效的连接
	 * 
	 * @param record
	 * @param handler
	 */
	void reportBadURL(OrionUrlRecord record, Handler<AsyncResult<Void>> handler);


	/**
	 * 注册服务
	 * 
	 * @param record
	 *          URL记录
	 * @param handler
	 *          注册成功放回该记录
	 */
	void register(OrionUrlRecord record, Handler<AsyncResult<OrionUrlRecord>> handler);

	/**
	 * 注销服务
	 * 
	 * @param id
	 *          服务的id
	 * @param handler
	 */
	void unregister(String id, Handler<AsyncResult<Void>> handler);

}

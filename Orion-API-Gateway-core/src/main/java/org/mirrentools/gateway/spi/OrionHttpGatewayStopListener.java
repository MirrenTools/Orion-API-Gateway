package org.mirrentools.gateway.spi;

import io.vertx.core.http.HttpServer;

/**
 * 网关启动监听器,对应Gateway中OrionHttpVerticle.stop执时调用
 * 
 * @author <a href="https://mirrentools.org/">Mirren</a>
 * @version 1.0.0
 */
public interface OrionHttpGatewayStopListener {
	/**
	 * 处理器
	 * 
	 * @param verticleId verticle示例的id
	 * @param httpServer http服务器
	 * @param httsServer https服务器
	 */
	void handle(String verticleId, HttpServer httpServer, HttpServer httsServer);
}

package org.mirrentools.gateway;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;

/**
 * 网关主控制器
 * 
 * @author <a href="https://mirrentools.org/">Mirren</a>
 * @version 1.0.0
 */
public class MainVerticle extends AbstractVerticle {
	/**
	 * 仅用于IDE中测试启动用
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		MainLauncher.start(MainVerticle.class);
	}

	@Override
	public void start(Promise<Void> startPromise) throws Exception {
		vertx.createHttpServer().requestHandler(req -> {
			req.response().putHeader("content-type", "text/plain").end("Hello from Vert.x!");
		}).listen(8888, http -> {
			if (http.succeeded()) {
				startPromise.complete();
				System.out.println("HTTP server started on port 8888");
			} else {
				startPromise.fail(http.cause());
			}
		});
	}
		
}

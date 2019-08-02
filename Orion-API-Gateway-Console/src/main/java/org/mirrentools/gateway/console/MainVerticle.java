package org.mirrentools.gateway.console;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;

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
				System.out.println("HTTP server started on port 5256");
			} else {
				startPromise.fail(http.cause());
			}
		});
	}

}

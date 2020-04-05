package org.mirrentools.gateway.console;

import org.mirrentools.gateway.console.common.Constant;
import org.mirrentools.gateway.console.common.RouterUtil;
import org.mirrentools.gateway.console.router.VerifyCodeRouter;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.CorsHandler;

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
		Router router = Router.router(vertx);
		router.route().handler(CorsHandler.create("*"));
		router.route().handler(BodyHandler.create());
		// 验证码处理器
		VerifyCodeRouter.startService(vertx, router);
		

		RouterUtil routerUtil = new RouterUtil();
		// 统一失败处理
		router.route("/*").failureHandler(routerUtil::failureHandler);
		router.errorHandler(404, routerUtil::the404Handler);
		router.errorHandler(405, routerUtil::the404Handler);
		router.errorHandler(500, routerUtil::failureHandler);
		// 服务的端口号
		Integer port = config().getInteger(Constant.CONFIG_HTTP_PORT, 5256);
		vertx.createHttpServer().requestHandler(router).listen(port, http -> {
			if (http.succeeded()) {
				startPromise.complete();
				System.out.println("HTTP server started on port " + port);
			} else {
				startPromise.fail(http.cause());
			}
		});
	}

}

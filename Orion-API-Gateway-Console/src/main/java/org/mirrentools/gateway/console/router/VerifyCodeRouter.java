package org.mirrentools.gateway.console.router;

import java.io.IOException;
import java.util.Map;

import org.mirrentools.gateway.console.common.ResultCodeEnum;
import org.mirrentools.gateway.console.common.ResultFormat;
import org.mirrentools.gateway.console.common.SessionDataStore;
import org.mirrentools.gateway.console.common.VerifyCodeUtils;

import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

/**
 * 状态码相关的处理器
 * 
 * @author <a href="http://mirrentools.org">Mirren</a>
 *
 */
public class VerifyCodeRouter {
	/**
	 * 回话处理器
	 */
	private SessionDataStore sessionStore;

	private VerifyCodeRouter(Vertx vertx) {
		sessionStore = SessionDataStore.instance(vertx);
	}

	/**
	 * 启动服务
	 * 
	 * @param vertx
	 */
	public static void startService(Vertx vertx, Router router) {
		VerifyCodeRouter impl = new VerifyCodeRouter(vertx);
		// 获取验证码数据
		router.get("/verification/data").handler(impl::get);
		// 获取验证码图片
		router.get("/verification/img/:code").blockingHandler(impl::getImage);
		// 检查验证码是否正确
		router.get("/verification/check").handler(impl::check);
	}

	/**
	 * 获取验证码数据
	 * 
	 * @param rct
	 */
	private void get(RoutingContext rct) {
		Map<String, String> Verify = VerifyCodeUtils.generateVerityData();
		String index = Verify.get("index");
		String value = Verify.get("value");
		String data = Verify.get("data");
		String code = Verify.get("code");
		sessionStore.put(index, index, value);
		JsonObject result = new JsonObject();
		result.put("index", index);
		result.put("data", data);
		result.put("code", code);
		rct.response().putHeader(HttpHeaders.CONTENT_TYPE, "application/json").end(result.toBuffer());
	}

	/**
	 * 获取验证码图片
	 * 
	 * @param rct
	 */
	private void getImage(RoutingContext rct) {
		String code = rct.pathParam("code");
		try {
			byte[] bs = VerifyCodeUtils.getImageBytes(code);
			rct.response().putHeader(HttpHeaders.CONTENT_TYPE, "image/jpeg").end(Buffer.buffer(bs));
		} catch (IOException e) {
			rct.response().putHeader(HttpHeaders.CONTENT_TYPE, "image/jpeg").end();
		}
	}

	/**
	 * 检查验证码是否正确
	 * 
	 * @param rct
	 */
	private void check(RoutingContext rct) {
		String index = rct.request().getParam("index");
		String value = rct.request().getParam("value");
		String data = sessionStore.<String>get(index, index);
		int res = (data != null && data.equals(value)) ? 1 : 0;
		JsonObject result = ResultFormat.format(ResultCodeEnum.C200, res);
		rct.response().putHeader(HttpHeaders.CONTENT_TYPE, "application/json").end(result.toBuffer());
	}
}

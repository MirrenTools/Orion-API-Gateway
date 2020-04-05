package org.mirrentools.gateway.console.common;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.vertx.core.http.HttpHeaders;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;
/**
 * 服务器的工具
 * 
 * @author <a href="http://szmirren.com">Mirren</a>
 *
 */
public class RouterUtil {
	private final Logger LOG = LogManager.getLogger(RouterUtil.class);

	/**
	 * 失败处理器
	 * 
	 * @param rct
	 */
	public void failureHandler(RoutingContext rct) {
		Throwable failure = rct.failure();
		LOG.error("发生了异常:", failure);
		HttpServerResponse response = rct.response().putHeader(HttpHeaders.CONTENT_TYPE, ContentType.JSON_UTF8.val());
		if (failure instanceof NullPointerException) {
			response.end(ResultFormat.formatAsNull(ResultCodeEnum.C412).toBuffer());
		} else {
			response.end(ResultFormat.formatAsNull(ResultCodeEnum.C500).toBuffer());
		}
	}

	/**
	 * 访问了不存在的路径处理器
	 * 
	 * @param rct
	 */
	public void _404Handler(RoutingContext rct) {
		LOG.info("用户: " + rct.request().remoteAddress().host() + "请求的了不存的路径: " + rct.request().method() + ":" + rct.request().path());
		rct.response().putHeader(HttpHeaders.CONTENT_TYPE, ContentType.JSON_UTF8.val())
				.end(ResultFormat.formatAsNull(ResultCodeEnum.C404).toBuffer());
	}

}

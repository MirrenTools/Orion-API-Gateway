package org.mirrentools.gateway.console.common;

import java.io.IOException;
import java.nio.channels.ClosedChannelException;

import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import io.vertx.ext.web.RoutingContext;

/**
 * 服务器的工具
 * 
 * @author <a href="http://szmirren.com">Mirren</a>
 *
 */
public class RouterUtil {
	/** 日志处理 */
	private final Logger LOG = LoggerFactory.getLogger(this.getClass());

	/**
	 * 失败处理器
	 * 
	 * @param rct
	 */
	public void failureHandler(RoutingContext rct) {
		int statusCode = rct.statusCode();
		Throwable failure = rct.failure();
		if (failure != null) {
			if (failure instanceof ClosedChannelException) {
				if (LOG.isDebugEnabled()) {
					LOG.debug("异常:ClosedChannelException");
				}
				return;
			}
			if (failure instanceof IOException) {
				if (failure.getMessage() != null && (failure.getMessage().contains("主机") || failure.getMessage().contains("host"))) {
					if (LOG.isDebugEnabled()) {
						LOG.debug("异常:IOException");
					}
					return;
				}
			}
			LOG.error("发生了异常:", failure);
		} else {
			LOG.error("进入了异常处理器statusCode:" + statusCode);
		}
		HttpServerResponse response = rct.response().putHeader(ContentType.CONTENT_TYPE.val(), ContentType.JSON_UTF8.val());
		if (response.ended()) {
			return;
		}
		if (statusCode == 413) {
			response.end(ResultFormat.formatAsNull(413, "Request Entity Too Large", "请求数据长度超长,请联系管理员!").toBuffer());
		} else if (statusCode == 404 || statusCode == 405) {
			the404Handler(rct);
		} else if (failure instanceof NullPointerException) {
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
	public void the404Handler(RoutingContext rct) {
		LOG.info("用户: " + rct.request().remoteAddress().host() + "请求的了不存的路径: " + rct.request().method() + ":" + rct.request().path());
		rct.response().putHeader(ContentType.CONTENT_TYPE.val(), ContentType.JSON_UTF8.val()).end(ResultFormat.formatAsNull(ResultCodeEnum.C404).toBuffer());
	}

}

package org.mirrentools.gateway.http.enums;

/**
 * HttpApi的类型
 * 
 * @author <a href="https://mirrentools.org/">Mirren</a>
 * @version 1.0.0
 */
public enum HttpApiType {
	/** http1x的API */
	HTTP1X_API(1),
	/** Http1x的接口代理 */
	HTTP1X_PROXY(2),
	/** http2x的API */
	HTTP2X_API(3),
	/** Http2x的接口代理 */
	HTTP2X_PROXY(4),
	/** 页面重定向 */
	HTTP_REDIRECT(5),
	/** 自定义类型 */
	CUSTOM(6);

	private int val;

	HttpApiType(int val) {
		this.val = val;
	}

	public int val() {
		return val;
	}
}

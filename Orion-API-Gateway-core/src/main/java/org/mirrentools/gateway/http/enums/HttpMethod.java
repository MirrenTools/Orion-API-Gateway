package org.mirrentools.gateway.http.enums;

/**
 * http的请求方式
 * 
 * @author <a href="http://szmirren.com">Mirren</a>
 *
 */
public enum HttpMethod {
	ALL("ALL"), OPTIONS("OPTIONS"), GET("GET"), HEAD("HEAD"), 
	POST("POST"), PUT("PUT"), DELETE("DELETE"), TRACE("TRACE"), 
	CONNECT("CONNECT"), PATCH("PATCH"), OTHER("OTHER");
	private String method;
	private HttpMethod(String method) {
		this.method = method;
	}
	public String method() {
		return method;
	}

}

package org.mirrentools.gateway.common;

/**
 * Orion网关的属性
 * 
 * @author <a href="http://mirrentools.org">Mirren</a>
 *
 */
public interface OrionApiGatewayAttribute {
	/**
	 * 网关的名字
	 */
	static final String NAME = "Orion-API-Gateway";
	/**
	 * OrionApiGateway版本号
	 */
	static final String VERSION = "1.0.0";
	/**
	 * session默认的cookie名字
	 */
	static final String SESSION_COOKIE_NAME = NAME + ".session";
	/**
	 * 网关userAgent默认名字
	 */
	static final String AGENT = NAME + "/" + VERSION;
}

package org.mirrentools.gateway.http;

import java.util.Set;

import org.mirrentools.gateway.http.spi.handler.OrionUrlManagerHandler;
import org.mirrentools.gateway.http.util.OrionRequestParameterConverter;

import io.vertx.core.http.HttpMethod;

/**
 * OrionAPI 请求后台
 * 
 * @author <a href="http://mirrentools.org">Mirren</a>
 *
 */
public class OrionHttpApiRequest {
	/** 请求方法 */
	private HttpMethod method;
	/** 请求超时时间 */
	private long timeout;
	/** 请求类型 */
	private String requestType;
	/** 响应类型 */
	private String responseType;
	/** 透传响应header */
	private Set<String> passHanders;
	/** 后台访问地址提供器 */
	private OrionUrlManagerHandler discoveryHandler;

	/** body数据格式化 */
	private OrionRequestParameterConverter bodyConverter;

	public HttpMethod getMethod() {
		return method;
	}

	public OrionHttpApiRequest setMethod(HttpMethod method) {
		this.method = method;
		return this;
	}

	public long getTimeout() {
		return timeout;
	}

	public OrionHttpApiRequest setTimeout(long timeout) {
		this.timeout = timeout;
		return this;
	}

	public String getRequestType() {
		return requestType;
	}

	public OrionHttpApiRequest setRequestType(String requestType) {
		this.requestType = requestType;
		return this;
	}

	public String getResponseType() {
		return responseType;
	}

	public OrionHttpApiRequest setResponseType(String responseType) {
		this.responseType = responseType;
		return this;
	}

	public Set<String> getPassHanders() {
		return passHanders;
	}

	public OrionHttpApiRequest setPassHanders(Set<String> passHanders) {
		this.passHanders = passHanders;
		return this;
	}

	public OrionUrlManagerHandler getDiscoveryHandler() {
		return discoveryHandler;
	}

	public OrionHttpApiRequest setDiscoveryHandler(OrionUrlManagerHandler discoveryHandler) {
		this.discoveryHandler = discoveryHandler;
		return this;
	}

	public OrionRequestParameterConverter getBodyConverter() {
		return bodyConverter;
	}

	public OrionHttpApiRequest setBodyConverter(OrionRequestParameterConverter bodyConverter) {
		this.bodyConverter = bodyConverter;
		return this;
	}

}

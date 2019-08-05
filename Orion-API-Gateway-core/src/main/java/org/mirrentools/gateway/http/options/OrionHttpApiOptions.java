package org.mirrentools.gateway.http.options;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.mirrentools.gateway.http.OrionHttpApiRequest;
import org.mirrentools.gateway.http.OrionHttpApiResponse;
import org.mirrentools.gateway.http.OrionHttpParameterModel;
import org.mirrentools.gateway.http.enums.HttpMethod;
import org.mirrentools.gateway.http.spi.handler.OrionAccessLimitHandler;
import org.mirrentools.gateway.http.spi.handler.OrionAfterHandler;
import org.mirrentools.gateway.http.spi.handler.OrionAuthenticationHandler;
import org.mirrentools.gateway.http.spi.handler.OrionBeforeHandler;
import org.mirrentools.gateway.http.spi.handler.OrionBlacklistHandler;
import org.mirrentools.gateway.http.spi.handler.OrionCacheHandler;
import org.mirrentools.gateway.http.spi.handler.OrionMainHandler;
import org.mirrentools.gateway.http.spi.handler.OrionParameterCheckHandler;
import org.mirrentools.gateway.http.spi.listener.OrionFailureHandlerListener;
import org.mirrentools.gateway.http.spi.listener.OrionHandlerEndListener;
import org.mirrentools.gateway.http.spi.listener.OrionHandlerListener;

import io.vertx.core.http.HttpClientOptions;

/**
 * API的配置
 * 
 * @author <a href="http://mirrentools.org">Mirren</a>
 *
 */
public class OrionHttpApiOptions {
	/** API的id */
	private String id;
	/** API的名称 */
	private String name;
	/** API的简介 */
	private String summery;
	/** 请求的path */
	private String path;
	/** 请求的类型 */
	private HttpMethod method;
	/** 虚拟主机 */
	private String virtualHost;
	/** 接口consumes */
	private Set<String> consumes;
	/** 透传参数 */
	private List<OrionHttpParameterModel> passParameters;
	/** 映射Path参数 */
	private List<OrionHttpParameterModel> pathParameters;
	/** 映射Header参数 */
	private List<OrionHttpParameterModel> headerParameters;
	/** 映射Query参数 */
	private List<OrionHttpParameterModel> queryParameters;
	/** 透传body内容 */
	private boolean passBody;
	/** 映射Body参数 */
	private List<OrionHttpParameterModel> bodyParameters;

	/** 黑名单处理器 */
	private OrionBlacklistHandler blacklistHandler;
	/** 访问限制处理器 */
	private OrionAccessLimitHandler limitHandler;
	/** 参数处理器 */
	private OrionParameterCheckHandler parameterHandler;
	/** 鉴权处理器 */
	private OrionAuthenticationHandler authenticationHandler;
	/** 前置处理器 */
	private OrionBeforeHandler beforeHandler;
	/** 缓存处理器 */
	private OrionCacheHandler cacheHandler;
	/** 中心处理器 */
	private OrionMainHandler mainHandler;
	/** 后置处理器 */
	private OrionAfterHandler afterHandler;

	/** 进入中心处理器的监听器集 */
	private List<OrionHandlerListener> handlerListeners;
	/** 中心处理器处理完毕的监听器集 */
	private List<OrionHandlerEndListener> handlerEndListeners;
	/** 进入异常的监听器集 */
	private List<OrionFailureHandlerListener> failureHandlerListeners;

	/** 请求数据 */
	private OrionHttpApiRequest request;
	/** 状态响应 */
	private OrionHttpApiResponse response;
	/** HTTP客户端配置信息 */
	private HttpClientOptions httpClientOptions;

	public String getId() {
		return id;
	}

	public OrionHttpApiOptions setId(String id) {
		this.id = id;
		return this;
	}

	public String getName() {
		return name;
	}

	public OrionHttpApiOptions setName(String name) {
		this.name = name;
		return this;
	}

	public String getSummery() {
		return summery;
	}

	public OrionHttpApiOptions setSummery(String summery) {
		this.summery = summery;
		return this;
	}

	public String getPath() {
		return path;
	}

	public OrionHttpApiOptions setPath(String path) {
		this.path = path;
		return this;
	}

	public HttpMethod getMethod() {
		return method;
	}

	public OrionHttpApiOptions setMethod(HttpMethod method) {
		this.method = method;
		return this;
	}

	public String getVirtualHost() {
		return virtualHost;
	}

	public OrionHttpApiOptions setVirtualHost(String virtualHost) {
		this.virtualHost = virtualHost;
		return this;
	}

	public Set<String> getConsumes() {
		return consumes;
	}

	public OrionHttpApiOptions setConsumes(Set<String> consumes) {
		this.consumes = consumes;
		return this;
	}

	public List<OrionHttpParameterModel> getPassParameters() {
		return passParameters;
	}

	public OrionHttpApiOptions setPassParameters(List<OrionHttpParameterModel> passParameters) {
		this.passParameters = passParameters;
		return this;
	}

	public List<OrionHttpParameterModel> getPathParameters() {
		return pathParameters;
	}

	public OrionHttpApiOptions setPathParameters(List<OrionHttpParameterModel> pathParameters) {
		this.pathParameters = pathParameters;
		return this;
	}

	public List<OrionHttpParameterModel> getHeaderParameters() {
		return headerParameters;
	}

	public OrionHttpApiOptions setHeaderParameters(List<OrionHttpParameterModel> headerParameters) {
		this.headerParameters = headerParameters;
		return this;
	}

	public List<OrionHttpParameterModel> getQueryParameters() {
		return queryParameters;
	}

	public OrionHttpApiOptions setQueryParameters(List<OrionHttpParameterModel> queryParameters) {
		this.queryParameters = queryParameters;
		return this;
	}

	public boolean isPassBody() {
		return passBody;
	}

	public OrionHttpApiOptions setPassBody(boolean passBody) {
		this.passBody = passBody;
		return this;
	}

	public List<OrionHttpParameterModel> getBodyParameters() {
		return bodyParameters;
	}

	public OrionHttpApiOptions setBodyParameters(List<OrionHttpParameterModel> bodyParameters) {
		this.bodyParameters = bodyParameters;
		return this;
	}

	public OrionBlacklistHandler getBlacklistHandler() {
		return blacklistHandler;
	}

	public OrionHttpApiOptions setBlacklistHandler(OrionBlacklistHandler blacklistHandler) {
		this.blacklistHandler = blacklistHandler;
		return this;
	}

	public OrionAccessLimitHandler getLimitHandler() {
		return limitHandler;
	}

	public OrionHttpApiOptions setLimitHandler(OrionAccessLimitHandler limitHandler) {
		this.limitHandler = limitHandler;
		return this;
	}

	public OrionParameterCheckHandler getParameterHandler() {
		return parameterHandler;
	}

	public OrionHttpApiOptions setParameterHandler(OrionParameterCheckHandler parameterHandler) {
		this.parameterHandler = parameterHandler;
		return this;
	}

	public OrionAuthenticationHandler getAuthenticationHandler() {
		return authenticationHandler;
	}

	public OrionHttpApiOptions setAuthenticationHandler(OrionAuthenticationHandler authenticationHandler) {
		this.authenticationHandler = authenticationHandler;
		return this;
	}

	public OrionBeforeHandler getBeforeHandler() {
		return beforeHandler;
	}

	public OrionHttpApiOptions setBeforeHandler(OrionBeforeHandler beforeHandler) {
		this.beforeHandler = beforeHandler;
		return this;
	}

	public OrionCacheHandler getCacheHandler() {
		return cacheHandler;
	}

	public OrionHttpApiOptions setCacheHandler(OrionCacheHandler cacheHandler) {
		this.cacheHandler = cacheHandler;
		return this;
	}

	public OrionMainHandler getMainHandler() {
		return mainHandler;
	}

	public OrionHttpApiOptions setMainHandler(OrionMainHandler mainHandler) {
		this.mainHandler = mainHandler;
		return this;
	}

	public OrionAfterHandler getAfterHandler() {
		return afterHandler;
	}

	public OrionHttpApiOptions setAfterHandler(OrionAfterHandler afterHandler) {
		this.afterHandler = afterHandler;
		return this;
	}

	public List<OrionHandlerListener> getHandlerListeners() {
		return handlerListeners;
	}

	public OrionHttpApiOptions addHandlerListeners(OrionHandlerListener handlerListener) {
		if (getHandlerListeners() == null) {
			setHandlerListeners(new ArrayList<>());
		}
		getHandlerListeners().add(handlerListener);
		return this;
	}

	public OrionHttpApiOptions setHandlerListeners(List<OrionHandlerListener> handlerListeners) {
		this.handlerListeners = handlerListeners;
		return this;
	}

	public List<OrionHandlerEndListener> getHandlerEndListeners() {
		return handlerEndListeners;
	}

	public OrionHttpApiOptions addHandlerEndListeners(OrionHandlerEndListener handlerEndListener) {
		if (getHandlerEndListeners() == null) {
			setHandlerEndListeners(new ArrayList<>());
		}
		getHandlerEndListeners().add(handlerEndListener);
		return this;
	}

	public OrionHttpApiOptions setHandlerEndListeners(List<OrionHandlerEndListener> handlerEndListeners) {
		this.handlerEndListeners = handlerEndListeners;
		return this;
	}

	public List<OrionFailureHandlerListener> getFailureHandlerListeners() {
		return failureHandlerListeners;
	}

	public OrionHttpApiOptions addFailureHandlerListeners(OrionFailureHandlerListener failureHandlerListener) {
		if (getFailureHandlerListeners() == null) {
			setFailureHandlerListeners(new ArrayList<>());
		}
		getFailureHandlerListeners().add(failureHandlerListener);
		return this;
	}

	public OrionHttpApiOptions setFailureHandlerListeners(List<OrionFailureHandlerListener> failureHandlerListeners) {
		this.failureHandlerListeners = failureHandlerListeners;
		return this;
	}

	public OrionHttpApiRequest getRequest() {
		return request;
	}

	public OrionHttpApiOptions setRequest(OrionHttpApiRequest request) {
		this.request = request;
		return this;
	}

	public OrionHttpApiResponse getResponse() {
		return response;
	}

	public OrionHttpApiOptions setResponse(OrionHttpApiResponse response) {
		this.response = response;
		return this;
	}

	public HttpClientOptions getHttpClientOptions() {
		return httpClientOptions;
	}

	public OrionHttpApiOptions setHttpClientOptions(HttpClientOptions httpClientOptions) {
		this.httpClientOptions = httpClientOptions;
		return this;
	}

}

package org.mirrentools.gateway.http;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.mirrentools.gateway.http.enums.HttpApiType;
import org.mirrentools.gateway.http.enums.HttpMethod;
import org.mirrentools.gateway.http.model.OrionHttpApiParameter;
import org.mirrentools.gateway.http.model.OrionHttpApiRequest;
import org.mirrentools.gateway.http.model.OrionHttpApiResponse;
import org.mirrentools.gateway.http.spi.handler.OrionAccessLimitHandler;
import org.mirrentools.gateway.http.spi.handler.OrionAfterHandler;
import org.mirrentools.gateway.http.spi.handler.OrionAuthenticationHandler;
import org.mirrentools.gateway.http.spi.handler.OrionBeforeHandler;
import org.mirrentools.gateway.http.spi.handler.OrionBlacklistHandler;
import org.mirrentools.gateway.http.spi.handler.OrionCacheHandler;
import org.mirrentools.gateway.http.spi.handler.OrionMainHandler;
import org.mirrentools.gateway.http.spi.handler.OrionParameterCheckHandler;
import org.mirrentools.gateway.http.spi.handler.OrionWanderHandler;
import org.mirrentools.gateway.http.spi.listener.OrionFailureHandlerListener;
import org.mirrentools.gateway.http.spi.listener.OrionHandlerEndListener;
import org.mirrentools.gateway.http.spi.listener.OrionHandlerListener;

import io.vertx.core.http.HttpClientOptions;
import io.vertx.core.json.JsonObject;

/**
 * API的配置
 * 
 * @author <a href="http://mirrentools.org">Mirren</a>
 *
 */
public class OrionHttpApiOptions {
	/** API的id */
	private String id;
	/** API的类型 */
	private HttpApiType type;
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
	/** API处理内容类型 */
	private Set<String> consumes;
	/** API要获取的参数 */
	private OrionHttpApiParameter parameter;

	/** 游行处理器 */
	private OrionWanderHandler wanderHandler;
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

	/** 请求后端的数据 */
	private OrionHttpApiRequest request;
	/** 后端状态响应 */
	private OrionHttpApiResponse response;
	/** HTTP客户端配置信息 */
	private HttpClientOptions httpClientOptions;

	/** 拓展参数 */
	private Object data;

	/**
	 * 实例化
	 */
	public OrionHttpApiOptions() {
		super();
	}

	/**
	 * 通过配置初始化
	 * 
	 * @param options
	 */
	public OrionHttpApiOptions(JsonObject options) {
		super();
		OrionHttpApiOptionsConverter.fromJson(options, this);
	}

	/**
	 * 将当前类转换为JsonObject
	 * 
	 * @return
	 */
	public JsonObject toJson() {
		JsonObject result = new JsonObject();
		OrionHttpApiOptionsConverter.toJson(this, result);
		return result;
	}

	/**
	 * 获取API的id
	 * 
	 * @return
	 */
	public String getId() {
		return id;
	}

	/**
	 * 设置API的id
	 * 
	 * @param id
	 * @return
	 */
	public OrionHttpApiOptions setId(String id) {
		this.id = id;
		return this;
	}

	/**
	 * 获取API的类型
	 * 
	 * @return
	 */
	public HttpApiType getType() {
		return type;
	}

	/**
	 * 设置API的类型
	 * 
	 * @param type
	 * @return
	 */
	public OrionHttpApiOptions setType(HttpApiType type) {
		this.type = type;
		return this;
	}

	/**
	 * 
	 * 获取API的名称
	 * 
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置API的名称
	 * 
	 * @param name
	 * @return
	 */
	public OrionHttpApiOptions setName(String name) {
		this.name = name;
		return this;
	}

	/**
	 * 获取API简介
	 * 
	 * @return
	 */
	public String getSummery() {
		return summery;
	}

	/**
	 * 设置API简介
	 * 
	 * @param summery
	 * @return
	 */
	public OrionHttpApiOptions setSummery(String summery) {
		this.summery = summery;
		return this;
	}

	/**
	 * 获取API的Path
	 * 
	 * @return
	 */
	public String getPath() {
		return path;
	}

	/**
	 * 设置API的Path
	 * 
	 * @param path
	 * @return
	 */
	public OrionHttpApiOptions setPath(String path) {
		this.path = path;
		return this;
	}

	/**
	 * 获取API的method
	 * 
	 * @return
	 */
	public HttpMethod getMethod() {
		return method;
	}

	/**
	 * 设置API的method
	 * 
	 * @param method
	 * @return
	 */
	public OrionHttpApiOptions setMethod(HttpMethod method) {
		this.method = method;
		return this;
	}

	/**
	 * 获取虚拟主机名称
	 * 
	 * @return
	 */
	public String getVirtualHost() {
		return virtualHost;
	}

	/**
	 * 设置虚拟主机名称
	 * 
	 * @param virtualHost
	 * @return
	 */
	public OrionHttpApiOptions setVirtualHost(String virtualHost) {
		this.virtualHost = virtualHost;
		return this;
	}

	/**
	 * 获取API处理内容类型
	 * 
	 * @return
	 */
	public Set<String> getConsumes() {
		return consumes;
	}

	/**
	 * 设置API处理内容类型
	 * 
	 * @param consumes
	 * @return
	 */
	public OrionHttpApiOptions setConsumes(Set<String> consumes) {
		this.consumes = consumes;
		return this;
	}

	/**
	 * 获取API要处理的参数
	 * 
	 * @return
	 */
	public OrionHttpApiParameter getParameter() {
		return parameter;
	}

	/**
	 * 设置API要处理的参数
	 * 
	 * @param parameter
	 * @return
	 */
	public OrionHttpApiOptions setParameter(OrionHttpApiParameter parameter) {
		this.parameter = parameter;
		return this;
	}

	/**
	 * 获取游行处理器
	 * 
	 * @return
	 */
	public OrionWanderHandler getWanderHandler() {
		return wanderHandler;
	}

	/**
	 * 设置游行处理器
	 * 
	 * @param wanderHandler
	 * @return
	 */
	public OrionHttpApiOptions setWanderHandler(OrionWanderHandler wanderHandler) {
		this.wanderHandler = wanderHandler;
		return this;
	}

	/**
	 * 获取黑名单处理器
	 * 
	 * @return
	 */
	public OrionBlacklistHandler getBlacklistHandler() {
		return blacklistHandler;
	}

	/**
	 * 设置黑名单处理器
	 * 
	 * @param blacklistHandler
	 * @return
	 */
	public OrionHttpApiOptions setBlacklistHandler(OrionBlacklistHandler blacklistHandler) {
		this.blacklistHandler = blacklistHandler;
		return this;
	}

	/**
	 * 获取访问限制处理器
	 * 
	 * @return
	 */
	public OrionAccessLimitHandler getLimitHandler() {
		return limitHandler;
	}

	/**
	 * 设置访问限制处理器
	 * 
	 * @param limitHandler
	 * @return
	 */
	public OrionHttpApiOptions setLimitHandler(OrionAccessLimitHandler limitHandler) {
		this.limitHandler = limitHandler;
		return this;
	}

	/**
	 * 获取参数处理器
	 * 
	 * @return
	 */
	public OrionParameterCheckHandler getParameterHandler() {
		return parameterHandler;
	}

	/**
	 * 设置参数处理器
	 * 
	 * @param parameterHandler
	 * @return
	 */
	public OrionHttpApiOptions setParameterHandler(OrionParameterCheckHandler parameterHandler) {
		this.parameterHandler = parameterHandler;
		return this;
	}

	/**
	 * 获取权限认证处理器
	 * 
	 * @return
	 */
	public OrionAuthenticationHandler getAuthenticationHandler() {
		return authenticationHandler;
	}

	/**
	 * 设置权限认证处理器
	 * 
	 * @param authenticationHandler
	 * @return
	 */
	public OrionHttpApiOptions setAuthenticationHandler(OrionAuthenticationHandler authenticationHandler) {
		this.authenticationHandler = authenticationHandler;
		return this;
	}

	/**
	 * 获取前置处理器
	 * 
	 * @return
	 */
	public OrionBeforeHandler getBeforeHandler() {
		return beforeHandler;
	}

	/**
	 * 设置前置处理器
	 * 
	 * @param beforeHandler
	 * @return
	 */
	public OrionHttpApiOptions setBeforeHandler(OrionBeforeHandler beforeHandler) {
		this.beforeHandler = beforeHandler;
		return this;
	}

	/**
	 * 获取缓存处理器
	 * 
	 * @return
	 */
	public OrionCacheHandler getCacheHandler() {
		return cacheHandler;
	}

	/**
	 * 设置缓存处理器
	 * 
	 * @param cacheHandler
	 * @return
	 */
	public OrionHttpApiOptions setCacheHandler(OrionCacheHandler cacheHandler) {
		this.cacheHandler = cacheHandler;
		return this;
	}

	/**
	 * 获取中心处理器
	 * 
	 * @return
	 */
	public OrionMainHandler getMainHandler() {
		return mainHandler;
	}

	/**
	 * 设置中心处理器
	 * 
	 * @param mainHandler
	 * @return
	 */
	public OrionHttpApiOptions setMainHandler(OrionMainHandler mainHandler) {
		this.mainHandler = mainHandler;
		return this;
	}

	/**
	 * 获取后置处理器
	 * 
	 * @return
	 */
	public OrionAfterHandler getAfterHandler() {
		return afterHandler;
	}

	/**
	 * 设置后置处理器
	 * 
	 * @param afterHandler
	 * @return
	 */
	public OrionHttpApiOptions setAfterHandler(OrionAfterHandler afterHandler) {
		this.afterHandler = afterHandler;
		return this;
	}

	/**
	 * 获取进入中心处理器的监听器集
	 * 
	 * @return
	 */
	public List<OrionHandlerListener> getHandlerListeners() {
		return handlerListeners;
	}

	/**
	 * 添加进入中心处理器的监听器集
	 * 
	 * @param handlerListener
	 * @return
	 */
	public OrionHttpApiOptions addHandlerListeners(OrionHandlerListener handlerListener) {
		if (getHandlerListeners() == null) {
			setHandlerListeners(new ArrayList<>());
		}
		getHandlerListeners().add(handlerListener);
		return this;
	}

	/**
	 * 设置进入中心处理器的监听器集
	 * 
	 * @param handlerListeners
	 * @return
	 */
	public OrionHttpApiOptions setHandlerListeners(List<OrionHandlerListener> handlerListeners) {
		this.handlerListeners = handlerListeners;
		return this;
	}

	/**
	 * 获取中心处理器处理完毕的监听器集
	 * 
	 * @return
	 */
	public List<OrionHandlerEndListener> getHandlerEndListeners() {
		return handlerEndListeners;
	}

	/**
	 * 添加中心处理器处理完毕的监听器集
	 * 
	 * @param handlerEndListener
	 * @return
	 */
	public OrionHttpApiOptions addHandlerEndListeners(OrionHandlerEndListener handlerEndListener) {
		if (getHandlerEndListeners() == null) {
			setHandlerEndListeners(new ArrayList<>());
		}
		getHandlerEndListeners().add(handlerEndListener);
		return this;
	}

	/**
	 * 设置中心处理器处理完毕的监听器集
	 * 
	 * @param handlerEndListeners
	 * @return
	 */
	public OrionHttpApiOptions setHandlerEndListeners(List<OrionHandlerEndListener> handlerEndListeners) {
		this.handlerEndListeners = handlerEndListeners;
		return this;
	}

	/**
	 * 获取进入异常的监听器集
	 * 
	 * @return
	 */
	public List<OrionFailureHandlerListener> getFailureHandlerListeners() {
		return failureHandlerListeners;
	}

	/**
	 * 添加进入异常的监听器集
	 * 
	 * @param failureHandlerListener
	 * @return
	 */
	public OrionHttpApiOptions addFailureHandlerListeners(OrionFailureHandlerListener failureHandlerListener) {
		if (getFailureHandlerListeners() == null) {
			setFailureHandlerListeners(new ArrayList<>());
		}
		getFailureHandlerListeners().add(failureHandlerListener);
		return this;
	}

	/**
	 * 设置进入异常的监听器集
	 * 
	 * @param failureHandlerListeners
	 * @return
	 */
	public OrionHttpApiOptions setFailureHandlerListeners(List<OrionFailureHandlerListener> failureHandlerListeners) {
		this.failureHandlerListeners = failureHandlerListeners;
		return this;
	}

	/**
	 * 获取请求后端的数据
	 * 
	 * @return
	 */
	public OrionHttpApiRequest getRequest() {
		return request;
	}

	/**
	 * 设置请求后端的数据
	 * 
	 * @param request
	 * @return
	 */
	public OrionHttpApiOptions setRequest(OrionHttpApiRequest request) {
		this.request = request;
		return this;
	}

	/**
	 * 获取后端响应的数据
	 * 
	 * @return
	 */
	public OrionHttpApiResponse getResponse() {
		return response;
	}

	/**
	 * 设置后端响应的数据
	 * 
	 * @param response
	 * @return
	 */
	public OrionHttpApiOptions setResponse(OrionHttpApiResponse response) {
		this.response = response;
		return this;
	}

	/**
	 * 获取HTTP客户端配置信息
	 * 
	 * @return
	 */
	public HttpClientOptions getHttpClientOptions() {
		return httpClientOptions;
	}

	/**
	 * 设置HTTP客户端配置信息
	 * 
	 * @param httpClientOptions
	 * @return
	 */
	public OrionHttpApiOptions setHttpClientOptions(HttpClientOptions httpClientOptions) {
		this.httpClientOptions = httpClientOptions;
		return this;
	}

	/**
	 * 获取拓展参数
	 * 
	 * @return
	 */
	public Object getData() {
		return data;
	}

	/**
	 * 设置拓展参数
	 * 
	 * @param data
	 * @return
	 */
	public OrionHttpApiOptions setData(Object data) {
		this.data = data;
		return this;
	}

	@Override
	public String toString() {
		return toJson().encodePrettily();
	}

}

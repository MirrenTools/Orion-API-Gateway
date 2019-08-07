package org.mirrentools.gateway.http.model;

import java.util.List;

import org.mirrentools.gateway.http.util.OrionHttp1xBodyDecoder;

/**
 * 请求参数定义
 * 
 * @author <a href="https://mirrentools.org/">Mirren</a>
 * @version 1.0.0
 */
public class OrionHttpApiParameter {
	/** Path参数 */
	private List<OrionParameterModel> pathParameters;
	/** 是否透传header参数,content-type不透传 */
	private boolean passHeader;
	/** Header参数 */
	private List<OrionParameterModel> headerParameters;
	/** 是否透传query参数 */
	private boolean passQuery;
	/** Query参数 */
	private List<OrionParameterModel> queryParameters;
	/** 是否透传body参数 */
	private boolean passBody;
	/** Body参数 */
	private List<OrionParameterModel> bodyParameters;
	/** body参数解码器 */
	private OrionHttp1xBodyDecoder bodyDecoder;

	/**
	 * 获取Path参数
	 * 
	 * @return
	 */
	public List<OrionParameterModel> getPathParameters() {
		return pathParameters;
	}

	/**
	 * 设置path参数
	 * 
	 * @param pathParameters
	 * @return
	 */
	public OrionHttpApiParameter setPathParameters(List<OrionParameterModel> pathParameters) {
		this.pathParameters = pathParameters;
		return this;
	}

	/**
	 * 是否是否透传header所有参数,除content-type
	 * 
	 * @return
	 */
	public boolean isPassHeader() {
		return passHeader;
	}

	/**
	 * 设置是否是否透传header所有参数
	 * 
	 * @param passHeader
	 */
	public void setPassHeader(boolean passHeader) {
		this.passHeader = passHeader;
	}

	/**
	 * 获取Header参数
	 * 
	 * @return
	 */
	public List<OrionParameterModel> getHeaderParameters() {
		return headerParameters;
	}

	/**
	 * 设置header参数
	 * 
	 * @param headerParameters
	 * @return
	 */
	public OrionHttpApiParameter setHeaderParameters(List<OrionParameterModel> headerParameters) {
		this.headerParameters = headerParameters;
		return this;
	}

	/**
	 * 是否透传Query参数
	 * 
	 * @return
	 */
	public boolean isPassQuery() {
		return passQuery;
	}

	/**
	 * 设置是否透传Query参数
	 * 
	 * @param passQuery
	 */
	public void setPassQuery(boolean passQuery) {
		this.passQuery = passQuery;
	}

	/**
	 * 获取query参数
	 * 
	 * @return
	 */
	public List<OrionParameterModel> getQueryParameters() {
		return queryParameters;
	}

	/**
	 * 设置query参数
	 * 
	 * @param queryParameters
	 * @return
	 */
	public OrionHttpApiParameter setQueryParameters(List<OrionParameterModel> queryParameters) {
		this.queryParameters = queryParameters;
		return this;
	}

	/**
	 * 是否透传body参数
	 * 
	 * @return
	 */
	public boolean isPassBody() {
		return passBody;
	}

	/**
	 * 设置是否透传body参数
	 * 
	 * @param passBody
	 * @return
	 */
	public OrionHttpApiParameter setPassBody(boolean passBody) {
		this.passBody = passBody;
		return this;
	}

	/**
	 * 获取body参数
	 * 
	 * @return
	 */
	public List<OrionParameterModel> getBodyParameters() {
		return bodyParameters;
	}

	/**
	 * 设置body参数
	 * 
	 * @param bodyParameters
	 * @return
	 */
	public OrionHttpApiParameter setBodyParameters(List<OrionParameterModel> bodyParameters) {
		this.bodyParameters = bodyParameters;
		return this;
	}

	/**
	 * 获取body参数解码器
	 * 
	 * @return
	 */
	public OrionHttp1xBodyDecoder getBodyDecoder() {
		return bodyDecoder;
	}

	/**
	 * 设置body参数解码器
	 * 
	 * @param bodyDecoder
	 * @return
	 */
	public OrionHttpApiParameter setBodyDecoder(OrionHttp1xBodyDecoder bodyDecoder) {
		this.bodyDecoder = bodyDecoder;
		return this;
	}

}

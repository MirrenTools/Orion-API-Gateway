package org.mirrentools.gateway.http.model;

/**
 * OrionAPI响应
 * 
 * @author <a href="http://mirrentools.org">Mirren</a>
 *
 */
public class OrionHttpApiResponse {
	/** 找不到资源返回 */
	private OrionStatusResponse notFoundResponse;
	/** 在黑名单中失败返回 */
	private OrionStatusResponse forbiddenResponse;
	/** 参数错误返回 */
	private OrionStatusResponse badRequestResponse;
	/** 访问限制返回 */
	private OrionStatusResponse accessLimitResponse;
	/** 连接不上后端返回 */
	private OrionStatusResponse badGatewayResponse;
	/** 失败返回 */
	private OrionStatusResponse failureResponse;

	public OrionStatusResponse getNotFoundResponse() {
		return notFoundResponse;
	}

	public void setNotFoundResponse(OrionStatusResponse notFoundResponse) {
		this.notFoundResponse = notFoundResponse;
	}

	public OrionStatusResponse getForbiddenResponse() {
		return forbiddenResponse;
	}

	public void setForbiddenResponse(OrionStatusResponse forbiddenResponse) {
		this.forbiddenResponse = forbiddenResponse;
	}

	public OrionStatusResponse getBadRequestResponse() {
		return badRequestResponse;
	}

	public void setBadRequestResponse(OrionStatusResponse badRequestResponse) {
		this.badRequestResponse = badRequestResponse;
	}

	public OrionStatusResponse getAccessLimitResponse() {
		return accessLimitResponse;
	}

	public void setAccessLimitResponse(OrionStatusResponse accessLimitResponse) {
		this.accessLimitResponse = accessLimitResponse;
	}

	public OrionStatusResponse getBadGatewayResponse() {
		return badGatewayResponse;
	}

	public void setBadGatewayResponse(OrionStatusResponse badGatewayResponse) {
		this.badGatewayResponse = badGatewayResponse;
	}

	public OrionStatusResponse getFailureResponse() {
		return failureResponse;
	}

	public void setFailureResponse(OrionStatusResponse failureResponse) {
		this.failureResponse = failureResponse;
	}

}

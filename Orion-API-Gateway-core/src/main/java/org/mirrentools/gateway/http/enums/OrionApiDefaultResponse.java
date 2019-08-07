package org.mirrentools.gateway.http.enums;

import org.mirrentools.gateway.http.model.OrionStatusResponse;

/**
 * 响应状态默认值
 * 
 * @author <a href="http://mirrentools.org">Mirren</a>
 *
 */
public enum OrionApiDefaultResponse {
	/** 无法连接上后台 */
	BAD_GATEWAY(new OrionStatusResponse(502, "text/html", "Bad Gateway", "Bad Gateway")),
	/** 异常 */
	FAILURE(new OrionStatusResponse(500, "text/html", "Internal Server Error", "Internal Server Error")),
	/** 找不到资源 */
	NOT_FOUND(new OrionStatusResponse(404, "text/html", "Not Found", "Not Found")),
	/** 在黑名单列表中或没有权限 */
	FORBIDDEN(new OrionStatusResponse(403, "text/html", "Forbidden", "Forbidden")),
	/** 网关入口参数检查失败 */
	BAD_REQUEST(new OrionStatusResponse(400, "text/html", "Bad Request", "Bad Request")),
	/** 访问限制 */
	ACCESS_LIMIT(new OrionStatusResponse(400, "text/html", "API Access Limitations", "API Access Limitations")),;

	private OrionStatusResponse data;

	private OrionApiDefaultResponse(OrionStatusResponse data) {
		this.data = data;
	}

	public OrionStatusResponse data() {
		return data;
	}

}

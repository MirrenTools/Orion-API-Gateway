package org.mirrentools.gateway.http.util;

import java.util.List;

import org.mirrentools.gateway.http.OrionHttpParameterModel;

import io.vertx.core.buffer.Buffer;

/**
 * 装载格式化请求后台的数据
 * 
 * @author <a href="http://mirrentools.org">Mirren</a>
 *
 */
public interface OrionRequestParameterConverter {
	
	/**
	 * 转换数据
	 * 
	 * @param contentType
	 *          请求后端的contentType
	 * @param parameters
	 *          body参数
	 * @return
	 */
	Buffer converter(String contentType, List<OrionHttpParameterModel> parameters);
}

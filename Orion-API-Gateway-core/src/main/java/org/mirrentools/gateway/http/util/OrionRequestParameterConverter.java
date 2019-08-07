package org.mirrentools.gateway.http.util;

import java.util.List;

import org.mirrentools.gateway.http.model.OrionParameterModel;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
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
	 * @param contentType 请求后端的contentType
	 * @param parameters  body参数
	 * @return
	 */
	Buffer converter(String contentType, List<OrionParameterModel> parameters);

	/**
	 * 异步转换数据
	 * 
	 * @param contentType 请求后端的contentType
	 * @param parameters  body参数
	 * @param handler     转换结果
	 */
	void converter(String contentType, List<OrionParameterModel> parameters, Handler<AsyncResult<Buffer>> handler);

}

package org.mirrentools.gateway.http.util;

import java.util.List;

import org.mirrentools.gateway.http.model.OrionParameterModel;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;

/**
 * http1.x的body内容解析
 * 
 * @author <a href="https://mirrentools.org/">Mirren</a>
 * @version 1.0.0
 */
public interface OrionHttp1xBodyDecoder {

	/**
	 * 转换数据
	 * 
	 * @param buffer body的数据
	 * @return
	 */
	List<OrionParameterModel> decoder(Buffer buffer);

	/**
	 * 转换数据
	 * 
	 * @param buffer  body的数据
	 * @param handler 异步处理后的结果
	 */
	void decoder(Buffer buffer, Handler<AsyncResult<List<OrionParameterModel>>> handler);

}

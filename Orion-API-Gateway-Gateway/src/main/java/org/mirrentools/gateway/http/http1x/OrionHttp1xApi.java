package org.mirrentools.gateway.http.http1x;

import org.mirrentools.gateway.http.OrionHttpApi;
import org.mirrentools.gateway.http.OrionHttpApiOptions;

/**
 * http1.x的API接口
 * 
 * @author <a href="http://mirrentools.org">Mirren</a>
 *
 */
public interface OrionHttp1xApi extends OrionHttpApi {
	/**
	 * 创建一个http1.x接口实现
	 * 
	 * @param options
	 * @return
	 */
	static OrionHttp1xApi create(OrionHttpApiOptions options) {
		return new OrionHttp1xApiImpl(options);
	}
}

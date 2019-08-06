package org.mirrentools.gateway.verticle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;

/**
 * Http1.x的应用
 * 
 * @author <a href="https://mirrentools.org/">Mirren</a>
 * @version 1.0.0
 */
public class OrionHttpVerticle extends AbstractVerticle {
	/** 日志处理 */
	private final Logger LOG = LogManager.getLogger(this.getClass());

	/** 本应用实例的id */
	private String id;

	@Override
	public void start(Promise<Void> start) throws Exception {

	}

	@Override
	public void stop() throws Exception {
		
	}

}

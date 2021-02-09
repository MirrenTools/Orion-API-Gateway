package org.mirrentools.gateway.http.http1x;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Optional;

import org.mirrentools.gateway.OrionProcessEndException;
import org.mirrentools.gateway.common.OrionApiGatewayAttribute;
import org.mirrentools.gateway.http.OrionHttpApiOptions;
import org.mirrentools.gateway.http.enums.OrionApiDefaultResponse;
import org.mirrentools.gateway.http.enums.WanderDestination;
import org.mirrentools.gateway.http.model.OrionParameter;
import org.mirrentools.gateway.http.model.OrionStatusResponse;
import org.mirrentools.gateway.http.spi.handler.OrionCacheHandler;

import io.vertx.core.Future;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpClientResponse;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import io.vertx.ext.web.RoutingContext;

/**
 * http1.x的API接口的默认实现
 * 
 * @author <a href="http://mirrentools.org">Mirren</a>
 *
 */
public class OrionHttp1xApiImpl implements OrionHttp1xApi {

	/** 日志处理 */
	private final Logger LOG = LoggerFactory.getLogger(OrionHttp1xApiImpl.class);

	/** OrionHttp1xApi配置 */
	private OrionHttpApiOptions options;
	/** 游行处理器的位置 */
	private WanderDestination destination;

	/**
	 * 初始化
	 * 
	 * @param options
	 */
	public OrionHttp1xApiImpl(OrionHttpApiOptions options) {
		super();
		Objects.requireNonNull(options, "The options cannot be null");
		Objects.requireNonNull(options.getMainHandler(), "The main handler cannot be null ");
		if (options.getWanderHandler() != null) {
			Objects.requireNonNull(options.getWanderHandler().getDestination(),
					"If the WanderHandler is not null, then the Destination cannot be null.");
			this.destination = options.getWanderHandler().getDestination();
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("初始化OrionHttp1xApi->Options:" + options);
		}
		this.options = options;
	}

	@Override
	public void handle(RoutingContext event) {
		// 添加请求到达监听器
		if (options.getHandlerListeners() != null) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("请求到达监听器->数量:" + options.getHandlerListeners().size());
			}
			options.getHandlerListeners().forEach(e -> e.handle(event));
		}
		// 添加请求结束监听器
		event.response().endHandler(end -> {
			if (options.getHandlerEndListeners() != null) {
				if (LOG.isDebugEnabled()) {
					LOG.debug("请求结束监听器->数量:" + options.getHandlerEndListeners().size());
				}
				options.getHandlerEndListeners().forEach(e -> e.handle(event));
			}
		});
		// 按流程执行处理器
		if (destination == WanderDestination.BLACKLIST) {
			wander(event, null, options.getCacheHandler(), null, options.getData());
		} else {
			step1Blacklist(event, options.getData());
		}

	}

	@Override
	public void failureHandler(RoutingContext failureHandler) {
		// 如果response处理异常并添加结束请求监听
		if (checkEnd(failureHandler)) {
			if (options.getFailureHandlerListeners() != null) {
				if (LOG.isDebugEnabled()) {
					LOG.debug("请求异常监听器->数量:" + options.getHandlerEndListeners().size());
				}
				options.getFailureHandlerListeners().forEach(e -> e.handle(failureHandler));
			}
		} else {
			OrionStatusResponse status = options.getResponse() == null
					? OrionApiDefaultResponse.FAILURE.data()
					: Optional.ofNullable(options.getResponse().getForbiddenResponse()).orElse(OrionApiDefaultResponse.FAILURE.data());
			endResponse(failureHandler, status);
			if (options.getFailureHandlerListeners() != null) {
				options.getFailureHandlerListeners().forEach(e -> e.handle(failureHandler));
			}
		}
	}

	/**
	 * 通用的结束响应
	 * 
	 * @param rct
	 * @param data
	 */
	private void endResponse(RoutingContext rct, OrionStatusResponse status) {
		if (checkEnd(rct)) {
			return;
		}
		if (status == null) {
			status = OrionApiDefaultResponse.FAILURE.data();
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("结束响应:" + status);
		}
		rct.response().putHeader(HttpHeaders.SERVER, OrionApiGatewayAttribute.NAME).putHeader(HttpHeaders.CONTENT_TYPE, status.getType())
				.putHeader(HttpHeaders.DATE, ZonedDateTime.now(ZoneOffset.UTC).format(DateTimeFormatter.RFC_1123_DATE_TIME))
				.setStatusCode(status.getCode()).setStatusMessage(status.getMsg());
		if (status.getData() != null) {
			rct.response().end(status.getData());
		} else {
			rct.response().end();
		}
	}

	/**
	 * 游行处理器
	 * 
	 * @param rct
	 * @param params
	 * @param cacheHandler
	 * @param response
	 * @param data
	 */
	private void wander(RoutingContext rct, OrionParameter params, OrionCacheHandler cacheHandler, HttpClientResponse response, Object data) {
		if (checkEnd(rct)) {
			return;
		} else {
			options.getWanderHandler().handle(rct, data, res -> {
				if (res.succeeded()) {
					if (LOG.isDebugEnabled()) {
						LOG.debug(String.format("API:%s->执行游行处理器-->完成", options.getName()));
					}
					wanderNext(rct, params, cacheHandler, response, res.result());
				} else {
					LOG.error(String.format("API:%s->执行游行处理器-->异常:", options.getName()), res.cause());
					failureHandler(rct);
				}
			});
		}
	}

	/**
	 * 游行处理器进入下一步
	 * 
	 * @param rct
	 * @param params
	 * @param cacheHandler
	 * @param response
	 * @param data
	 */
	private void wanderNext(RoutingContext rct, OrionParameter params, OrionCacheHandler cacheHandler, HttpClientResponse response,
			Object data) {
		if (checkEnd(rct)) {
			return;
		}
		switch (destination) {
			case BLACKLIST :
				step1Blacklist(rct, data);
				break;
			case ACCESS_LIMIT :
				step2AccessLimit(rct, data);
				break;
			case PARAMETER :
				step3ParameterLoadAndCheck(rct, data);
				break;
			case AUTHENTICATION :
				step4Authentication(rct, params, data);
				break;
			case BEFORE :
				step5Before(rct, params, data);
				break;
			case CACHE :
				step6Cache(rct, params, data);
				break;
			case MAIN :
				step7Main(rct, cacheHandler, params, data);
				break;
			case AFTER :
				step8After(rct, cacheHandler, response, data);
				break;
			case AFTER_END :
				if (!checkEnd(rct)) {
					Future<Buffer> future = response.body();
					future.onSuccess(res -> rct.response().end(res)).onFailure(err -> failureHandler(rct));
				}
				break;
		}

	}

	/**
	 * 第一步 黑名单检查
	 * 
	 * @param rct
	 * @param data
	 */
	private void step1Blacklist(RoutingContext rct, Object data) {
		if (checkEnd(rct)) {
			return;
		} else if (options.getBlacklistHandler() != null) {
			options.getBlacklistHandler().handle(rct, data, res -> {
				if (res.succeeded()) {
					if (LOG.isDebugEnabled()) {
						LOG.debug(String.format("API:%s->执行黑名单检查-->结果:通过!", options.getName()));
					}
					if (destination == WanderDestination.ACCESS_LIMIT) {
						wander(rct, null, null, null, res.result());
					} else {
						step2AccessLimit(rct, res.result());
					}
				} else {
					if (res.cause() instanceof OrionProcessEndException) {
						OrionStatusResponse status = options.getResponse() == null
								? OrionApiDefaultResponse.FORBIDDEN.data()
								: Optional.ofNullable(options.getResponse().getForbiddenResponse()).orElse(OrionApiDefaultResponse.FORBIDDEN.data());
						if (LOG.isDebugEnabled()) {
							LOG.debug(String.format("API:%s->执行黑名单检查-->结果:不通过", options.getName()));
						}
						endResponse(rct, status);
					} else {
						LOG.error(String.format("API:%s->执行黑名单检查-->异常:", options.getName()), res.cause());
						failureHandler(rct);
					}
				}
			});
		} else {
			if (destination == WanderDestination.ACCESS_LIMIT) {
				wander(rct, null, null, null, data);
			} else {
				step2AccessLimit(rct, data);
			}
		}
	}

	/**
	 * 第二步 访问限制
	 * 
	 * @param rct
	 * @param data
	 */
	private void step2AccessLimit(RoutingContext rct, Object data) {
		if (checkEnd(rct)) {
			return;
		} else if (options.getLimitHandler() != null) {
			options.getLimitHandler().handle(rct, data, res -> {
				if (res.succeeded()) {
					if (LOG.isDebugEnabled()) {
						LOG.debug(String.format("API:%s->执行访问限制-->结果:通过!", options.getName()));
					}
					if (destination == WanderDestination.PARAMETER) {
						wander(rct, null, null, null, res.result());
					} else {
						step3ParameterLoadAndCheck(rct, res.result());
					}
				} else {
					if (res.cause() instanceof OrionProcessEndException) {
						OrionStatusResponse status = options.getResponse() == null
								? OrionApiDefaultResponse.ACCESS_LIMIT.data()
								: Optional.ofNullable(options.getResponse().getForbiddenResponse()).orElse(OrionApiDefaultResponse.ACCESS_LIMIT.data());
						if (LOG.isDebugEnabled()) {
							LOG.debug(String.format("API:%s->执行访问限制-->结果:不通过!", options.getName()));
						}
						endResponse(rct, status);
					} else {
						LOG.error(String.format("API:%s->执行访问限制-->异常:", options.getName()), res.cause());
						failureHandler(rct);
					}
				}
			});
		} else {
			if (destination == WanderDestination.PARAMETER) {
				wander(rct, null, null, null, data);
			} else {
				step3ParameterLoadAndCheck(rct, data);
			}
		}
	}

	/**
	 * 第三步 参数检查与装载请求参数
	 * 
	 * @param rct
	 * @param data
	 */
	private void step3ParameterLoadAndCheck(RoutingContext rct, Object data) {
		if (checkEnd(rct)) {
			return;
		} else if (options.getParameterHandler() != null) {
			options.getParameterHandler().handle(rct, data, res -> {
				if (res.succeeded()) {
					if (LOG.isDebugEnabled()) {
						LOG.debug(String.format("API:%s->执行参数检查与装载-->结果:完成!", options.getName()));
					}
					if (destination == WanderDestination.AUTHENTICATION) {
						wander(rct, res.result(), null, null, data);
					} else {
						step4Authentication(rct, res.result(), data);
					}
				} else {
					if (res.cause() instanceof OrionProcessEndException) {
						OrionStatusResponse status = options.getResponse() == null
								? OrionApiDefaultResponse.BAD_REQUEST.data()
								: Optional.ofNullable(options.getResponse().getForbiddenResponse()).orElse(OrionApiDefaultResponse.BAD_REQUEST.data());
						if (LOG.isDebugEnabled()) {
							LOG.debug(String.format("API:%s->执行参数检查与装载-->结果:检查不通过!", options.getName()));
						}
						endResponse(rct, status);
					} else {
						LOG.error(String.format("API:%s->执行参数检查与装载-->异常:", options.getName()), res.cause());
						failureHandler(rct);
					}
				}
			});
		} else {
			if (destination == WanderDestination.AUTHENTICATION) {
				wander(rct, new OrionParameter(), null, null, data);
			} else {
				step4Authentication(rct, new OrionParameter(), data);
			}
		}
	}

	/**
	 * 第四步 执行权限认证
	 * 
	 * @param rct
	 * @param params
	 * @param data
	 */
	private void step4Authentication(RoutingContext rct, OrionParameter params, Object data) {
		if (checkEnd(rct)) {
			return;
		} else if (options.getAuthenticationHandler() != null) {
			options.getAuthenticationHandler().handle(rct, params, data, res -> {
				if (res.succeeded()) {
					if (LOG.isDebugEnabled()) {
						LOG.debug(String.format("API:%s->执行权限认证-->结果:通过!", options.getName()));
					}
					if (destination == WanderDestination.BEFORE) {
						wander(rct, params, null, null, res.result());
					} else {
						step5Before(rct, params, res.result());
					}
				} else {
					if (res.cause() instanceof OrionProcessEndException) {
						OrionStatusResponse status = options.getResponse() == null
								? OrionApiDefaultResponse.FORBIDDEN.data()
								: Optional.ofNullable(options.getResponse().getForbiddenResponse()).orElse(OrionApiDefaultResponse.FORBIDDEN.data());
						if (LOG.isDebugEnabled()) {
							LOG.debug(String.format("API:%s->执行权限认证-->结果:不通过!", options.getName()));
						}
						endResponse(rct, status);
					} else {
						LOG.error(String.format("API:%s->执行权限认证-->异常:", options.getName()), res.cause());
						failureHandler(rct);
					}
				}
			});
		} else {
			if (destination == WanderDestination.BEFORE) {
				wander(rct, params, null, null, data);
			} else {
				step5Before(rct, params, data);
			}
		}
	}

	/**
	 * 第五步 前置处理器
	 * 
	 * @param rct
	 * @param params
	 * @param data
	 */
	private void step5Before(RoutingContext rct, OrionParameter params, Object data) {
		if (checkEnd(rct)) {
			return;
		} else if (options.getBeforeHandler() != null) {
			options.getBeforeHandler().handle(rct, params, data, res -> {
				if (res.succeeded()) {
					if (LOG.isDebugEnabled()) {
						LOG.debug(String.format("API:%s->执行前置处理器-->结果:成功!", options.getName()));
					}
					if (destination == WanderDestination.CACHE) {
						wander(rct, params, null, null, res.result());
					} else {
						step6Cache(rct, params, res.result());
					}
				} else {
					LOG.error(String.format("API:%s->执行前置处理器-->异常:", options.getName()), res.cause());
					failureHandler(rct);
				}
			});
		} else {
			if (destination == WanderDestination.CACHE) {
				wander(rct, params, null, null, data);
			} else {
				step6Cache(rct, params, data);
			}
		}
	}

	/**
	 * 第六步 缓存处理器
	 * 
	 * @param rct
	 * @param params
	 * @param data
	 */
	private void step6Cache(RoutingContext rct, OrionParameter params, Object data) {
		if (checkEnd(rct)) {
			return;
		} else if (options.getCacheHandler() != null) {
			options.getCacheHandler().handle(rct, params, data, res -> {
				if (res.succeeded()) {
					if (LOG.isDebugEnabled()) {
						LOG.debug(String.format("API:%s->执行缓存处理器-->结果:成功!", options.getName()));
					}
					if (destination == WanderDestination.MAIN) {
						wander(rct, params, options.getCacheHandler(), null, res.result());
					} else {
						step7Main(rct, options.getCacheHandler(), params, res.result());
					}
				} else {
					LOG.error(String.format("API:%s->执行缓存处理器-->异常:", options.getName()), res.cause());
					failureHandler(rct);
				}
			});
		} else {
			if (destination == WanderDestination.MAIN) {
				wander(rct, params, null, null, data);
			} else {
				step7Main(rct, null, params, data);
			}
		}
	}

	/**
	 * 第七步 中心处理器
	 * 
	 * @param rct
	 * @param params
	 * @param data
	 */
	private void step7Main(RoutingContext rct, OrionCacheHandler cacheHandler, OrionParameter params, Object data) {
		if (checkEnd(rct)) {
			return;
		} else if (options.getMainHandler() != null) {
			options.getMainHandler().handle(rct, cacheHandler, params, data, res -> {
				if (res.succeeded()) {
					if (LOG.isDebugEnabled()) {
						LOG.debug(String.format("API:%s->执行中心处理器-->结果:成功!", options.getName()));
					}
					if (destination == WanderDestination.AFTER) {
						wander(rct, params, cacheHandler, res.result(), data);
					} else {
						step8After(rct, cacheHandler, res.result(), data);
					}
				} else {
					LOG.error(String.format("API:%s->执行中心处理器-->异常:", options.getName()), res.cause());
					failureHandler(rct);
				}
			});
		} else {
			LOG.error(String.format("API:%s->执行中心处理器-->异常:不存在中心处理器", options.getName()));
			failureHandler(rct);
		}
	}

	/**
	 * 第八步 后置处理器
	 * 
	 * @param rct
	 * @param params
	 * @param data
	 */
	private void step8After(RoutingContext rct, OrionCacheHandler cacheHandler, HttpClientResponse response, Object data) {
		if (checkEnd(rct)) {
			return;
		} else if (options.getAfterHandler() != null) {
			options.getAfterHandler().handle(rct, cacheHandler, response, data, res -> {
				if (res.succeeded()) {
					if (LOG.isDebugEnabled()) {
						LOG.debug(String.format("API:%s->执行后置处理器-->结果:成功!", options.getName()));
					}
					if (destination == WanderDestination.AFTER_END) {
						wander(rct, null, cacheHandler, response, res.result());
					} else {
						if (!rct.response().ended() && !rct.response().closed()) {
							if (res.result() == null) {
								rct.response().end();
							} else {
								rct.response().end(res.result());
							}
						}
					}
				} else {
					LOG.error(String.format("API:%s->执行后置处理器-->异常:", options.getName()), res.cause());
					failureHandler(rct);
				}
			});
		} else {
			if (destination == WanderDestination.AFTER_END) {
				wander(rct, null, cacheHandler, response, data);
			} else {
				if (!checkEnd(rct)) {
					Future<Buffer> future = response.body();
					future.onSuccess(res -> rct.response().end(res)).onFailure(err -> failureHandler(rct));
				}
			}
		}
	}

	/**
	 * 检查response是否已经响应或关闭了
	 * 
	 * @return
	 */
	private boolean checkEnd(RoutingContext rct) {
		if (rct.response().ended() || rct.response().closed()) {
			return true;
		}
		return false;
	}

}

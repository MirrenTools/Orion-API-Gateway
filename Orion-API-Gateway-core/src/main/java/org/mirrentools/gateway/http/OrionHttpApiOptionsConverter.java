package org.mirrentools.gateway.http;

import io.vertx.core.json.JsonObject;
import io.vertx.core.spi.json.JsonCodec;

/**
 * HttpApiOptions转换器
 * 
 * @TODO 等待实现
 * @author <a href="https://mirrentools.org/">Mirren</a>
 * @version 1.0.0
 */
public class OrionHttpApiOptionsConverter implements JsonCodec<OrionHttpApiOptions, JsonObject> {
	@Override
	public JsonObject encode(OrionHttpApiOptions value) {
		return (value != null) ? value.toJson() : null;
	}

	@Override
	public OrionHttpApiOptions decode(JsonObject value) {
		return (value != null) ? new OrionHttpApiOptions(value) : null;
	}

	@Override
	public Class<OrionHttpApiOptions> getTargetClass() {
		return OrionHttpApiOptions.class;
	}

	/**
	 * 通过JsonObject初始化
	 * 
	 * @param json
	 * @param obj
	 */
	static void fromJson(Iterable<java.util.Map.Entry<String, Object>> json, OrionHttpApiOptions obj) {
		// TODO 待实现
	}

	/**
	 * 将类装换为JsonObject
	 * 
	 * @param obj
	 * @param json
	 */
	static void toJson(OrionHttpApiOptions obj, JsonObject json) {
		toJson(obj, json.getMap());
	}

	/**
	 * 将类装换为JsonObject
	 * 
	 * @param obj
	 * @param json
	 */
	static void toJson(OrionHttpApiOptions obj, java.util.Map<String, Object> json) {
		// TODO 待实现
	}
}

package org.mirrentools.gateway.http;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.DecodeException;
import io.vertx.core.json.EncodeException;
import io.vertx.core.json.JsonObject;
import io.vertx.core.spi.json.JsonCodec;

/**
 * HttpApiOptions转换器
 * 
 * @TODO 等待实现
 * @author <a href="https://mirrentools.org/">Mirren</a>
 * @version 1.0.0
 */
public class OrionHttpApiOptionsConverter implements JsonCodec {


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

	@Override
	public <T> T fromString(String json, Class<T> clazz) throws DecodeException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T fromBuffer(Buffer json, Class<T> clazz) throws DecodeException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T fromValue(Object json, Class<T> toValueType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String toString(Object object, boolean pretty) throws EncodeException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Buffer toBuffer(Object object, boolean pretty) throws EncodeException {
		// TODO Auto-generated method stub
		return null;
	}
}

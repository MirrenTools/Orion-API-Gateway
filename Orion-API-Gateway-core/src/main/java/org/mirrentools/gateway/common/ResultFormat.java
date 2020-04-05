package org.mirrentools.gateway.common;

import java.util.List;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

/**
 * 将StatusCodeMsg转换为返回json<br>
 * 返回结果<br>
 * code : 状态码<br>
 * msg : 信息<br>
 * data : 数据<br>
 * 
 * @author <a href="http://szmirren.com">Mirren</a>
 *
 */
public class ResultFormat {
	/**
	 * 格式化返回结果,code为状态码枚举类,data为数据
	 * 
	 * @param code
	 * @param data
	 * @return
	 */
	public static JsonObject format(ResultCodeEnum code, Object data) {
		JsonObject json = new JsonObject();
		json.put("code", code.getCode());
		json.put("msg", code.getMsg());
		if (data != null) {
			json.put("data", data);
		} else {
			json.putNull("data");
		}
		return json;
	}
	/**
	 * 格式化返回结果,code为状态码枚,msg提示信息,data为数据
	 * 
	 * @param code
	 * @param msg
	 * @param data
	 * @return
	 */
	public static JsonObject format(int code, String msg, Object data) {
		JsonObject result = new JsonObject();
		result.put("code", code);
		result.put("msg", msg);
		if (data != null) {
			result.put("data", data);
		} else {
			result.putNull("data");
		}
		return result;
	}
	/**
	 * 格式化返回结果,code为状态码枚举类,data为数据
	 * 
	 * @param code
	 * @param data
	 * @return
	 */
	public static JsonObject format(ResultCodeEnum code, JsonArray data) {
		JsonObject result = new JsonObject();
		result.put("code", code.getCode());
		result.put("msg", code.getMsg());
		if (data == null) {
			data = new JsonArray();
		}
		result.put("data", data);
		return result;
	}
	/**
	 * 格式化返回结果
	 * 
	 * @param code
	 *          状态码
	 * @param msg
	 *          提示信息
	 * @param data
	 *          结果数据
	 * @return
	 */
	public static JsonObject format(int code, String msg, JsonArray data) {
		JsonObject result = new JsonObject();
		result.put("code", code);
		result.put("msg", msg);
		if (data == null) {
			data = new JsonArray();
		}
		result.put("data", data);
		return result;
	}
	/**
	 * 格式化返回结果,code为状态码枚举类,data为数据
	 * 
	 * @param code
	 * @param data
	 * @return
	 */
	public static JsonObject format(ResultCodeEnum code, List<JsonObject> data) {
		JsonObject result = new JsonObject();
		result.put("code", code.getCode());
		result.put("msg", code.getMsg());
		if (data == null) {
			result.put("data", new JsonArray());
		} else {
			result.put("data", data);
		}
		return result;
	}
	/**
	 * 格式化返回结果
	 * 
	 * @param code
	 *          状态码
	 * @param msg
	 *          提示信息
	 * @param data
	 *          结果数据
	 * @return
	 */
	public static JsonObject format(int code, String msg, List<JsonObject> data) {
		JsonObject result = new JsonObject();
		result.put("code", code);
		result.put("msg", msg);
		if (data == null) {
			result.put("data", new JsonArray());
		} else {
			result.put("data", data);
		}
		return result;
	}
	/**
	 * 格式化返回结果,code为状态码枚举类,data为数据
	 * 
	 * @param code
	 * @param data
	 * @return
	 */
	public static JsonObject format(ResultCodeEnum code, JsonObject data) {
		JsonObject result = new JsonObject();
		result.put("code", code.getCode());
		result.put("msg", code.getMsg());
		if (data == null) {
			data = new JsonObject();
		}
		result.put("data", data);
		return result;
	}
	/**
	 * 格式化返回结果
	 * 
	 * @param code
	 *          状态码
	 * @param msg
	 *          提示信息
	 * @param data
	 *          结果数据
	 * @return
	 */
	public static JsonObject format(int code, String msg, JsonObject data) {
		JsonObject result = new JsonObject();
		result.put("code", code);
		result.put("msg", msg);
		if (data == null) {
			data = new JsonObject();
		}
		result.put("data", data);
		return result;
	}

	/**
	 * 格式化返回结果其中data为null,code为状态码枚举类
	 * 
	 * @param code
	 * @return
	 */
	public static JsonObject formatAsNull(ResultCodeEnum code) {
		JsonObject result = new JsonObject();
		result.put("code", code.getCode());
		result.put("msg", code.getMsg());
		result.putNull("data");
		return result;
	}
	/**
	 * 格式化返回结果其中data为null
	 * 
	 * @param code
	 *          状态码
	 * @param msg
	 *          提示信息
	 * @return
	 */
	public static JsonObject formatAsNull(int code, String msg) {
		JsonObject result = new JsonObject();
		result.put("code", code);
		result.put("msg", msg);
		result.putNull("data");
		return result;
	}

	/**
	 * 格式化返回结果其中data为{},code为状态码枚举类
	 * 
	 * @param code
	 * @return
	 */
	public static JsonObject formatAsNewJson(ResultCodeEnum code) {
		JsonObject result = new JsonObject();
		result.put("code", code.getCode());
		result.put("msg", code.getMsg());
		result.put("data", new JsonObject());
		return result;
	}
	/**
	 * 格式化返回结果其中data为{}
	 * 
	 * @param code
	 *          状态码
	 * @param msg
	 *          提示信息
	 * @return
	 */
	public static JsonObject formatAsNewJson(int code, String msg) {
		JsonObject result = new JsonObject();
		result.put("code", code);
		result.put("msg", msg);
		result.put("data", new JsonObject());
		return result;
	}

	/**
	 * 格式化返回结果其中data为[],code为状态码枚举类
	 * 
	 * @param code
	 * @return
	 */
	public static JsonObject formatAsNewArray(ResultCodeEnum code) {
		JsonObject result = new JsonObject();
		result.put("code", code.getCode());
		result.put("msg", code.getMsg());
		result.put("data", new JsonArray());
		return result;
	}
	/**
	 * 格式化返回结果其中data为[]
	 * 
	 * @param code
	 *          状态码
	 * @param msg
	 *          提示信息
	 * @return
	 */
	public static JsonObject formatAsNewArray(int code, String msg) {
		JsonObject result = new JsonObject();
		result.put("code", code);
		result.put("msg", msg);
		result.put("data", new JsonArray());
		return result;
	}

	/**
	 * 格式化返回结果其中data为0,code为状态码枚举类
	 * 
	 * @param code
	 * @return
	 */
	public static JsonObject formatAsZero(ResultCodeEnum code) {
		JsonObject result = new JsonObject();
		result.put("code", code.getCode());
		result.put("msg", code.getMsg());
		result.put("data", 0);
		return result;
	}
	/**
	 * 格式化返回结果其中data为0
	 * 
	 * @param code
	 *          状态码
	 * @param msg
	 *          提示信息
	 * @return
	 */
	public static JsonObject formatAsZero(int code, String msg) {
		JsonObject result = new JsonObject();
		result.put("code", code);
		result.put("msg", msg);
		result.put("data", 0);
		return result;
	}

	/**
	 * 格式化返回结果其中data为1,code为状态码枚举类
	 * 
	 * @param code
	 * @return
	 */
	public static JsonObject formatAsOne(ResultCodeEnum code) {
		JsonObject result = new JsonObject();
		result.put("code", code.getCode());
		result.put("msg", code.getMsg());
		result.put("data", 1);
		return result;
	}
	/**
	 * 格式化返回结果其中data为1
	 * 
	 * @param code
	 *          状态码
	 * @param msg
	 *          提示信息
	 * @return
	 */
	public static JsonObject formatAsOne(int code, String msg) {
		JsonObject result = new JsonObject();
		result.put("code", code);
		result.put("msg", msg);
		result.put("data", 1);
		return result;
	}
	/**
	 * 格式化返回结果其中data为"",code为状态码枚举类
	 * 
	 * @param code
	 * @return
	 */
	public static JsonObject formatAsEmpty(ResultCodeEnum code) {
		JsonObject result = new JsonObject();
		result.put("code", code.getCode());
		result.put("msg", code.getMsg());
		result.put("data", "");
		return result;
	}
	/**
	 * 格式化返回结果其中data为""
	 * 
	 * @param code
	 *          状态码
	 * @param msg
	 *          提示信息
	 * @return
	 */
	public static JsonObject formatAsEmpty(int code, String msg) {
		JsonObject result = new JsonObject();
		result.put("code", code);
		result.put("msg", msg);
		result.put("data", "");
		return result;
	}
}

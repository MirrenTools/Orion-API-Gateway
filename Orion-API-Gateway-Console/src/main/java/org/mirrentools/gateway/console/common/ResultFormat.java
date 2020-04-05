package org.mirrentools.gateway.console.common;

import java.util.List;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

/**
 * 将结果处理结果转换为返回json<br>
 * 返回结果<br>
 * code : 状态码<br>
 * msg : 显性描述信息<br>
 * explain : 隐性描述信息<br>
 * data : 数据<br>
 * 
 * @author <a href="http://szmirren.com">Mirren</a>
 *
 */
public class ResultFormat {
	/**
	 * 格式化返回结果
	 * 
	 * @param code
	 *          状态码枚举类
	 * @param data
	 *          数据,如果data为null则data=null
	 * @return
	 */
	public static JsonObject format(ResultCodeEnum code, Object data) {
		JsonObject result = new JsonObject();
		result.put("code", code.getCode());
		result.put("msg", code.getMsg());
		result.put("explain", code.getExplain());
		if (data != null) {
			result.put("data", data);
		} else {
			result.putNull("data");
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
	 * @param explain
	 *          状态信息
	 * @param data
	 *          数据,如果data为null则data=null
	 * @return
	 */
	public static JsonObject format(int code, String msg, String explain, Object data) {
		JsonObject result = new JsonObject();
		result.put("code", code);
		result.put("msg", msg);
		result.put("explain", explain);
		if (data != null) {
			result.put("data", data);
		} else {
			result.putNull("data");
		}
		return result;
	}

	/**
	 * 格式化返回结果
	 * 
	 * @param code
	 *          状态码枚举类
	 * @param data
	 *          数据,如果data为null则data=null
	 * @return
	 */
	public static JsonObject format(ResultCodeEnum code, JsonArray data) {
		JsonObject result = new JsonObject();
		result.put("code", code.getCode());
		result.put("msg", code.getMsg());
		result.put("explain", code.getExplain());
		if (data == null) {
			result.putNull("data");
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
	 * @param explain
	 *          状态信息
	 * @param data
	 *          数据,如果data为null则data=null
	 * @return
	 */
	public static JsonObject format(int code, String msg, String explain, JsonArray data) {
		JsonObject result = new JsonObject();
		result.put("code", code);
		result.put("msg", msg);
		result.put("explain", explain);
		if (data == null) {
			result.putNull("data");
		} else {
			result.put("data", data);
		}
		return result;
	}

	/**
	 * 格式化返回结果
	 * 
	 * @param code
	 *          状态码枚举类
	 * @param data
	 *          数据,如果data为null则data=null
	 * @return
	 */
	public static JsonObject format(ResultCodeEnum code, List<JsonObject> data) {
		JsonObject result = new JsonObject();
		result.put("code", code.getCode());
		result.put("msg", code.getMsg());
		result.put("explain", code.getExplain());
		if (data == null) {
			result.putNull("data");
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
	 * @param explain
	 *          状态信息
	 * @param data
	 *          数据,如果data为null则data=null
	 * @return
	 */
	public static JsonObject format(int code, String msg, String explain, List<JsonObject> data) {
		JsonObject result = new JsonObject();
		result.put("code", code);
		result.put("msg", msg);
		result.put("explain", explain);
		if (data == null) {
			result.putNull("data");
		} else {
			result.put("data", data);
		}
		return result;
	}

	/**
	 * 格式化返回结果
	 * 
	 * @param code
	 *          状态码枚举类
	 * @param data
	 *          数据,如果data为null则data=null
	 * @return
	 */
	public static JsonObject format(ResultCodeEnum code, JsonObject data) {
		JsonObject result = new JsonObject();
		result.put("code", code.getCode());
		result.put("msg", code.getMsg());
		result.put("explain", code.getExplain());
		if (data == null) {
			result.putNull("data");
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
	 * @param explain
	 *          状态信息
	 * @param data
	 *          数据,如果data为null则data=null
	 * @return
	 */
	public static JsonObject format(int code, String msg, String explain, JsonObject data) {
		JsonObject result = new JsonObject();
		result.put("code", code);
		result.put("msg", msg);
		result.put("explain", explain);
		if (data == null) {
			result.putNull("data");
		} else {
			result.put("data", data);
		}
		return result;
	}

	/**
	 * 格式化返回结果其中data为null
	 * 
	 * @param code
	 *          状态码枚举类
	 * @return
	 */
	public static JsonObject formatAsNull(ResultCodeEnum code) {
		JsonObject result = new JsonObject();
		result.put("code", code.getCode());
		result.put("msg", code.getMsg());
		result.put("explain", code.getExplain());
		result.putNull("data");
		return result;
	}

	/**
	 * 格式化返回结果其中data为null
	 * 
	 * @param code
	 *          状态码
	 * @return
	 */
	public static JsonObject formatAsNull(int code, String msg) {
		JsonObject result = new JsonObject();
		result.put("code", code);
		result.put("msg", msg);
		result.put("explain", msg);
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
	public static JsonObject formatAsNull(int code, String msg, String explain) {
		JsonObject result = new JsonObject();
		result.put("code", code);
		result.put("msg", msg);
		result.put("explain", explain);
		result.putNull("data");
		return result;
	}

	/**
	 * 格式化返回结果其中data为{}
	 * 
	 * @param code
	 *          状态码枚举类
	 * @return
	 */
	public static JsonObject formatAsNewJson(ResultCodeEnum code) {
		JsonObject result = new JsonObject();
		result.put("code", code.getCode());
		result.put("msg", code.getMsg());
		result.put("explain", code.getExplain());
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
	 * @param explain
	 *          状态信息
	 * @return
	 */
	public static JsonObject formatAsNewJson(int code, String msg, String explain) {
		JsonObject result = new JsonObject();
		result.put("code", code);
		result.put("msg", msg);
		result.put("explain", explain);
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
		result.put("explain", code.getExplain());
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
	 * @param explain
	 *          状态信息
	 * @return
	 */
	public static JsonObject formatAsNewArray(int code, String msg, String explain) {
		JsonObject result = new JsonObject();
		result.put("code", code);
		result.put("msg", msg);
		result.put("explain", explain);
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
		result.put("explain", code.getExplain());
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
	 * @param explain
	 *          状态信息
	 * @return
	 */
	public static JsonObject formatAsZero(int code, String msg, String explain) {
		JsonObject result = new JsonObject();
		result.put("code", code);
		result.put("msg", msg);
		result.put("explain", explain);
		result.put("data", 0);
		return result;
	}

	/**
	 * 格式化返回结果其中data为1
	 * 
	 * @param code
	 *          状态码枚举类
	 * @return
	 */
	public static JsonObject formatAsOne(ResultCodeEnum code) {
		JsonObject result = new JsonObject();
		result.put("code", code.getCode());
		result.put("msg", code.getMsg());
		result.put("explain", code.getExplain());
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
	 * @param explain
	 *          状态信息
	 * @return
	 */
	public static JsonObject formatAsOne(int code, String msg, String explain) {
		JsonObject result = new JsonObject();
		result.put("code", code);
		result.put("msg", msg);
		result.put("explain", explain);
		result.put("data", 1);
		return result;
	}

	/**
	 * 格式化返回结果其中data为""
	 * 
	 * @param code
	 *          状态码枚举类
	 * @return
	 */
	public static JsonObject formatAsEmpty(ResultCodeEnum code) {
		JsonObject result = new JsonObject();
		result.put("code", code.getCode());
		result.put("msg", code.getMsg());
		result.put("explain", code.getExplain());
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
	 * @param explain
	 *          状态信息
	 * @return
	 */
	public static JsonObject formatAsEmpty(int code, String msg, String explain) {
		JsonObject result = new JsonObject();
		result.put("code", code);
		result.put("msg", msg);
		result.put("explain", explain);
		result.put("data", "");
		return result;
	}
}

package org.mirrentools.gateway.console.common;

import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;

/**
 * futrue的工具类
 * 
 * @author <a href="http://szmirren.com">Mirren</a>
 *
 */
public class FutureUtil {
	/**
	 * 得到一个成功的自定义Futrue
	 * 
	 * @param result
	 * @return
	 */
	public static Future<JsonObject> success(JsonObject result) {
		return Future.succeededFuture(result);
	}
	/**
	 * 得到一个成功结果为0的自定义Futrue
	 * 
	 * @param code
	 *          状态码
	 * @return
	 */
	public static Future<JsonObject> successZero(ResultCodeEnum code) {
		return Future.succeededFuture(ResultFormat.formatAsZero(code));
	}
	/**
	 * 得到一个成功结果为0的自定义Futrue
	 * 
	 * @param code
	 *          状态码
	 * @param msg
	 *          描述信息
	 * @return
	 */
	public static Future<JsonObject> successZero(int code, String msg) {
		return Future.succeededFuture(ResultFormat.formatAsZero(code, msg));
	}
	/**
	 * 得到一个成功结果为1的自定义Futrue
	 * 
	 * @param code
	 *          状态码
	 * @return
	 */
	public static Future<JsonObject> successOne(ResultCodeEnum code) {
		return Future.succeededFuture(ResultFormat.formatAsOne(code));
	}

	/**
	 * 得到一个成功结果为newJson的自定义Futrue
	 * 
	 * @param code
	 *          状态码
	 * @return
	 */
	public static Future<JsonObject> successNewJson(ResultCodeEnum code) {
		return Future.succeededFuture(ResultFormat.formatAsNewJson(code));
	}
	/**
	 * 得到一个成功结果为newJson的自定义Futrue
	 * 
	 * @param code
	 *          状态码
	 * @param msg
	 *          提示信息
	 * @return
	 */
	public static Future<JsonObject> successNewJson(int code, String msg) {
		return Future.succeededFuture(ResultFormat.formatAsNewJson(code, msg));
	}
	/**
	 * 得到一个成功结果为newArray的自定义Futrue
	 * 
	 * @param code
	 *          状态码
	 * @return
	 */
	public static Future<JsonObject> successNewArray(ResultCodeEnum code) {
		return Future.succeededFuture(ResultFormat.formatAsNewArray(code));
	}
	/**
	 * 得到一个成功结果为newArray的自定义Futrue
	 * 
	 * @param code
	 *          状态码
	 * @param msg
	 *          提示信息
	 * @return
	 */
	public static Future<JsonObject> successNewArray(int code, String msg) {
		return Future.succeededFuture(ResultFormat.formatAsNewArray(code, msg));
	}

	/**
	 * 得到一个成功的Future,状态为200
	 * 
	 * @param data
	 *          数据
	 * @return
	 */
	public static Future<JsonObject> successC200(Object data) {
		return Future.succeededFuture(ResultFormat.format(ResultCodeEnum.C200, data));
	}
	/**
	 * 得到一个成功的Future,状态为200,data为0
	 * 
	 * @return
	 */
	public static Future<JsonObject> successC200Zero() {
		return Future.succeededFuture(ResultFormat.formatAsZero(ResultCodeEnum.C200));
	}

	/**
	 * 得到一个成功的Future,状态为200,data为0
	 * 
	 * @param msg
	 *          提示信息
	 * @return
	 */
	public static Future<JsonObject> successC200Zero(String msg) {
		return Future.succeededFuture(ResultFormat.formatAsZero(ResultCode.C200, msg));
	}
	/**
	 * 得到一个成功的Future,状态为200,data为1
	 * 
	 * @return
	 */
	public static Future<JsonObject> successC200One() {
		return Future.succeededFuture(ResultFormat.formatAsOne(ResultCodeEnum.C200));
	}
	/**
	 * 得到一个成功的Future,状态为200,data为1
	 * 
	 * @return
	 */
	public static Future<JsonObject> successC200One(String msg) {
		return Future.succeededFuture(ResultFormat.formatAsOne(ResultCode.C200, msg));
	}
	/**
	 * 得到一个成功的Future,状态为200,data为new JsonObject
	 * 
	 * @return
	 */
	public static Future<JsonObject> successC200NewJson() {
		return Future.succeededFuture(ResultFormat.formatAsNewJson(ResultCodeEnum.C200));
	}
	/**
	 * 
	 * 得到一个成功的Future,状态为200,data为new JsonObject
	 * 
	 * @param msg
	 *          状态信息
	 * @return
	 */
	public static Future<JsonObject> successC200NewJson(String msg) {
		return Future.succeededFuture(ResultFormat.formatAsNewJson(ResultCode.C200, msg));
	}

	/**
	 * 得到一个成功的Future,状态为200,data为new JsonArray
	 * 
	 * @return
	 */
	public static Future<JsonObject> successC200NewArray() {
		return Future.succeededFuture(ResultFormat.formatAsNewArray(ResultCodeEnum.C200));
	}

	/**
	 * 得到一个成功的Future,状态为200,data为null
	 * 
	 * @return
	 */
	public static Future<JsonObject> successC200Null() {
		return Future.succeededFuture(ResultFormat.formatAsNull(ResultCodeEnum.C200));
	}
	/**
	 * 得到一个成功的Future,状态为402,data为0
	 * 
	 * @return
	 */
	public static Future<JsonObject> successC402Zero() {
		return Future.succeededFuture(ResultFormat.formatAsZero(ResultCodeEnum.C402));
	}

	/**
	 * 得到一个成功的Future,状态为402,data为0
	 * 
	 * @param msg
	 *          提示信息
	 * @return
	 */
	public static Future<JsonObject> successC402Zero(String msg) {
		return Future.succeededFuture(ResultFormat.formatAsZero(ResultCode.C402, msg));
	}

	/**
	 * 得到一个成功的Future,状态为402,data为null
	 * 
	 * @return
	 */
	public static Future<JsonObject> successC402Null() {
		return Future.succeededFuture(ResultFormat.formatAsNull(ResultCodeEnum.C402));
	}
	/**
	 * 得到一个成功的Future,状态为402,data为null
	 * 
	 * @return
	 */
	public static Future<JsonObject> successC402Null(String msg) {
		return Future.succeededFuture(ResultFormat.formatAsNull(ResultCode.C402, msg));
	}
	/**
	 * 得到一个成功的Future,状态为412,data为null
	 * 
	 * @return
	 */
	public static Future<JsonObject> successC412Null() {
		return Future.succeededFuture(ResultFormat.formatAsNull(ResultCodeEnum.C412));
	}
	/**
	 * 得到一个成功的Future,状态为412,data为0
	 * 
	 * @return
	 */
	public static Future<JsonObject> successC412Zero() {
		return Future.succeededFuture(ResultFormat.formatAsZero(ResultCodeEnum.C412));
	}

	/**
	 * 得到一个成功的Future,状态为412,data为0
	 * 
	 * @param msg
	 *          状态信息
	 * @return
	 */
	public static Future<JsonObject> successC412Zero(String msg) {
		return Future.succeededFuture(ResultFormat.formatAsZero(ResultCode.C412, msg));
	}
	/**
	 * 得到一个成功的Future,状态为412,data为1
	 * 
	 * @return
	 */
	public static Future<JsonObject> successC412One() {
		return Future.succeededFuture(ResultFormat.formatAsOne(ResultCodeEnum.C412));
	}
	/**
	 * 得到一个成功的Future,状态为412,data为new JsonObject
	 * 
	 * @return
	 */
	public static Future<JsonObject> successC412NewJson() {
		return Future.succeededFuture(ResultFormat.formatAsNewJson(ResultCodeEnum.C412));
	}

	/**
	 * 得到一个成功的Future,状态为412,data为new JsonArray
	 * 
	 * @return
	 */
	public static Future<JsonObject> successC412NewArray() {
		return Future.succeededFuture(ResultFormat.formatAsNewArray(ResultCodeEnum.C412));
	}

	/**
	 * 得到一个成功的Future,状态为413,data为null
	 * 
	 * @return
	 */
	public static Future<JsonObject> successC413Null() {
		return Future.succeededFuture(ResultFormat.formatAsNull(ResultCodeEnum.C413));
	}
	/**
	 * 得到一个成功的Future,状态为413,data为0
	 * 
	 * @return
	 */
	public static Future<JsonObject> successC413Zero() {
		return Future.succeededFuture(ResultFormat.formatAsZero(ResultCodeEnum.C413));
	}
	/**
	 * 得到一个成功的Future,状态为413,data为0
	 * 
	 * @param msg
	 *          提示信息
	 * @return
	 */
	public static Future<JsonObject> successC413Zero(String msg) {
		return Future.succeededFuture(ResultFormat.formatAsZero(ResultCode.C500, msg));
	}
	/**
	 * 得到一个成功的Future,状态为413,data为1
	 * 
	 * @return
	 */
	public static Future<JsonObject> successC413One() {
		return Future.succeededFuture(ResultFormat.formatAsOne(ResultCodeEnum.C413));
	}
	/**
	 * 得到一个成功的Future,状态为413,data为new JsonObject
	 * 
	 * @return
	 */
	public static Future<JsonObject> successC413NewJson() {
		return Future.succeededFuture(ResultFormat.formatAsNewJson(ResultCodeEnum.C413));
	}
	/**
	 * 得到一个成功的Future,状态为413,data为new JsonObject
	 * 
	 * @return
	 */
	public static Future<JsonObject> successC413NewJson(String msg) {
		return Future.succeededFuture(ResultFormat.formatAsNewJson(ResultCode.C413, msg));
	}

	/**
	 * 得到一个成功的Future,状态为413,data为new JsonArray
	 * 
	 * @return
	 */
	public static Future<JsonObject> successC413NewArray() {
		return Future.succeededFuture(ResultFormat.formatAsNewArray(ResultCodeEnum.C413));
	}

	/**
	 * 得到一个成功的Future,状态为413,data为new JsonArray
	 * 
	 * @param msg
	 *          提示信息
	 * @return
	 */
	public static Future<JsonObject> successC413NewArray(String msg) {
		return Future.succeededFuture(ResultFormat.formatAsNewArray(ResultCode.C413, msg));
	}

	/**
	 * 得到一个成功的Future,状态为1006,data为null
	 * 
	 * @return
	 */
	public static Future<JsonObject> successC1006Null() {
		return Future.succeededFuture(ResultFormat.formatAsNull(ResultCodeEnum.C1006));
	}
	/**
	 * 得到一个成功的Future,状态为1006,data为0
	 * 
	 * @return
	 */
	public static Future<JsonObject> successC1006Zero() {
		return Future.succeededFuture(ResultFormat.formatAsZero(ResultCodeEnum.C1006));
	}
	/**
	 * 得到一个成功的Future,状态为1006,data为1
	 * 
	 * @return
	 */
	public static Future<JsonObject> successC1006One() {
		return Future.succeededFuture(ResultFormat.formatAsOne(ResultCodeEnum.C1006));
	}
	/**
	 * 得到一个成功的Future,状态为1006,data为new JsonObject
	 * 
	 * @return
	 */
	public static Future<JsonObject> successC1006NewJson() {
		return Future.succeededFuture(ResultFormat.formatAsNewJson(ResultCodeEnum.C1006));
	}

	/**
	 * 得到一个成功的Future,状态为1006,data为new JsonArray
	 * 
	 * @return
	 */
	public static Future<JsonObject> successC1006NewArray() {
		return Future.succeededFuture(ResultFormat.formatAsNewArray(ResultCodeEnum.C1006));
	}

	/**
	 * 得到一个成功的Future,状态为500,data为0
	 * 
	 * @return
	 */
	public static Future<JsonObject> successC500Zero() {
		return Future.succeededFuture(ResultFormat.formatAsZero(ResultCodeEnum.C500));
	}

	/**
	 * 得到一个成功的Future,状态为500,data为0
	 * 
	 * @param msg
	 *          提示信息
	 * @return
	 */
	public static Future<JsonObject> successC500Zero(String msg) {
		return Future.succeededFuture(ResultFormat.formatAsZero(ResultCode.C500, msg));
	}
	/**
	 * 得到一个成功的Future,状态为500,data为{}
	 * 
	 * @param msg
	 *          提示信息
	 * @return
	 */
	public static Future<JsonObject> successC500NewJson(String msg) {
		return Future.succeededFuture(ResultFormat.formatAsNewJson(ResultCode.C500, msg));
	}
	/**
	 * 得到一个成功的Future,状态为500,data为[]
	 * 
	 * @param msg
	 *          提示信息
	 * @return
	 */
	public static Future<JsonObject> successC500NewArray(String msg) {
		return Future.succeededFuture(ResultFormat.formatAsNewArray(ResultCode.C500, msg));
	}
	/**
	 * 得到一个成功的Future,状态为500,data为0
	 * 
	 * @param data
	 *          数据
	 * @return
	 */
	public static Future<JsonObject> successC500Data(Object data) {
		return Future.succeededFuture(ResultFormat.format(ResultCode.C500, ResultCode.M500, data));
	}
	/**
	 * 得到一个成功的Future,状态为500,data为0
	 * 
	 * @param msg
	 *          提示信息
	 * @param data
	 *          数据
	 * @return
	 */
	public static Future<JsonObject> successC500(String msg, Object data) {
		return Future.succeededFuture(ResultFormat.format(ResultCode.C500, msg, data));
	}

}

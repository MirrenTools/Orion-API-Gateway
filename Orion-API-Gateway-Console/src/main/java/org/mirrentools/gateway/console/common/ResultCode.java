package org.mirrentools.gateway.console.common;

/**
 * 返回结果的状态码与状态信息
 * 
 * @author <a href="http://szmirren.com">Mirren</a>
 *
 */
public interface ResultCode {
	/** 状态码200 */
	public final static int C200 = 200;
	/** 状态码200的默认提示信息 */
	public final static String M200 = "成功！";
	/** 状态码200的默认状态信息 */
	public final static String E200 = "成功";
	/** 状态码401 */
	public final static int C401 = 401;
	/** 状态码401的默认提示信息 */
	public final static String M401 = "对不起!你没有操作该接口的权限！";
	/** 状态码401的默认状态信息 */
	public final static String E401 = "对不起!你没有操作该接口的权限,请检查token是否错误！";
	/** 状态码407 */
	public final static int C407 = 407;
	/** 状态码407的默认提示信息 */
	public final static String M407 = "对不起！您请求的数据不存在！";
	/** 状态码407的默认状态信息 */
	public final static String E407 = "对不起！您请求的数据不存在,请检查id是否有误！";
	/** 状态码412 */
	public final static int C412 = 412;
	/** 状态码412的默认提示信息 */
	public final static String M412 = "你的数据中缺少了必填的参数，或者你输入的数据存在空值！";
	/** 状态码412的默认状态信息 */
	public final static String E412 = "缺少必填参数";
	/** 状态码413 */
	public final static int C413 = 413;
	/** 状态码413的默认提示信息 */
	public final static String M413 = "你请求的数据不符合规定的范围！";
	/** 状态码413的默认状态信息 */
	public final static String E413 = "参数不符合范围,请检查是否数值是否保护了空格,是否符合对象或字符串要求";
	/** 状态码500 */
	public final static int C500 = 500;
	/** 状态码500的默认提示信息 */
	public final static String M500 = "操作失败，请稍后重试！";
	/** 状态码500的默认状态信息 */
	public final static String E500 = "服务端发生了错误,请联系管理员!";

}

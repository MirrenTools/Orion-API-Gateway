package org.mirrentools.gateway.console.common;

/**
 * 返回结果的状态码与状态信息
 * 
 * @author <a href="http://szmirren.com">Mirren</a>
 *
 */
public interface ResultCode {
	/** 状态码 登录失败,请稍后重试! */
	public final static String LOGIN_FAILED = "登录失败,请稍后重试!";
	/** 状态码 无效的手机号码! */
	public final static String INVALID_PHONE = "无效的手机号码!";
	/** 状态码200 */
	public final static int C200 = 200;
	/** 状态码200的默认提示信息 */
	public final static String M200 = "成功！";
	/** 状态码202 */
	public final static int C202 = 202;
	/** 状态码202的默认提示信息 */
	public final static String M202 = "请重新执行该操作!";
	/** 状态码203 */
	public final static int C203 = 203;
	/** 状态码203的默认提示信息 */
	public final static String M203 = "请进行下一步操作!";
	/** 状态码304 */
	public final static int C304 = 304;
	/** 状态码304的默认提示信息 */
	public final static String M304 = "账号或密码错误!";
	/** 状态码308 */
	public final static int C308 = 308;
	/** 状态码308的默认提示信息 */
	public final static String M308 = "验证码已经失效,请重新获取验证码!";
	/** 状态码309 */
	public final static int C309 = 309;
	/** 状态码309的默认提示信息 */
	public final static String M309 = "验证码错误!";
	/** 状态码310 */
	public final static int C310 = 310;
	/** 状态码310的默认提示信息 */
	public final static String M310 = "发短信无法暂时无法使用！";
	/** 状态码311 */
	public final static int C311 = 311;
	/** 状态码311的默认提示信息 */
	public final static String M311 = "一个手机号码一天只能接收10条短信！";
	/** 状态码401 */
	public final static int C401 = 401;
	/** 状态码401的默认提示信息 */
	public final static String M401 = "已超过登录有效期,请重新登录!";
	/** 状态码402 */
	public final static int C402 = 402;
	/** 状态码402的默认提示信息 */
	public final static String M402 = "非法操作或没有权限！";
	/** 状态码406 */
	public final static int C406 = 406;
	/** 状态码406的默认提示信息 */
	public final static String M406 = "对不起,您请求的数据无法打开(人员已满)!";
	/** 状态码407 */
	public final static int C407 = 407;
	/** 状态码407的默认提示信息 */
	public final static String M407 = "对不起！您请求的数据不存在！";
	/** 状态码408 */
	public final static int C408 = 408;
	/** 状态码408的默认提示信息 */
	public final static String M408 = "对不起！您请求的数据已过时！";
	/** 状态码412 */
	public final static int C412 = 412;
	/** 状态码412的默认提示信息 */
	public final static String M412 = "你的数据中缺少了必填的参数，或者你输入的数据存在空值！";
	/** 状态码413 */
	public final static int C413 = 413;
	/** 状态码413的默认提示信息 */
	public final static String M413 = "你请求的数据不符合规定的范围！";
	/** 状态码500 */
	public final static int C500 = 500;
	/** 状态码500的默认提示信息 */
	public final static String M500 = "操作失败，请稍后重试！";

}

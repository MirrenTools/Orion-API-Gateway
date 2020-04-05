package org.mirrentools.gateway.console.common;

/**
 * 状态码与状态信息的枚举类
 * 
 * @author Mirren
 *
 */
public enum ResultCodeEnum {
	/**
	 * 成功
	 */
	C200(200, "成功！", "成功"),
	/**
	 * 对不起!你没有操作该接口的权限！
	 */
	C401(401, "对不起!你没有操作该接口的权限！", "对不起!你没有操作该接口的权限,请检查token是否错误！"),
	/**
	 * 无效的URL请求,或者指定URL不存在
	 */
	C404(404, "找不到该服务", "无效的请求或指定URL不存在!"),
	/**
	 * 对不起！您请求的数据不存在！
	 */
	C407(407, "对不起！您请求的数据不存在！", "对不起！您请求的数据不存在,请检查id是否有误！"),
	/**
	 * 你的数据中缺少了必填的参数，或者你输入的数据存在空值！
	 */
	C412(412, "你的数据中缺少了必填的参数，或者你输入的数据存在空值！", "缺少必填参数"),
	/**
	 * 你请求的数据不符合规定的范围！
	 */
	C413(413, "你请求的数据不符合规定的范围！", "参数不符合范围,请检查是否数值是否保护了空格,是否符合对象或字符串要求"),
	/**
	 * 操作失败，请稍后重试！
	 */
	C500(500, "操作失败，请稍后重试！", "服务端发生了错误,请联系管理员!");

	// 状态码
	private int code;
	private String msg;
	private String explain;

	ResultCodeEnum(int code, String explain, String msg) {
		this.code = code;
		this.explain = explain;
		this.msg = msg;
	}

	/**
	 * 得到状态码
	 * 
	 * @return
	 */
	public int getCode() {
		return code;
	}

	/**
	 * 获得状态码提示的信息
	 * 
	 * @return
	 */
	public String getMsg() {
		return msg;
	}

	/**
	 * 获取状态码状态信息
	 * 
	 * @return
	 */
	public String getExplain() {
		return explain;
	}

}

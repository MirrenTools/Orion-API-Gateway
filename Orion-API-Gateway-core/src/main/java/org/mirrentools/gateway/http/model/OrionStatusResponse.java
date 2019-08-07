package org.mirrentools.gateway.http.model;

/**
 * 响应状态返回
 * 
 * @author <a href="http://mirrentools.org">Mirren</a>
 *
 */
public class OrionStatusResponse {
	/** 响应的状态码 */
	private int code;
	/** 响应的信息 */
	private String msg;
	/** 响应的数据类型 */
	private String type;
	/** 响应的数据 */
	private String data;

	public OrionStatusResponse() {
		super();
	}

	public OrionStatusResponse(int code, String msg, String type, String data) {
		super();
		this.code = code;
		this.msg = msg;
		this.type = type;
		this.data = data;
	}

	public int getCode() {
		return code;
	}

	public OrionStatusResponse setCode(int code) {
		this.code = code;
		return this;
	}

	public String getMsg() {
		return msg;
	}

	public OrionStatusResponse setMsg(String msg) {
		this.msg = msg;
		return this;
	}

	public String getType() {
		return type;
	}

	public OrionStatusResponse setType(String type) {
		this.type = type;
		return this;
	}

	public String getData() {
		return data;
	}

	public OrionStatusResponse setData(String data) {
		this.data = data;
		return this;
	}

	@Override
	public String toString() {
		return "OrionStatusResponse [code=" + code + ", msg=" + msg + ", type=" + type + ", data=" + data + "]";
	}
	
	

}
